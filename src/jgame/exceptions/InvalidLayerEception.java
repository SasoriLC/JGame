package jgame.exceptions;
/**
 * This exception is must be thrown when the user enters an invalid layer for an entity (less than 0)
 * @author David Almeida
 * @since 1.2
 */
public class InvalidLayerEception extends JGameException{

	private static final long serialVersionUID = 1429821046007419908L;

	/**
	 * 	{@inheritDoc}
	 */
	public InvalidLayerEception() {
		super("The given layer is not valid. An entity must be in a layer greater or equal than 0!");
	}


	
}
