package jgame.exceptions;
/**
 * This class represents an abstract sprite exception
 * @author David Almeida
 * @since 1.0
 */
public abstract class SpriteException extends JGameException{

	private static final long serialVersionUID = 3096434659252431466L;

	/**
	 * {@inheritDoc}
	 */
	public SpriteException(String message) {
		super(message);
	}
	
}
