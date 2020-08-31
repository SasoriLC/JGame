package jgame.exceptions;

/**
 * 
 * This exception is throw if an entity is being added twice to the same scene
 * @author David Almeida
 * @since 1.2
 */
public class EntityIsAlreadyInSceneException extends JGameException {

	
	private static final long serialVersionUID = 5786301564331226310L;

	/**
	 * {@inheritDoc}
	 */
	public EntityIsAlreadyInSceneException() {
		super("The given entity is already part of this scene.");
	}



}
