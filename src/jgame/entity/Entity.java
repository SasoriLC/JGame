package jgame.entity;

import java.util.HashSet;
import java.util.Set;
import jgame.component.Component;
import jgame.structures.Point2D;
/**
 * An entity represents every object, example: Game Object, Camera.
 * An entity is NOT a component.
 * @author David Almeida
 * @see jgame.component.Component
 * @since 1.0
 */
public abstract class Entity extends Observable{
	public Point2D position;
	protected Set<Component> components;
	
	/**
	 * Creates an entity
	 * @since 1.0
	 */
	protected Entity(){
		components = new HashSet<>(); 
	}
	
	/**
	 * 
	 * @param c - the component to add
	 * @since 1.0
	 */
	public final void addComponent(Component c){
		components.add(c);
	}
	
	/**
	 * 
	 * @return the components of this entity
	 * @since 1.0
	 */
	public final Iterable<Component> getComponents(){
		return components;
	}
	
}
