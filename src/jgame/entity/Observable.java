package jgame.entity;

import java.util.HashSet;
import java.util.Set;
/**
 * This class represents an observable object.
 * <br>
 * An observable object can be observed by an observer object. The observable object is 
 * responsible for notifying it's observers when an important event has occurred.
 * @author David Almeida
 * @see jgame.entity.Observer
 * @since 1.0
 */
public abstract class Observable{
	private Set<Observer> observers = new HashSet<>();
	
	/**
	 * Notifies all the observers
	 * @since 1.0
	 */
	public void notifyObservers(){
		for(Observer o: observers)
			o.update(this);
	}
	
	/**
	 * Notifies all the observers
	 * @param metadata - the metadata to be transmitted to the respective observers
	 * @since 1.0
	 */
	public void notifyObservers(Object... metadata){
		for(Observer o: observers)
			o.update(this,metadata);
	}
	
	/**
	 * Adds an observer. Note that an observer must override hashCode method
	 * @param observer - the observer to add
	 * @since 1.0
	 */
	public final void addObserver(Observer observer){
		this.observers.add(observer);
	}

	/**
	 * Removes an observer. Note that an observer must override hashCode method
	 * @param observer - the observer to remove
	 * @since 1.2
	 */
	public final void removeObserver(Observer observer){
		this.observers.remove(observer);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((observers == null) ? 0 : observers.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observable other = (Observable) obj;
		if (observers == null) {
			if (other.observers != null)
				return false;
		} else if (!observers.equals(other.observers))
			return false;
		return true;
	}
}
