package jgame;

import java.util.List;
import jgame.entity.GameObject;
import jgame.entity.Observable;
import jgame.entity.Observer;
import jgame.tile.Tile;
import jgame.tile.TileType;
/**
 * This class is responsible for detecting a the collision of a game object.
 * AABB collision is used as algorithm.
 * @author David Almeida
 * @since 1.0
 */
public class GameObjectCollisionDetector implements Observer{

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
				if(tile.getTileType().equals(TileType.WALL)){
					if(o.collided(tile)){
						pushGameObjectFrom(o,tile.getX(),tile.getY(),
								tile.getX() + tile.getWidth()
								, tile.getY() + tile.getHeight());
					}
				}
			}
			
			for(GameObject g: gos){
				if(o.collided(g) && g.isCollidable()){
					pushGameObjectFrom(o, (int)g.position.x, (int)g.position.y
							, (int)g.position.x + g.getSprite().getSpriteWidth()
							, (int)g.position.y + g.getSprite().getSpriteHeight());
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
//		float velocityX = (float)metadata[0];
//		float velocityY = (float)metadata[1];
//		GameObject o = (GameObject) entity;
//		
//		Scene currentScene = Window.getInstance().getScene();
//		
//		GameObject g = new GameObject(new Sprite("Sprites/sprite.png"
//				,16),(int)(o.position.x + velocityX),(int)(o.position.y + velocityY));
//		
//		
//		List<Tile> tiles = currentScene.getGameObjectCurrentTiles(o);
//		List<Tile> aux = currentScene.getClosestTileTypeFrom((int)g.position.x
//				,(int)g.position.x + g.getSprite().getSpriteWidth()
//				,(int)g.position.y
//				, (int)g.position.y + g.getSprite().getSpriteHeight(), TileType.WALL);
//		
//		
//		aux.forEach(t ->{
//				if(o.collided(t))
//					pushGameObjectFromTile(o,t);
//		});
//		System.out.println("My game object: " + o.position.x + "/" + o.position.y);
//		System.out.println("Future game object: " + g.position.x + "/" + g.position.y);
//
//		//o.position = g.position;
	}
}
