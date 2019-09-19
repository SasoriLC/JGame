package jgame.exceptions;
/**
 * TODO
 * @author David Almeida
 * @since 1.0
 */
public class SpriteSequenceInvalid extends SpriteException{
	public SpriteSequenceInvalid(){
		super("The sequence is not valid. The start should be between"
				+ " zero and the maximum sprite sequence number,the end\n"
				+ "between the start and the maximum sprite sequence number"); 
	}
}
