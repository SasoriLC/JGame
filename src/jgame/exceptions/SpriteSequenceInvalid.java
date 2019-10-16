package jgame.exceptions;
/**
 * This exception is thrown when a sprite sequence is invalid
 * @author David Almeida
 * @since 1.0
 */
public class SpriteSequenceInvalid extends SpriteException{

	private static final long serialVersionUID = -5142695251558515955L;

	/**
	 * Creates the exception
	 * @since 1.0
	 */
	public SpriteSequenceInvalid(){
		super("The sequence is not valid. The start should be between"
				+ " zero and the maximum sprite sequence number,the end\n"
				+ "between the start and the maximum sprite sequence number"); 
	}
}
