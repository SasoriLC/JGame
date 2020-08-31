package jgame.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jgame.components.Component;
import jgame.exceptions.InvalidLayerEception;
import jgame.structures.Point2D;
/**
 * An entity represents every object, example: Game Object, Camera. Every entity has its own unique id.
 * @author David Almeida
 * @since 1.0
 */
public abstract class Entity extends Observable {
	
	private static long CURRENT_ID = 0;
	
	private long id;
	public Point2D position;
	private HashMap<String,Component> components;
	protected String name;
	protected int layer;
	
	
	/**
	 * Creates an entity
	 * @since 1.0
	 */
	protected Entity(){
		id = CURRENT_ID++;
		position = new Point2D(0,0);
		components = new HashMap<>();	
		name = "Entity";

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
	
	/**
	 * @return the name
	 * @since 1.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @since 1.0
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param layer the layer to set
	 * @throws InvalidLayerEception if layer < 0
	 * @since 1.0
	 */
	public void setLayer(int layer) throws InvalidLayerEception{
		if(layer < 0)
			throw new InvalidLayerEception();
		this.layer = layer;
	}
	
	/**
	 * @return the entity's layer
	 * @since 1.2
	 */
	public int getLayer(){
		return layer;
	}
	
	/**
	 * 
	 * @return the id of the entity
	 * @since 1.2
	 */
	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return id == other.id;
	}
		
	
}
