package jgame.sprite;

import java.util.ArrayList;
import java.util.List;

public final class Animator {
	private List<AnimationsThread> animationsThreads;
	private static final int ANIMATIONS_PER_TIMER = 20;
	private static final Animator INSTANCE = new Animator();
	
	public static Animator getInstance(){
		return INSTANCE;
	}
	
	private Animator(){
		animationsThreads = new ArrayList<>();
		animationsThreads.add(new AnimationsThread());
	}
	
	public void addAnimation(Animation anim){
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
