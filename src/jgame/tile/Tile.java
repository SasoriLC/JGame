package jgame.tile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This class represents a tile.
 * <br>
 * A tile is composed by it's image, it's number and it's type.
 * <br>
 * In a scene each tile with a different image as a different number
 * @author David Almeida
 * @since 1.0
 */
public class Tile implements Cloneable{
	private int number,scnX,scnY;
	private BufferedImage tile;
	private TileType type;
	
	
	/**
	 * 
	 * @param image the image representing the tile
	 * @param number the number of the tile
	 * @param x the x of the tile in the scene
	 * @param x the y of the tile in the scene
	 * @since 1.0
	 */
	public Tile(BufferedImage image, int number,int x,int y){
		this.number = number;
		this.scnX = x;
		this.scnY = y;
		tile = image;
	}
	
	/**
	 * 
	 * @param image the image representing the tile
	 * @param number the number of the tile
	 * @param x the x of the tile in the scene
	 * @param x the y of the tile in the scene
	 * @param type the tile's type
	 * @since 1.0
	 */
	public Tile(BufferedImage image, int number,int x,int y,TileType type){
		this(image,number,x,y);
		this.type = type;
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
	 * @return the x of the tile in the scn file
	 * @since 1.0
	 */
	public int getTileScnX(){
		return scnX;
	}
	
	/**
	 * 
	 * @return the x of the tile
	 * @since 1.0
	 */
	public int getX(){
		return getTileScnX() * getWidth();
	}
	
	/**
	 * 
	 * @return the y of the tile
	 * @since 1.0
	 */
	public int getY(){
		return getTileScnY() * getHeight();
	}
	
	/**
	 * 
	 * @return the y of the tile in the scn file
	 * @since 1.0
	 */
	public int getTileScnY(){
		return scnY;
	}
	
	/**
	 * 
	 * @param newType the tile's type
	 * @since 1.0
	 * @see TileType
	 */
	public void setTileType(TileType newType){
		this.type = newType;
	}
	
	/**
	 * 
	 * @return the tile's type
	 * @since 1.0
	 */
	public TileType getTileType(){
		return type;
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
