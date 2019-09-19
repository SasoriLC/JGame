package jgame.exceptions;

import java.io.IOException;
/**
 * TODO
 * @author David Almeida
 * @since 1.0
 */
public class TileNotFound extends IOException{

	private static final long serialVersionUID = 1L;

	public TileNotFound(String tile){
		super("Could not create tile from " + tile + "\n"
				+ " because the file does not exists");
	}
}
