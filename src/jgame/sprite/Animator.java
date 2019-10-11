package jgame.sprite;

import java.util.ArrayList;
import java.util.List;
/**
 * This class is responsible for handling all the animations.
 * @author David Almeida
 * @since 1.0
 */
final class Animator {
	private List<AnimationsThread> animationsThreads;
	private static final int ANIMATIONS_PER_TIMER = 20;
	private static final Animator INSTANCE = new Animator();
	
	/**
	 * 
	 * @return the instance of the animator
	 * @since 1.0
	 */
	static Animator getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Creates the animator
	 * @since 1.0
	 */
	private Animator(){
		animationsThreads = new ArrayList<>();
		animationsThreads.add(new AnimationsThread());
	}
	
	/**
	 * Adds a new animation to be animated
	 * @param anim the animation
	 * @since 1.0
	 */
	void addAnimation(Animation anim){
		synchronized(animationsThreads){
			AnimationsThread current = animationsThreads.get(animationsThreads.size() - 1);
			
			if(current.getCurrentThreadAnimations() % ANIMATIONS_PER_TIMER == 0){
				current = new AnimationsThread();
				animationsThreads.add(current);
			}	
		
			current.addAnimation(anim);
			anim.addObserver(current);
		}
		
	}
}
