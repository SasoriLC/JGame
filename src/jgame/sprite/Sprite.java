package jgame.sprite;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import jgame.Audio;
import jgame.exceptions.SpriteDoesNotExistException;
import jgame.exceptions.SpriteSequenceInvalid;

/**
 * 
 * This class represents a sprite. <br>Every game object has its own sprite
 * @author David Almeida
 * @see jgame.sprite.Animation
 * @since 1.0
 */
public class Sprite{
	protected BufferedImage image;
	protected BufferedImage[] sprite,sequence;
	protected Animation animation;
	protected int spriteWidth,spriteHeight;
	
	/**
	 * Creates an empty sprite. A null sprite is useful for empty game objects that wrap others.
	 * @since 1.0
	 */
	public Sprite(){}
	
	/**
	 * 
	 * @param spritePath the path of sprite
	 * @param quantity the quantity of images in the sprite
	 * @throws SpriteDoesNotExistException if @spritePath is null
	 * @since 1.0
	 */
	public Sprite(String spritePath, int quantity) throws SpriteDoesNotExistException{
		sprite = new BufferedImage[quantity];
		animation = new Animation(false);
		try {
			image = ImageIO.read(new File(spritePath));

			spriteWidth = image.getWidth() / quantity;
			spriteHeight = image.getHeight();

			for(int i = 0; i < sprite.length; i++)
				sprite[i] = image.getSubimage(i * spriteWidth, 0
						,spriteWidth , spriteHeight); 
			setSequence(0,1,1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a sprite with only one image
	 * @param spritePath the path of sprite
	 * @throws SpriteDoesNotExistException if @spritePath is null
	 * @since 1.0
	 */
	public Sprite(String spritePath) throws SpriteDoesNotExistException{
		this(spritePath,1);
		setSequence(0,1,1);
	}


	/**
	 * 
	 * @param start, the start of the sprite animation
	 * @param end, the end of the sprite animation
	 * @param time the duration of the animation in milliseconds
	 * <p>
	 * start is inclusive and end is exclusive
	 * @throws SpriteDoesNotExistException if the sprite is null
	 * @since 1.0 
	 */
	public void setSequence(int start, int end,long time) throws SpriteDoesNotExistException{
		if(sprite == null)
			throw new SpriteDoesNotExistException();
		
		try{
			if(start < 0 || start > sprite.length-1 || end < start)
				throw new SpriteSequenceInvalid();
			int size = end-start;
			
			sequence = new BufferedImage[size];
			for(int i = 0; i < sequence.length; i++)
				sequence[i] = sprite[start + i];

			//create a new animation
			if(!animation.isRunning())
				animation.startNewAnimation(sequence.length, time);

		}catch(SpriteSequenceInvalid e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param start the start of the sprite animation
	 * @param end the end of the sprite animation
	 * @param time the duration of the animation in milliseconds
	 * @param a the audio to be played during the animation of the sequence
	 * <p>
	 * start is inclusive and end is exclusive
	 * @throws SpriteDoesNotExistException if the sprite is null
	 * @since 1.0 
	 */
	public void setSequence(int start, int end,long time, Audio a) throws SpriteDoesNotExistException{
		animation.setAnimationAudio(a);
		this.setSequence(start, end, time);
	}

	/**
	 * 
	 * @return the current sprite sequence
	 * @see setSequence
	 * @since 1.0
	 */
	public BufferedImage getNextSpriteSequence(){
		return sequence[animation.getCurrentAnimationIndex()];
	}

	/**
	 * 
	 * @return the width of a single sprite
	 * @since 1.0
	 */
	public int getSpriteWidth(){
		return spriteWidth;
	}

	/**
	 * 
	 * @return the height of a single sprite
	 * @since 1.0
	 */
	public int getSpriteHeight(){
		return spriteHeight;
	}

	/**
	 * @return the animation
	 */
	public Animation getAnimation() {
		return animation;
	}
	
	/**
	 * Sets a new size for the sprite
	 * @param width the new width
	 * @param height the new height
	 * @since 1.0
	 */
	public void setSpriteSize(int width,int height){
		spriteWidth = width;
		spriteHeight = height;
		if(image != null){
			spriteWidth /= sprite.length;
			image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			for(int i = 0; i < sprite.length; i++)
				sprite[i] = image.getSubimage(i * spriteWidth, 0
						,spriteWidth , spriteHeight); 
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((animation == null) ? 0 : animation.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + Arrays.hashCode(sequence);
		result = prime * result + Arrays.hashCode(sprite);
		result = prime * result + spriteHeight;
		result = prime * result + spriteWidth;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sprite other = (Sprite) obj;
		if (animation == null) {
			if (other.animation != null)
				return false;
		} else if (!animation.equals(other.animation))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (!Arrays.equals(sequence, other.sequence))
			return false;
		if (!Arrays.equals(sprite, other.sprite))
			return false;
		if (spriteHeight != other.spriteHeight)
			return false;
		if (spriteWidth != other.spriteWidth)
			return false;
		return true;
	}
}
