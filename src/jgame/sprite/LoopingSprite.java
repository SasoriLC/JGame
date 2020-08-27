package jgame.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jgame.exceptions.SpriteDoesNotExistException;

/**
 * This sprite represents a sprite that is going to be animated forever
 * @author David Almeida
 * @see jgame.sprite.Animation
 * @see jgame.sprite.Sprite
 * @since 1.0
 */
public class LoopingSprite extends Sprite{

	/**
	 * Creates a looping sprite
	 * @param spritePath the path of sprite
	 * @param quantity the quantity of images in the sprite
	 * @param time the time of a single animation
	 * @throws SpriteDoesNotExistException if @spritePath is null
	 * @throws IOException 
	 * @since 1.0
	 */
	public LoopingSprite(String spritePath, int quantity,long time) throws SpriteDoesNotExistException, IOException{
		this(ImageIO.read(new File(spritePath)), quantity,time);
	}
	
	/**
	 * Creates a looping sprite
	 * @param spritePath the path of sprite
	 * @param quantity the quantity of images in the sprite
	 * @param time the time of a single animation
	 * @throws SpriteDoesNotExistException if @spritePath is null
	 * @since 1.0
	 */
	public LoopingSprite(BufferedImage img, int quantity,long time) throws SpriteDoesNotExistException{
		super(img, quantity);
		this.setSequence(0, quantity, time);
		animation = new Animation(true);
		animation.startNewAnimation(quantity, time);
	}
}
