package jgame.components;

import java.util.List;

import jgame.Scene;
import jgame.Window;
import jgame.entity.Camera;
import jgame.entity.Entity;
import jgame.entity.GameObject;
import jgame.entity.Observable;
import jgame.entity.Tile;

/**
 * This class is responsible for detecting a the collision of an entity.
 * AABB collision is used as algorithm.
 * @author David Almeida
 * @since 1.1
 */
public class BoxCollider extends Component{
	private int xMin,yMin,xMax,yMax;

	public BoxCollider(GameObject o){
		this((int)o.position.x,(int)o.position.y
				,(int)(o.position.x + o.getSprite().getSpriteWidth()),
				(int)(o.position.y + o.getSprite().getSpriteHeight()));
	}

	public BoxCollider(Tile t){
		this((int)(t.position.x * t.getWidth())
				,(int)(t.position.y * t.getHeight())
				,(int)(t.position.x * t.getWidth() + t.getHeight()),
				(int)(t.position.y * t.getHeight() + t.getHeight()));
	}

	public BoxCollider(int xMin, int yMin,int xMax, int yMax){
		super("BoxCollider");
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}

	/**
	 * @return the xMin
	 */
	public int getxMin() {
		return xMin;
	}

	/**
	 * @param xMin the xMin to set
	 */
	public void setxMin(int xMin) {
		this.xMin = xMin;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @param yMin the yMin to set
	 */
	public void setyMin(int yMin) {
		this.yMin = yMin;
	}

	/**
	 * @return the xMax
	 */
	public int getxMax() {
		return xMax;
	}

	/**
	 * @param xMax the xMax to set
	 */
	public void setxMax(int xMax) {
		this.xMax = xMax;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @param yMax the yMax to set
	 */
	public void setyMax(int yMax) {
		this.yMax = yMax;
	}

	@Override
	public String toString(){
		return "min x: " + xMin + " min y: " + yMin + " max x: " + xMax + " max y: " + yMax;
	}

	/**
	 * This method is called when the observable game object moves and detects collision 
	 * using AABB algorithm
	 * @param entity - the game object being observed
	 * @since 1.0
	 */
	@Override
	public void update(Observable entity){
		if(entity instanceof GameObject){
			GameObject o = (GameObject) entity;

			Scene currentScene = Window.getInstance().getScene();
			List<Tile> tiles = currentScene.getGameObjectCurrentTiles(o);
			List<GameObject> gos = currentScene.getVisibleGameObjects();

			for(Tile tile: tiles){
				if(o.collided(tile)){
					if(tile.hasComponent("BoxCollider")){
						BoxCollider c = (BoxCollider)tile.getComponent("BoxCollider");
						pushGameObjectFrom(o,c.getxMin(),c.getyMin(),
								c.getxMax() ,c.getxMax());
					}
				}
			}

			for(GameObject g: gos){
				if(o.collided(g) && !g.equals(o)){
					if(g.hasComponent("BoxCollider")){
						BoxCollider c = (BoxCollider)g.getComponent("BoxCollider");
						pushGameObjectFrom(o,c.getxMin(),c.getyMin(),
								c.getxMax() ,c.getxMax());
					}
				}
			}
		}
	}

	/**
	 * This method push a game object away from the given point
	 * @param o the game object
	 * @param minX the minimum x 
	 * @param minY the minimum y
	 * @param maxX the maximum x
	 * @param maxY the maximum y
	 * @since 1.0
	 */
	private void pushGameObjectFrom(GameObject o, int minX, int minY, int maxX, int maxY){
		BoxCollider c = (BoxCollider) o.getComponent("BoxCollider");
		boolean verticalCollision = !(maxX <= o.position.x)
				&& !(o.position.x + o.getSprite().getSpriteWidth() <= minX);

		if(verticalCollision){
			if(maxY - 2 < o.position.y ){
				o.position.y = maxY;
			}else if(minY > o.position.y + o.getSprite().getSpriteHeight() - 2){
				o.position.y = minY - o.getSprite().getSpriteHeight();
			}
		}


		boolean horizontalCollision = (!(maxY <= o.position.y))
				&& !(o.position.y + o.getSprite().getSpriteHeight() <= minY);

		if(horizontalCollision){
			if(minX > o.position.x + o.getSprite().getSpriteWidth() - 2){ //right
				o.position.x = minX - o.getSprite().getSpriteWidth();
			}else{ //left
				o.position.x = maxX;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable entity, Object... metadata) {
		if(entity instanceof GameObject){
			xMin += (int)metadata[0];
			yMin += (int)metadata[1];
			xMax += (int)metadata[0];
			yMax += (int)metadata[1];
		}
	}

}
