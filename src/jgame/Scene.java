package jgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import jgame.components.BoxCollider;
import jgame.entity.Camera;
import jgame.entity.GameObject;
import jgame.entity.Tile;
import jgame.exceptions.TileNotFoundException;

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
 * @see jgame.entity.GameObject
 * @see jgame.OrderedLayerSet
 * @see jgame.entity.Tile
 */
@SuppressWarnings("serial")
public class Scene extends JComponent{
	private ArrayList<ArrayList<Tile>> tiles; //tile's matrix of the scene
	private OrderedLayerSet gameObjects; //the game objects in the scene
	private List<GameObject> visibleGameObjects;
	private int width;
	private int height;
	
	/**
	 * Creates a scene
	 * @param scnFilePath the path of the scene
	 * @since 1.0
	 */
	public Scene(String scnFilePath){
		try(Scanner sc = new Scanner(new File(scnFilePath))) {
			tiles = new ArrayList<>();
			gameObjects = new OrderedLayerSet();
			visibleGameObjects = new ArrayList<>();

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
								,i,y));
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


		} catch (TileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
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
	 * @param gO the game object to add to the scene
	 * @since 1.0
	 */
	public void addGameObject(GameObject gO){
		gameObjects.add(gO);
	}

	/**
	 * 
	 * Paints the map and its game objects (such as overlays)
	 * @since 1.0
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//center the scene
		Camera camera = Camera.getInstance();
		camera.moveToTarget(this);

		//paint the map
		Tile aux = tiles.get(0).get(0);

		//there is no need to draw the elements that are
		//not within the range of the camera

		int xMin = Math.max(0, (int) camera.position.x / aux.getWidth());

		//between the limit and the xOffset of the camera plus
		//the maximum that it can represent on the screen
		//+1 because of floating point numbers
		int xMax = Math.min(tiles.get(0).size()
				, (int) camera.position.x + (camera.getWidth() / aux.getWidth()) + 1);

		int yMin = Math.max(0, (int) camera.position.y / aux.getHeight());
		int yMax = Math.min(tiles.size(), 
				(int)camera.position.y + (camera.getHeight() / aux.getHeight()) + 1);

		g.setColor(Color.RED);
		for(int y = yMin; y < yMax; y++){
			for(int x = xMin; x < xMax; x++){
				Tile tile = tiles.get(y).get(x);
				g.drawImage(tile.getImage(), x * tile.getWidth() - (int)camera.position.x
						,y * tile.getHeight() - (int) camera.position.y,null);
				if(tile.hasComponent("BoxCollider")){
					drawBoxCollider(g,(BoxCollider) tile.getComponent("BoxCollider"));
				}
			}
		}

		//paint the game objects
		visibleGameObjects = new ArrayList<>();
		for(GameObject gO: gameObjects){
			//only draw the necessary game objects
			if(gO.position.x + gO.getSprite().getSpriteWidth() >= camera.position.x
					&& gO.position.x <= camera.position.x + camera.getWidth()
					&& gO.position.y + gO.getSprite().getSpriteWidth() >= camera.position.y
					&& gO.position.y <= camera.position.y + camera.getHeight()){
				visibleGameObjects.add(gO);
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
	 * @return the all the game objects that are currently being shown on the scene
	 * @since 1.0
	 */
	public List<GameObject> getVisibleGameObjects(){
		return visibleGameObjects;
	}
	
	/**
	 * 
	 * @param name the name of the game object
	 * @return the game objects of this scene that have a name equal to @name
	 * @since 1.0
	 */
	public List<GameObject> getGameObjectsByName(String name){
		List<GameObject> result = new ArrayList<GameObject>();
		for(GameObject g: gameObjects)
			if(g.getName().equals(name))
				result.add(g);
		
		return result;
	}

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
	 * 
	 * @param gO the target
	 * @return the tiles where the game object is currently in
	 * @since 1.0
	 */
	public List<Tile> getGameObjectCurrentTiles(GameObject gO){
		return getTilesIn(gO.position.x,gO.position.x + gO.getSprite().getSpriteWidth()
					,gO.position.y,gO.position.y+ gO.getSprite().getSpriteHeight());
	}
}
