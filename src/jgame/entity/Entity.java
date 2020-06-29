package jgame.entity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import jgame.components.Component;
import jgame.structures.Point2D;
/**
 * An entity represents every object, example: Game Object, Camera.
 * @author David Almeida
 * @since 1.0
 */
public abstract class Entity extends Observable{
	public Point2D position;
	private HashMap<String,Component> components;
	
	
	/**
	 * Creates an entity
	 * @since 1.0
	 */
	protected Entity(){
		position = new Point2D(0,0);
		components = new HashMap<>();	
	}
	
	public void addComponent(Component component){
		this.addObserver(component);
		components.put(component.getComponentName(),component);
	}
	
	public List<Component> getComponents(){
		return components.values().stream().collect(Collectors.toList());
	}
	
	public boolean hasComponent(String component){
		return components.get(component) != null;
	}
	
	public Component getComponent(String component){
		return components.get(component);
	}
		
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((components == null) ? 0 : components.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		Entity other = (Entity) obj;
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	
}
