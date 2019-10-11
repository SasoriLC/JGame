package jgame.entity;

import java.util.ArrayList;

import jgame.Scene;
import jgame.structures.Point2D;
import jgame.tile.Tile;

/**
 * This class represents a camera.
 * <br>
 * A camera must have a game object to follow
 * <br>
 * The camera will only show things that are within its range (width, height)
 * @author David Almeida
 * @since 1.0
 */
public class Camera extends Entity{

	private static Camera INSTANCE;
	private GameObject target;
	private int width;
	private int height;


	/**
	 * 
	 * @param target the target of the camera
	 * @param width the width of the camera
	 * @param height the height of the camera
	 * @param initialX the initial x position of the camera
	 * @param initialY the initial y position of the camera
	 * @since 1.0
	 */
	private Camera(GameObject target, int width, int height,
			int initialX, int initialY){
		this.target = target;
		this.width = width;
		this.height = height;
		position = new Point2D(initialX,initialY);
		INSTANCE = this;
	}
	
	/**
	 * Creates an Instance of the camera object
	 * @param target the target of the camera
	 * @param width the width of the camera
	 * @param height the height of the camera
	 * @param initialX the initial x position of the camera
	 * @param initialY the initial y position of the camera
	 * @return the camera
	 * @since 1.0
	 */
	public static Camera create(GameObject target, int width, int height,
			int initialX, int initialY){
		INSTANCE = new Camera(target,width,height,initialX,initialY);
		return INSTANCE;
	}
	
	/**
	 * 
	 * @return the current camera's instance
	 * @since 1.0
	 */
	public static Camera getInstance(){
		return INSTANCE;
	}

	/**
	 * @return the width
	 * @since 1.0
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 * @since 1.0
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 * @since 1.0
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 * @since 1.0
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 
	 * @return the camera's target game object
	 * @since 1.0
	 */
	public GameObject getTarget(){
		return target;
	}

	/**
	 * 
	 * @param scene the scene in which the target is being focused
	 * <p>
	 * Centers the camera at the target
	 * @since 1.0
	 */
	public void moveToTarget(Scene scene){
		ArrayList<ArrayList<Tile>> tiles = scene.getTiles();
		Tile tile = tiles.get(0).get(0);

		int targetX = (int) 
				(target.position.x - width / 2) + target.getSprite().getSpriteWidth() / 2;
		position.x = (float)(position.x + (targetX - position.x) * 0.1);
//		xOffset = (target.getX() - width / 2) + target.getSprite().getSpriteWidth() / 2;;

		if(position.x  < 0)
			position.x  = 0;
		else if(position.x  + width > scene.getWidth())
			position.x  = tiles.get(0).size() * tile.getWidth() 
			- width;
		 
		int targetY = (int) 
				(target.position.y - height / 2) + target.getSprite().getSpriteHeight() / 2;
		position.y = (float) (position.y + (targetY - position.y) * 0.1);
//		yOffset = (target.getY() - height / 2) + target.getSprite().getSpriteHeight() / 2;
		
		if(position.y < 0)
			position.y = 0;
		else if (position.y + height > scene.getHeight())
			position.y = tiles.size() * tile.getHeight() - height;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + height;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + width;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Camera other = (Camera) obj;
		if (height != other.height)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (width != other.width)
			return false;
		return true;
	}
}
