package jgame.exceptions;

/**
 * This exception is thrown when there is an attempt to set a sequence in a non existing sprite 
 * (null sprite)
 * @author David Almeida
 * @since 1.0
 */
public class SpriteDoesNotExistException extends SpriteException{

	private static final long serialVersionUID = -5018064789407219239L;

	/**
	 * 	{@inheritDoc}
	 */
	public SpriteDoesNotExistException() {
		super("You can't set a sequence in a null sprite. Please create a game object with "
				+ "an existing sprite.");
	}

}
