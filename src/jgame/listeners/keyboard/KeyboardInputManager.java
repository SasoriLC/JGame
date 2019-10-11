package jgame.listeners.keyboard;
import java.util.HashMap;
import jgame.Behavior;
import jgame.structures.time.InfiniteTimer;
/**
 * This class handles all the keyboard input. It implements an internal timer that each 50 
 * milliseconds checks if there are keys being pressed or released and executes the given 
 * tasks associated with that behaviors (if there are any).
 * @author David Almeida
 * @since 1.0
 */
class KeyboardInputManager {
	private HashMap<Integer,Behavior> keysBeingPressed;
	private Keyboard keyboard;
	private int lastKey;
	

	/**
	 * Creates the input manager
	 * @param k the keyboard
	 * @since 1.0
	 */
	KeyboardInputManager(Keyboard k){
		keysBeingPressed = new HashMap<>();
		keyboard = k;
		new InfiniteTimer(50, () -> {
			synchronized(keysBeingPressed){
				keysBeingPressed.forEach((key,b) -> b.run());
			}
		}).start();
	}

	/**
	 * Executes the press behavior, if there is any, associated with the key code
	 * @param k the key code
	 * @since 1.0
	 */
	void keyPressed(int k){
		synchronized(keysBeingPressed){
			if(!keyboard.supportsMutipleKeys() && lastKey != k){
				Behavior b = keyboard.getReleaseBehavior(lastKey);
				if(b != null) b.run();
				keysBeingPressed.remove(lastKey);
			}
				
			Behavior b = keyboard.getPressedBehavior(k);
			if(b != null)
				keysBeingPressed.put(k, b);
			
			lastKey = k;
		}
	}

	/**
	 * Executes the release behavior, if there is any, associated with the key code
	 * @param k the key code
	 * @since 1.0
	 */
	void keyReleased(int k){
		synchronized(keysBeingPressed){
			Behavior b = keyboard.getReleaseBehavior(k);
			if(b != null)
				b.run();
			keysBeingPressed.remove(k);
		}
	}

}
