package jgame.sprite;
import java.util.ArrayList;
import jgame.entity.Observable;
import jgame.entity.Observer;
import jgame.structures.time.InfiniteTimer;

class AnimationsThread implements Observer{
	private int currentThreadAnimations;
	private ArrayList<Animation> animations;


	AnimationsThread(){
		animations = new ArrayList<>();
		
		new InfiniteTimer(50, 
				() -> {
					synchronized(animations){
						//to prevent concurrent modifications
						ArrayList<Animation> anims = (ArrayList<Animation>)animations.clone();
						for(Animation a: anims)
							a.play();
				}
				}).start();;
	}

	
	
	void addAnimation(Animation animation){
		synchronized(animations){
			animations.add(animation);
			currentThreadAnimations++;
		}
	}

	/**
	 * @return the current number of animations that this thread has 
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
