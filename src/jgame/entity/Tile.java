package jgame.entity;
import java.awt.image.BufferedImage;
/**
 * This class represents a tile.
 * <br>
 * A tile is composed by it's image and it's number. It is also an entity
 * <br>
 * In a scene each tile with a different image as a different number
 * @author David Almeida
 * @since 1.0
 * @version 1.0
 */
public class Tile extends Entity implements Cloneable{
	private int number;
	private BufferedImage tile;
	
	/**
	 * 
	 * @param image the image representing the tile
	 * @param number the number of the tile
	 * @param x the x of the tile in the scene
	 * @param y the y of the tile in the scene
	 * @since 1.0
	 */
	public Tile(BufferedImage image, int number,int x,int y){
		this.number = number;
		position.x = x;
		position.y = y;
		tile = image;
	}
		
	/**
	 * 
	 * @return the image that represents the tile
	 * @since 1.0
	 */
	public BufferedImage getImage(){
		return tile;
	}
	
	/**
	 * 
	 * @return the width of the tile
	 * @since 1.0
	 */
	public int getWidth(){
		return tile.getWidth();
	}
	
	/**
	 * 
	 * @return the height of the tile
	 * @since 1.0
	 */
	public int getHeight(){
		return tile.getHeight();
	}
	
	/**
	 * 
	 * @return the number of the tile
	 * @since 1.0
	 */
	public int getTileNumber(){
		return number;
	}
	
	/**
	 * 
	 * @return the x of the tile
	 * @since 1.0
	 */
	public int getX(){
		return (int)position.x * getWidth();
	}
	
	/**
	 * 
	 * @return the y of the tile
	 * @since 1.0
	 */
	public int getY(){
		return (int)position.y * getHeight();
	}
		
	/**
	 * 
	 * @return a clone of this object
	 * @since 1.0
	 */
	@Override
	public Object clone(){
		Tile clone = null;
		try {
			clone = (Tile) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
}