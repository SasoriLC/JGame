package jgame.entity;
import java.awt.image.BufferedImage;
import java.util.Objects;

import jgame.exceptions.SpriteDoesNotExistException;
import jgame.sprite.Sprite;
/**
 * This class represents a tile. Note that a tile is ALWAYS in layer -1.
 * <br>
 * A tile is composed by it's image and it's number. It is also an entity
 * <br>
 * In a scene each tile with a different image as a different number
 * @author David Almeida
 * @since 1.0
 * @version 1.0
 * @deprecated
 */
public class Tile extends GameObject implements Cloneable{
	private int number;
	
	/**
	 * 
	 * @param image the image representing the tile
	 * @param number the number of the tile
	 * @param x the x of the tile in the scene
	 * @param y the y of the tile in the scene
	 * @throws SpriteDoesNotExistException 
	 * @since 1.0
	 */
	public Tile(BufferedImage image, int number,int x,int y) throws SpriteDoesNotExistException{
		super(new Sprite(image,1),x,y,-1);
		this.number = number;
		position.x = x;
		position.y = y;
		layer = -1;
	}
		
	/**
	 * 
	 * @return the image that represents the tile
	 * @since 1.0
	 */
	public BufferedImage getImage(){
		return this.getSprite().getFullSpriteImage();
	}
	
	/**
	 * 
	 * @return the width of the tile
	 * @since 1.0
	 */
	public int getWidth(){
		return 	this.getSprite().getFullSpriteImage().getWidth();
	}
	
	/**
	 * 
	 * @return the height of the tile
	 * @since 1.0
	 */
	public int getHeight(){
		return this.getSprite().getFullSpriteImage().getHeight();
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
		clone = (Tile) super.clone();
		return clone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		return number == other.number;
	}

}