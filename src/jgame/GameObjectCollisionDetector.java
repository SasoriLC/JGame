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
			
			for(Tile tile: tiles){
				if(tile.getTileType().equals(TileType.WALL)){
					if(o.collided(tile)){
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
							if(tile.getX() > o.position.x + o.getSprite().getSpriteWidth() - 2){
								o.position.x = tile.getX() - o.getSprite().getSpriteWidth();
							}else{
								o.position.x = tile.getX() + tile.getWidth();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void update(Observable entity, Object... metadata) {
		//TODO
	}
}
