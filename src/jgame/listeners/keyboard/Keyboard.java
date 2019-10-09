package jgame.listeners.keyboard;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import jgame.Behavior;

public class Keyboard {

	private Map<Integer,Behavior> keyPressedBehaviors = new HashMap<>();
	private Map<Integer,Behavior> keyReleasedBehaviors = new HashMap<>();
	private KeyListener listener = new DefaultKeyListener(this);
	private KeyboardInputManager inputManager;
	private boolean mutipleKeys;
    
	/**
	 * 
	 * @param mutipleKeys true if the keyboard should read multiple inputs
	 */
    public Keyboard(boolean mutipleKeys){
    	inputManager = new KeyboardInputManager(this);
    	this.mutipleKeys = mutipleKeys;
    }

	/**
	 * 
	 * @param keyCode
	 * @param behavior - the behavior to execute when the key is pressed/clicked
	 * @since 1.0
	 */
	public void addPressBehavior(int keyCode, Behavior behavior){
		keyPressedBehaviors.put(keyCode, behavior);
	}
	
	/**
	 * 
	 * @param keyCode
	 * @param behavior - the behavior to execute when the key is released
	 * @since 1.0
	 */
	public void addReleaseBehavior(int keyCode, Behavior behavior){
		keyReleasedBehaviors.put(keyCode, behavior);
	}
	
	/**
	 * 
	 * @param keyCode
	 * <p>
	 * The behavior associated when the key with key code is pressed
	 * @since 1.0
	 */
	Behavior getPressedBehavior(int keyCode){
		return keyPressedBehaviors.get(keyCode);
	}
	
	/**
	 * 
	 * @param keyCode
	 * <p>
	 * The behavior associated when the key with key code is released
	 * @since 1.0
	 */
	Behavior getReleaseBehavior(int keyCode){
		return keyReleasedBehaviors.get(keyCode);
	}
	
	public void press(int keyCode){
		inputManager.keyPressed(keyCode);
	}
	
	public void release(int keyCode){
		inputManager.keyReleased(keyCode);
	}
	
	/**
	 * @return true if the keyboard supports multiple inputs mutipleKeys
	 */
	public boolean supportsMutipleKeys() {
		return mutipleKeys;
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
