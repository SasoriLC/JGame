package jgame.exceptions;

/**
 * This is a generic exception for all JGame library's exception
 * @author David Almeida
 * @since 1.2
 */
public class JGameException extends Exception{

	
	private static final long serialVersionUID = -7641800386195671614L;

	/**
	 * 
	 * {@inheritDoc}
	 */
	public JGameException(String message) {
		super(message);
	}

}
