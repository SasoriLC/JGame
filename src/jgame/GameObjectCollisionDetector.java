package jgame;

import java.util.List;

import jgame.entity.GameObject;
import jgame.entity.Observable;
import jgame.entity.Observer;
import jgame.sprite.Sprite;
import jgame.structures.Point2D;
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
		
			
			for(Tile tile: tiles){
				if(tile.getTileType().equals(TileType.WALL)){
					if(o.collided(tile)){
						pushGameObjectFromTile(o,tile);
					}
				}
			}
		}
	}

	private void pushGameObjectFromTile(GameObject o, Tile tile){
		boolean verticalCollision = !(tile.getX() + tile.getWidth() <= o.position.x)
				&& !(o.position.x + o.getSprite().getSpriteWidth() <= tile.getX());

		if(verticalCollision){
			if(tile.getY() + tile.getHeight() - 2 < o.position.y ){
				o.position.y = tile.getY() + tile.getHeight();
			}else if(tile.getY() > o.position.y + o.getSprite().getSpriteHeight() - 2){
				o.position.y = tile.getY() - o.getSprite().getSpriteHeight();
			}
		}


		boolean horizontalCollision = (!(tile.getY() + tile.getHeight() <= o.position.y))
				&& !(o.position.y + o.getSprite().getSpriteHeight() <= tile.getY());

		if(horizontalCollision){
			if(tile.getX() > o.position.x + o.getSprite().getSpriteWidth() - 2){ //right
				o.position.x = tile.getX() - o.getSprite().getSpriteWidth();
			}else{ //left
				o.position.x = tile.getX() + tile.getWidth();
			}
		}
	}
	
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
