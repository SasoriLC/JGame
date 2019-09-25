package jgame.entity;

import java.awt.Graphics;
import jgame.Scene;
import jgame.Window;
import jgame.sprite.Sprite;
import jgame.structures.Vector2;
import jgame.tile.Tile;

/**
 * This class represents a game object.
 * @author David Almeida
 * @see jgame.entity.Entity
 * @since 1.0
 */
public class GameObject extends Entity{

	private Sprite sprite;
	private float xD; //x direction
	private float yD; //y direction
	
	
	/**
	 * 
	 * @param sprite the sprite representing the game object
	 * @since 1.0
	 */
	public GameObject(Sprite sprite) {
		super();
		this.sprite = sprite;
	}
	
	
	/**
	 * 
	 * @param sprite the sprite representing the game object
	 * @param x the initial x of the game object
	 * @param y the initial y of the game object
	 * @since 1.0
	 */
	public GameObject(Sprite sprite, int x, int y) {
		this(sprite);
		position = new Vector2(x,y);
	}
	
		
	/**
	 * Draws the game object in the scene
	 * @param g the graphics of the scene
	 * @since 1.0
	 */
	public void draw(Graphics g){
		move();
		Camera camera = Camera.getInstance();
		
		g.drawImage(sprite.getNextSpriteSequence(),
					(int) (position.x - camera.position.x)
					,(int) (position.y - camera.position.y), null);
	}
	
	/**
	 * Moves the game object in the current scene that is being displayed
	 * @since 1.0
	 */
	private void move(){
		this.notifyObservers();
		Scene scene = Window.getInstance().getScene();
		position.x += xD;
		if(position.x < 0)
			position.x = 0;
		else if(position.x > scene.getWidth() - sprite.getSpriteWidth())
			position.x = scene.getWidth() - sprite.getSpriteWidth();
		
		
		position.y += yD;
		if(position.y < 0)
			position.y = 0;
		else if(position.y > scene.getHeight() - sprite.getSpriteHeight())
			position.y = scene.getHeight() - sprite.getSpriteHeight();
		
	}
	
	/**
	 * @param velocity the velocity of the movement
	 * <p>
	 * This is used to move the game object around the x-axis
	 * @since 1.0
	 */
	public void moveX(float velocity) {
		xD = velocity;
	}

	/**
	 * @param velocity the velocity of the movement
	 * <p>
	 * This is used to move the game object around the y-axis
	 * @since 1.0
	 */
	public void moveY(float velocity) {
		yD = velocity;
	}
	

	/**
	 * 
	 * @return the sprite of the game object
	 * @since 1.0
	 */
	public Sprite getSprite(){
		return sprite;
	}
	
	
	/**
	 * 
	 * @param minX the starting x point of the rectangle
	 * @param minY the starting y point of the rectangle
	 * @param maxX the ending x point of the rectangle
	 * @param maxY the ending y point of the rectangle
	 * @return true if the game object is within the given rectangle
	 * @since 1.0
	 */
	private boolean isWithinRectangle(float minX, float minY,float maxX,float maxY){
//		player1.x < player2.x + player2.width &&
//	    player1.x + player1.width > player2.x &&
//	    player1.y < player2.y + player2.height &&
//	    player1.y + player1.height > player2.y
	    
		return(position.x + getSprite().getSpriteWidth() >= minX 
				&& position.x <= maxX)
				|| (position.y  + getSprite().getSpriteHeight() >= minY 
				&& position.y <= maxY);
	}
	
	
	/**
	 * 
	 * @param tile the tile to check
	 * @return true if the game object collided with the given tile
	 * @since 1.0
	 */
	public boolean collided(Tile tile){		
		return isWithinRectangle(tile.getX()
				,tile.getY(),
				tile.getX() + tile.getWidth(),
				tile.getY() + tile.getHeight());
	}
	
	/**
	 * 
	 * @param go
	 * @return true if the game object collided with the given game object
	 * @since 1.0
	 */
	public boolean collided(GameObject go){
		return isWithinRectangle(go.position.x
				,go.position.y,
				go.position.x + go.getSprite().getSpriteWidth(),
				go.position.y + go.getSprite().getSpriteHeight());
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sprite == null) ? 0 : sprite.hashCode());
		result = prime * result + Float.floatToIntBits(xD);
		result = prime * result + Float.floatToIntBits(yD);
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
		GameObject other = (GameObject) obj;
		if (sprite == null) {
			if (other.sprite != null)
				return false;
		} else if (!sprite.equals(other.sprite))
			return false;
		if (Float.floatToIntBits(xD) != Float.floatToIntBits(other.xD))
			return false;
		if (Float.floatToIntBits(yD) != Float.floatToIntBits(other.yD))
			return false;
		return true;
	}
			
}
