package jgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import jgame.components.BoxCollider;
import jgame.entity.Camera;
import jgame.entity.Entity;
import jgame.entity.GameObject;
import jgame.entity.Observable;
import jgame.entity.Observer;
import jgame.entity.Tile;
import jgame.exceptions.EntityIsAlreadyInSceneException;
import jgame.exceptions.EntityIsNotInSceneException;
import jgame.exceptions.JGameException;
import jgame.exceptions.TileNotFoundException;
import jgame.structures.Pair;

/**
 * This class represents a Map.
 * <br>
 * It is made from a scn file and it has:
 * <br>
 * The tiles from the scn files
 * <br>
 * Game objects
 * <br>
 * A camera to follow a certain game object of the scene
 * @author David Almeida
 * @since 1.0
 * @see jgame.entity.Entity
 * @see jgame.entity.GameObject
 * @see jgame.OrderedLayerSet
 */
@SuppressWarnings("serial")
public class Scene extends JComponent implements Observer{
	private static final int CHUNK_SIZE = 60;
	
	private ArrayList<ArrayList<Tile>> tiles; //tile's matrix of the scene
	private List<Entity>[][] chunks; //the game objects in the scene
	private Map<Long, Pair<Integer,Integer>> entityChunks; //key: entity's id, value: x and y entity
	private Map<String, List<Entity>> entities; //key: name, value: game objects with that name
	private List<Entity> visibleEntities;
	private int width;
	private int height;
	
	/**
	 * Creates a scene
	 * @param scnFilePath the path of the scene
	 * @since 1.0
	 */
	public Scene(String scnFilePath){
		//TODO PARA REFAZER. POR ENQUANTO ESTÁ ASSIM PARA AINDA SE PODER TESTAR A PARTIR DE UM FICHEIRO
		//IRA PASSAR APENAS A RECEBER O TAMANHO DO MAPA (COMPRIMENTO E LARGURA)
		entities = new HashMap<>();
		entityChunks = new HashMap<>();
		try(Scanner sc = new Scanner(new File(scnFilePath))) {
			tiles = new ArrayList<>();
			visibleEntities = new ArrayList<>();

			int currentTileNumber = 1;
			int y = 0;

			//key - the number of the tile
			//value - tile file path
			HashMap<Integer, BufferedImage> map = new HashMap<>();
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] aux = line.split("->");
				if(aux.length == 1){ //not a file path
					ArrayList<Tile> row = new ArrayList<>();
					aux = line.split(" ");

					for(int i = 0; i < aux.length; i++){
						int number = Integer.parseInt(aux[i]);
						BufferedImage image = map.get(number);

						row.add(new Tile(image,number
								,i * image.getWidth(),y * image.getHeight()));
					}

					tiles.add(row);
					y++;
				}else if(aux[0].equals("tile")){
					//read the file path and associate the tile to a number
					BufferedImage img = ImageIO.read(new File(aux[1]));
					map.put(currentTileNumber++,img);
				}
			}

