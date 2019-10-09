package jgame.listeners.keyboard;
import java.util.HashMap;
import jgame.Behavior;
import jgame.structures.time.InfiniteTimer;

class KeyboardInputManager {
	private HashMap<Integer,Behavior> keysBeingPressed;
	private Keyboard keyboard;


	KeyboardInputManager(Keyboard k){
		keysBeingPressed = new HashMap<>();
		keyboard = k;
		new InfiniteTimer(50, () -> {
			synchronized(keysBeingPressed){
				keysBeingPressed.forEach((key,b) -> b.run());
			}
		}).start();
	}

	void keyPressed(int k){
		synchronized(keysBeingPressed){
			Behavior b = keyboard.getPressedBehavior(k);
			if(b != null)
				keysBeingPressed.put(k, b);
		}
	}

	void keyReleased(int k){
		synchronized(keysBeingPressed){
			Behavior b = keyboard.getReleaseBehavior(k);
			if(b != null)
				b.run();
			keysBeingPressed.remove(k);
		}
	}

}
