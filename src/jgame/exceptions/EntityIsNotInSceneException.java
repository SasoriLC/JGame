package jgame.exceptions;

/**
 * This exception is throw if an entity that is not in scene is being deleted from it
 * @author David Almeida
 * @since 1.2
 */
public class EntityIsNotInSceneException extends JGameException {

	private static final long serialVersionUID = 877993832097600409L;

	/**
	 * {@inheritDoc}
	 */
	public EntityIsNotInSceneException() {
		super("The given entity is not in the scene therefore you can't delete it.");
	}



}
