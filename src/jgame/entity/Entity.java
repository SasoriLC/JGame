package jgame.entity;

import jgame.structures.Point2D;
/**
 * An entity represents every object, example: Game Object, Camera.
 * @author David Almeida
 * @since 1.0
 */
abstract class Entity extends Observable{
	public Point2D position;
	
	/**
	 * Creates an entity
	 * @since 1.0
	 */
	protected Entity(){}
		
}
