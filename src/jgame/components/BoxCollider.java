package jgame.components;

import java.util.List;

import jgame.Scene;
import jgame.Window;
import jgame.entity.Entity;
import jgame.entity.GameObject;
import jgame.entity.Observable;
import jgame.structures.Point2D;

/**
 * This class is responsible for detecting a collision of an entity.
 * AABB collision is used as algorithm.
 * @author David Almeida
 * @since 1.1
 */
public class BoxCollider extends Component {
	public Point2D minPoint;
	public Point2D maxPoint;
	private Point2D lastMinPoint;
	private Point2D lastMaxPoint;

	//entity
	private Entity entity;
	private Point2D lastEntityPoint;

	/**
	 * If this constructor is used to collider will be cover the whole game object
	 * @param t the tile of the collider
	 * @since 1.1
	 */
	public BoxCollider(GameObject o){
		this(o,o.position.x,o.position.y
				,o.position.x + o.getSprite().getSpriteWidth(),
				o.position.y + o.getSprite().getSpriteHeight());
	}

	/**
	 * 
	 * @param e the entity that this collider is associated with
	 * @param xMin the minimum x of the collider
	 * @param yMin the minimum y of the collider
	 * @param xMax the maximum x of the collider
	 * @param yMax the maximum y of the collider
	 * @since 1.1
	 */
	public BoxCollider(Entity e, float xMin, float yMin,float xMax, float yMax){
		super("BoxCollider");
		entity = e;
		minPoint = new Point2D(xMin,yMin);
		maxPoint = new Point2D(xMax,yMax);
		lastMinPoint = new Point2D(minPoint.x,minPoint.y);
		lastMaxPoint = new Point2D(maxPoint.x,maxPoint.y);
		lastEntityPoint = new Point2D(entity.position.x,entity.position.y);

	}

	/**
	 * 
	 * @param xMin the new minimum x
	 * @param yMin the new minimum y
	 * @param xMax the new maximum x
	 * @param yMax the new maximum y
	 * @since 1.1
	 */
	public void setPosition(float xMin, float yMin, float xMax, float yMax) {
		minPoint = new Point2D(xMin,yMin);
		maxPoint = new Point2D(xMax,yMax);
	}

	/**
	 * 
	 * @param minX the starting x point of the rectangle
	 * @param minY the starting y point of the rectangle
	 * @param maxX the ending x point of the rectangle
	 * @param maxY the ending y point of the rectangle
	 * @return true if the collider is within the given rectangle
	 * @since 1.1
	 */
	private boolean isWithinRectangle(float minX, float minY,float maxX,float maxY){
		return maxPoint.x >= minX 
				&& minPoint.x <= maxX
				&& maxPoint.y >= minY 
				&& minPoint.y <= maxY;
	}


	/**
	 * 
	 * @param tile the tile to check
	 * @return true if the game object collided with the given tile
	 * @since 1.1
	 */
	public boolean collided(Entity entity){
		if(entity.hasComponent("BoxCollider")){
			BoxCollider collider = (BoxCollider) entity.getComponent("BoxCollider");
			return isWithinRectangle(collider.minPoint.x
					,collider.minPoint.y,
					collider.maxPoint.x,
					collider.maxPoint.y);
		}
		return false;
	}

	/**
	 * This method is called when the observable game object moves and detects collision 
	 * using AABB algorithm
	 * @param entity the game object being observed
	 * @since 1.1
	 */
	@Override
	public void update(Observable entity){
		if(entity instanceof GameObject && Window.getInstance().getScene().hasLoaded()){
			GameObject o = (GameObject) entity;

			Scene currentScene = Window.getInstance().getScene();
			List<Entity> entities = currentScene.getVisibleEntities();

			for(Entity e: entities){
				if(e.hasComponent("BoxCollider") && !e.equals(o)){
					BoxCollider c = (BoxCollider)e.getComponent("BoxCollider");
					if(c.collided(o)){
						pushGameObjectFrom(o,c.minPoint.x,c.minPoint.y,
								c.maxPoint.x ,c.maxPoint.y);
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
	private void pushGameObjectFrom(GameObject o, float minX, float minY, float maxX, float maxY){
		minPoint = new Point2D(lastMinPoint.x, lastMinPoint.y);
		maxPoint = new Point2D(lastMaxPoint.x, lastMaxPoint.y);
		entity.position = new Point2D(lastEntityPoint.x,lastEntityPoint.y);
		
//		float oldX = o.position.x;
//		float oldY = o.position.y;
//		boolean verticalCollision = !(maxX <= o.position.x)
//				&& !(o.position.x + o.getSprite().getSpriteWidth() <= minX);
//
//		if(verticalCollision){
//			if(maxY - 2 < o.position.y ){
//				o.position.y = maxY;
//			}else if(minY > o.position.y + o.getSprite().getSpriteHeight() - 2){
//				o.position.y = minY - o.getSprite().getSpriteHeight();
//			}
//		}
//
//
//		boolean horizontalCollision = (!(maxY <= o.position.y))
//				&& !(o.position.y + o.getSprite().getSpriteHeight() <= minY);
//
//		if(horizontalCollision){
//			if(minX > o.position.x + o.getSprite().getSpriteWidth() - 2){ //right
//				o.position.x = minX - o.getSprite().getSpriteWidth();
//			}else{ //left
//				o.position.x = maxX;
//			}
//		}
//		
//		float difX = o.position.x - oldX;
//		float difY = o.position.y - oldY;
		
//		minPoint = new Point2D(minPoint.x + difX, minPoint.y + difY);
//		maxPoint = new Point2D(maxPoint.x + difX, maxPoint.y + difY);
	}

	/**
	 * Moves the box collider accordingly
	 * <br>
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable entity, Object... metadata) {
		if(entity instanceof GameObject){
			lastMinPoint = new Point2D(minPoint.x, minPoint.y);
			lastMaxPoint = new Point2D(maxPoint.x,maxPoint.y);
			lastEntityPoint = new Point2D(this.entity.position.x,this.entity.position.y);
			minPoint.x += (float)metadata[0];
			minPoint.y += (float)metadata[1];
			maxPoint.x += (float)metadata[0];
			maxPoint.y += (float)metadata[1];
		}
	}

	@Override
	public Component copyWithNewEntity(Entity e) {
		return new BoxCollider(e,minPoint.x,minPoint.y,maxPoint.x,maxPoint.y);	
	}

}
