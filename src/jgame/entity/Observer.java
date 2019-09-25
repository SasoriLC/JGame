package jgame.entity;
/**
 * This class represents an observer object.
 * <br>
 * An observer object is responsible for handling an event that has occurred in the observable 
 * object.
 * @author David Almeida
 * @see jgame.entity.Observable
 * @since 1.0
 */
public interface Observer {
	
	/**
	 * This method is called when an event has occurred in one of the observable object
	 * (entity or component)
	 * @param entity - the observable object
	 */
	public void update(Observable entity);
	
	/**
	 * This method is called when an event has occurred in one of the observable object
	 * (entity or component)
	 * @param entity - the observable object
	 * @param metadata - the metadata to be transmitted 
	 */
	public void update(Observable entity, Object... metadata);
}
