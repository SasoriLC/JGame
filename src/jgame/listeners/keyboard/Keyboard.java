package jgame.listeners.keyboard;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import jgame.Behavior;

public class Keyboard {

	private Map<Integer,Behavior> keyBehaviors = new HashMap<>();
	private KeyListener listener = new DefaultKeyListener(this);
	
	/**
	 * 
	 * @param keyCode
	 * @param behaviour - the behavior to implement when the key is pressed/clicked
	 * @since 1.0
	 */
	public void addBehavior(int keyCode, Behavior behaviour){
		keyBehaviors.put(keyCode, behaviour);
	}
	
	/**
	 * 
	 * @param keyCode
	 * <p>
	 * Executes the behavior of the given key code 
	 * @since 1.0
	 */
	void executeBehavior(int keyCode){
		if(keyBehaviors.get(keyCode) != null)
			keyBehaviors.get(keyCode).run();
	}
	
	
	/**
	 * @return the key listener
	 */
	public KeyListener getKeyListener() {
		return listener;
	}

	/**
	 * @param listener the key listener to set
	 */
	public void setKeyListener(KeyListener listener) {
		this.listener = listener;
	}
	
}