			width = tiles.get(0).size() * tiles.get(0).get(0).getWidth();
			height = tiles.size() * tiles.get(0).get(0).getHeight();
			chunks = new ArrayList[width/CHUNK_SIZE + 1][height/CHUNK_SIZE + 1];
			for(ArrayList<Tile> l: tiles) {
				for(Tile t: l) {
					addGameObject(t);
				}
			}
		} catch (TileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (JGameException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 
	 * @return the width of the scene
	 * @since 1.0
	 */
	public int getWidth(){
		return width;
	}

	/**
	 * 
	 * @return the height of the scene
	 * @since 1.0
	 */
	public int getHeight(){
		return height;
	}

	/**
	 * 
	 * @return the tiles of the scene
	 * @since 1.0
	 */
	public  ArrayList<ArrayList<Tile>> getTiles(){
		return tiles;
	}

	/**
	 * 
	 * @param e the entity to add to the scene
	 * @throws EntityIsAlreadyInSceneException if an entity is being added twice to the same scene
	 * @since 1.0
	 */
	public void addGameObject(Entity e) throws EntityIsAlreadyInSceneException{
//		gameObjects.add(gO);
		int xChunk = (int)e.position.x/CHUNK_SIZE;
		int yChunk = (int)e.position.y/CHUNK_SIZE;
		if(entityChunks.put(e.getId(), new Pair<Integer,Integer>(xChunk,yChunk)) != null)
			throw new EntityIsAlreadyInSceneException();
		List<Entity> currentList = chunks[xChunk][yChunk];
		if(currentList == null) {
			currentList = new ArrayList<>();
			chunks[xChunk][yChunk] = currentList;
		}
		List<Entity> gOs = entities.get(e.getName());
		if(gOs == null) {
			gOs = new ArrayList<>();
			entities.put(e.getName(), gOs);
		}
		gOs.add(e);
		currentList.add(e);
		
		e.addObserver(this);
	}
	
	/**
	 * 
	 * @param e the entity to delete from the scene
	 * @throws EntityIsAlreadyInSceneException if an entity is being added twice to the same scene
	 * @since 1.0
	 */
	public void deleteEntity(Entity e) throws EntityIsNotInSceneException{
		Pair<Integer,Integer> p = entityChunks.remove(e);
		if(p == null)
			throw new EntityIsNotInSceneException();
		chunks[p.getE()][p.getV()].remove(e);
		entities.remove(e.getName());
	}

	/**
	 * 
	 * Paints the map and its entities (such as overlays)
	 * @since 1.0
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//center the scene
		Camera camera = Camera.getInstance();
		camera.moveToTarget(this);

		//paint the map
//		Tile aux = tiles.get(0).get(0);

		//there is no need to draw the elements that are
		//not within the range of the camera

		int xMin = Math.max(0, (int) camera.position.x /*/ aux.getWidth()*/);

		//between the limit and the xOffset of the camera plus
		//the maximum that it can represent on the screen
		//+1 because of floating point numbers
		int xMax = Math.min(width
				, (int) camera.position.x + (camera.getWidth() /*/ aux.getWidth()*/) + CHUNK_SIZE);

		int yMin = Math.max(0, (int) camera.position.y /*/ aux.getHeight()*/);
		int yMax = Math.min(height, 
				(int)camera.position.y + (camera.getHeight() /*/ aux.getHeight()*/) + CHUNK_SIZE);

//		g.setColor(Color.RED);
//		for(int y = yMin; y < yMax; y++){
//			for(int x = xMin; x < xMax; x++){
//				Tile tile = tiles.get(y).get(x);
//				g.drawImage(tile.getImage(), x * tile.getWidth() - (int)camera.position.x
//						,y * tile.getHeight() - (int) camera.position.y,null);
//				if(tile.hasComponent("BoxCollider")){
//					drawBoxCollider(g,(BoxCollider) tile.getComponent("BoxCollider"));
//				}
//			}
//		}

		//paint the game objects
		visibleEntities = new ArrayList<>();
		OrderedLayerSet visibleSet = new OrderedLayerSet();
		//for safety draw the outline chunks
		for(int y = yMin; y < yMax + CHUNK_SIZE && (y/CHUNK_SIZE) < chunks[0].length; y+=CHUNK_SIZE) {
			for(int x = xMin; x < xMax + CHUNK_SIZE && (x/CHUNK_SIZE) < chunks.length; x+=CHUNK_SIZE){
				//only draw the necessary game objects
				List<Entity> entities = chunks[x/CHUNK_SIZE][y/CHUNK_SIZE];
				if(entities == null)
					continue;
				for(Entity e: entities) {
					visibleSet.add(e);
					visibleEntities.add(e);
				}
			}
		}
		for(Entity e: visibleSet){
			if(!(e instanceof GameObject))
				continue;
			GameObject gO = (GameObject) e;
			if(gO.position.x + gO.getSprite().getSpriteWidth() >= camera.position.x
					&& gO.position.x <= camera.position.x + camera.getWidth()
					&& gO.position.y + gO.getSprite().getSpriteWidth() >= camera.position.y
					&& gO.position.y <= camera.position.y + camera.getHeight()){				
				if(gO.hasComponent("BoxCollider")){
					g.setColor(Color.GREEN);
					drawBoxCollider(g,(BoxCollider) gO.getComponent("BoxCollider"));
				}
				gO.draw(g);
			}
		}				
	}
	
	private void drawBoxCollider(Graphics g, BoxCollider collider){
		Camera camera = Camera.getInstance();
		//top
		g.drawLine((int)collider.minPoint.x - (int)camera.position.x, (int)collider.minPoint.y - (int)camera.position.y, (int)collider.maxPoint.x  - (int)camera.position.x, (int)collider.minPoint.y - (int)camera.position.y);
		//right
		g.drawLine((int)collider.maxPoint.x - (int)camera.position.x, (int)collider.minPoint.y - (int)camera.position.y, (int)collider.maxPoint.x  - (int)camera.position.x, (int)collider.maxPoint.y - (int)camera.position.y);
		//bottom
		g.drawLine((int)collider.minPoint.x - (int)camera.position.x, (int)collider.maxPoint.y - (int)camera.position.y, (int)collider.maxPoint.x  - (int)camera.position.x, (int)collider.maxPoint.y - (int)camera.position.y);
		//left
		g.drawLine((int)collider.minPoint.x - (int)camera.position.x, (int)collider.minPoint.y - (int)camera.position.y,(int) collider.minPoint.x - (int)camera.position.x, (int)collider.maxPoint.y - (int)camera.position.y);
	}
		
	/**
	 * 
	 * @return the all the entities that are currently being shown on the scene
	 * @since 1.0
	 */
	public List<Entity> getVisibleEntities(){
		return visibleEntities;
	}
	
	/**
	 * 
	 * @param name the name of the entity
	 * @return the entities of this scene that have a name equal to @name
	 * @since 1.0
	 */
	public List<Entity> getEntitiesByName(String name){
		return entities.get(name);
//		List<GameObject> result = new ArrayList<GameObject>();
//		for(GameObject g: mapGameObjects)
//			if(g.getName().equals(name))
//				result.add(g);
//		
//		return result;
	}

	/**
	 * REMOVE
	 * @param xMin
	 * @param yMin
	 * @param xMax
	 * @param yMax
	 * @return
	 * @deprecated
	 */
	public List<Tile> getTilesIn(float xMin, float yMin, float xMax, float yMax){
		Tile aux = tiles.get(0).get(0);

		int minX = (int) xMin/ aux.getWidth();
		int maxX = (int) ((xMax) / aux.getWidth()) + 1;

		int minY = (int) yMin / aux.getHeight();
		int maxY = (int) ((yMax) / aux.getHeight()) + 1;

		List<Tile> tiles = new ArrayList<>();
		for(int y = minY; y < maxY; y++){
			for(int x  = minX; x < maxX; x++){
				if(y < this.tiles.size() && x < this.tiles.get(0).size())
					tiles.add(this.tiles.get(y).get(x));
				
			}
		}
		return tiles;
	}
	
	/**
	 * REMOVE
	 * @param gO the target
	 * @return the tiles where the game object is currently in
	 * @since 1.0
	 * @deprecated
	 */
	public List<Tile> getGameObjectCurrentTiles(GameObject gO){
		return getTilesIn(gO.position.x,gO.position.x + gO.getSprite().getSpriteWidth()
					,gO.position.y,gO.position.y+ gO.getSprite().getSpriteHeight());
	}

	@Override
	public void update(Observable entity) {
		if(entity instanceof Entity) {
			Entity e = (Entity) entity;
			//update the entity's chunk
			Pair<Integer,Integer> currentChunks = entityChunks.get(e.getId());
			chunks[currentChunks.getE()][currentChunks.getV()].remove(e);
			
			int xChunk = (int)e.position.x/CHUNK_SIZE;
			int yChunk = (int)e.position.y/CHUNK_SIZE;
			
			List<Entity> newChunkList = chunks[xChunk][yChunk];
			if(newChunkList == null) {
				newChunkList = new ArrayList<>();
				chunks[xChunk][yChunk] =newChunkList;
			}
			newChunkList.add(e);
			entityChunks.put(e.getId(), new Pair<Integer,Integer>(xChunk,yChunk));
		}
	}

	@Override
	public void update(Observable entity, Object... metadata) {
		// TODO Auto-generated method stub
		
	}
}
