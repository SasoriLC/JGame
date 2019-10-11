package jgame.sprite;

import jgame.Audio;
import jgame.entity.Observable;
/**
 * This class is responsible for changing the indexes of the sprite's sequence 
 * in order to make it an animation
 * @author David Almeida
 * @since 1.0
 */
public class Animation extends Observable{

	private int currentIndex = 0;
	private int maxIndex;
	private boolean isRunning;
	private boolean loop;
	private long lastTImeExecuted;
	private Audio audio;
	private long time;
	//in order to distinct different animations every animation needs an id
	private long id;
	private static long numberOfAnimations; 

	/**
	 * Creates a new animation
	 * @loop true if the animation is supposed to loop
	 * @since 1.0
	 */
	Animation (boolean loop){
		this.loop = loop;
		id = numberOfAnimations++;
	}
	/**
	 * 
	 * @return true if the animation is still running
	 * @since 1.0
	 */
	public boolean isRunning(){
		return isRunning;
	}

	/**
	 * 
	 * @return the current index of the animation
	 * @since 1.0
	 */
	int getCurrentAnimationIndex(){
		if(currentIndex == maxIndex){
			stop();
		}
		return currentIndex;
	}

	/**
	 * Stops the animation
	 * @since 1.0
	 */
	public void stop(){
		currentIndex = 0;
		isRunning = false;
		if(audio != null)
			audio.stop();
		this.notifyObservers();
	}

	/**
	 * 
	 * @param maxIndex the maximum index of the animation, that is, the maximum 
	 * number of indexes that animation as to go through
	 * @param time the duration of the animation
	 * <p>
	 * Starts the animation
	 * @since 1.0
	 */
	void startNewAnimation(int maxIndex,long time){
		if(!isRunning){
			isRunning = true;
			this.maxIndex = maxIndex;
			this.time = time;
			Animator.getInstance().addAnimation(this);
			
			//the audio should be played when the animation begins 
			if(audio != null)
				audio.play();
		}
	}

	/**
	 * 
	 * <p>
	 * Plays the animation if it is necessary
	 * @since 1.0
	 */
	void play(){
		if(lastTImeExecuted == 0 || System.currentTimeMillis() - lastTImeExecuted >= time/maxIndex){
			currentIndex++;
			if(currentIndex == this.maxIndex && !loop){
				currentIndex = 0;
				isRunning = false;
				if(audio != null)
					audio.stop();
				
				this.notifyObservers();
			}else if(currentIndex==this.maxIndex){
				currentIndex = 0;
			}
			lastTImeExecuted = System.currentTimeMillis();
		}
	}

	/**
	 * Sets an audio to be played alongside with the animation
	 * @param audio The audio
	 * @since 1.0
	 */
	public final void setAnimationAudio(Audio audio){
		//this.audio = audio;
	}

	/**
	 * @return the maxIndex
	 * @since 1.0
	 */
	int getMaxIndex() {
		return maxIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(o instanceof Animation)
			return ((Animation)o).id == id;
		return false;
	}
}
