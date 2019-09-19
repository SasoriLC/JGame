package jgame.sprite;

import java.util.Timer;
import java.util.TimerTask;
/**
 * This class is responsible for changing the indexes of the sprite's sequence 
 * in order to make it an animation
 * @author David Almeida
 * @since 1.0
 */
public class Animation {
	
	private Timer timer;
	private TimerTask timerTask;
	private int currentIndex = 0;
	private int maxIndex;
	private boolean isRunning;
	
	
	/**
	 * Creates a new animation
	 * @since 1.0
	 */
	public Animation (){
		timer = new Timer();
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
	public int getCurrentAnimationIndex(){
		if(currentIndex == maxIndex){
			currentIndex = 0;
			isRunning = false;
			timerTask.cancel();
		}
		return currentIndex;
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
	public void startNewAnimation(int maxIndex,long time){
		isRunning = true;
		this.maxIndex = maxIndex;
		createNewSpriteChangeTask(maxIndex);
		timer.scheduleAtFixedRate(timerTask,0, time);
	}
	
	/**
	 * 
	 * @param maxIndex the maximum index of the animation, that is, the maximum 
	 * number of indexes that animation as to go through
	 * <p>
	 * Creates a new timer task for the animation, this method will be called 
	 * when starting a new animation
	 * @since 1.0
	 */
	private void createNewSpriteChangeTask(int maxIndex){
		timerTask = new TimerTask(){
			@Override
			public void run(){
				currentIndex++;
				if(currentIndex == maxIndex){
					currentIndex = 0;
					isRunning = false;
					timerTask.cancel();
				}

			}
		};
	}
	
}
