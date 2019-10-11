package jgame.sprite;
import java.util.ArrayList;
import jgame.entity.Observable;
import jgame.entity.Observer;
import jgame.structures.time.InfiniteTimer;

/**
 * This class is responsible for handling mutiple animations in a single thread 
 * (implemented with a timer)
 * @author David Almeida
 * @since 1.0
 */
class AnimationsThread implements Observer{
	private int currentThreadAnimations;
	private ArrayList<Animation> animations;


	/**
	 * Creates a timer that will execute animations every 50 milliseconds. Note that the animations 
	 * are only played when necessary
	 * @since 1.0
	 */
	AnimationsThread(){
		animations = new ArrayList<>();
		
		new InfiniteTimer(50, 
				() -> {
					synchronized(animations){
						//to prevent concurrent modifications
						@SuppressWarnings("unchecked")
						ArrayList<Animation> anims = (ArrayList<Animation>)animations.clone();
						for(Animation a: anims)
							a.play();
				}
				}).start();;
	}

	
	/**
	 * Adds an animation to this thread to execute
	 * @param animation the animation to add
	 * @since 1.0
	 */
	void addAnimation(Animation animation){
		synchronized(animations){
			animations.add(animation);
			currentThreadAnimations++;
		}
	}

	/**
	 * @return the current number of animations that this thread has 
	 * @since 1.0
	 */
	public int getCurrentThreadAnimations() {
		return currentThreadAnimations;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable entity) {
		//this thread (timer) is only notified when an animation ends
		//so we must remove it
		if(entity instanceof Animation){
			animations.remove((Animation)entity);
			currentThreadAnimations--;	
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable entity, Object... metadata) {
		//there is no necessity to implement this method since it is never going to be called
	}
}
