package jgame.sprite;
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
	 * @since 1.0
	 */
	public LoopingSprite(String spritePath, int quantity,long time) {
		super(spritePath, quantity);
		this.setSequence(0, quantity, time);
		animation = new Animation(true);
		animation.startNewAnimation(quantity, time);
	}
}
