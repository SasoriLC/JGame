package jgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import jgame.entity.Camera;
import jgame.entity.GameObject;
import jgame.entity.Observable;
import jgame.entity.Observer;
import jgame.tile.Tile;

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
 * @author Sasori
 * @since 1.0
 */
@SuppressWarnings("serial")
public class Scene extends JComponent{
	private ArrayList<ArrayList<Tile>> tiles; //tile's matrix of the scene
	private Set<GameObject> gameObjects; //the game objects in the scene
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
			gameObjects = new HashSet<>();

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

		for(int y = yMin; y < yMax; y++){
			for(int x = xMin; x < xMax; x++){
				Tile tile = tiles.get(y).get(x);
				g.drawImage(tile.getImage(), x * tile.getWidth() - (int)camera.position.x
						,y * tile.getHeight() - (int) camera.position.y,null);

			}
		}

		//paint the game objects
		for(GameObject gO: gameObjects){
			gO.draw(g);
		}
	}
	
	/**
	 * 
	 * @param gO the target
	 * @return the tiles where the game object is currently in
	 * @since 1.0
	 */
	public List<Tile> getGameObjectCurrentTiles(GameObject gO){
		Tile aux = tiles.get(0).get(0);
				
		int minX = (int) gO.position.x/ aux.getWidth();
		int maxX = (int) ((gO.position.x + gO.getSprite().getSpriteWidth()) / aux.getWidth()) + 1;
		
		int minY = (int) gO.position.y / aux.getHeight();
		int maxY = (int) ((gO.position.y+ gO.getSprite().getSpriteHeight()) / aux.getHeight()) + 1;
		
		List<Tile> tiles = new ArrayList<>();
		for(int y = minY; y < maxY; y++){
			for(int x  = minX; x < maxX; x++){
				if(y < this.tiles.size() && x < this.tiles.get(0).size())
				tiles.add(this.tiles.get(y).get(x));
			}
		}
		return tiles;
	}
}
