package jgame.exceptions;

import java.io.IOException;
/**
 * This exception is thrown when the file specified for a certain tile is not found
 * @author David Almeida
 * @since 1.0
 */
public class TileNotFoundException extends IOException{

	private static final long serialVersionUID = 1L;

	public TileNotFoundException(String tile){
		super("Could not create tile from " + tile + "\n"
				+ " because the file does not exists");
	}
}
