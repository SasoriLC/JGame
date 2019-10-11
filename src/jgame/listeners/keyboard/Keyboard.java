package jgame.listeners.keyboard;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import jgame.Behavior;

/**
 * This class represents a keyboard. A keyboard as its own listener that specifies what should be 
 * done when a certain action occurs, for example a button is clicked.
 * <br>
 * Note that it is highly recommended to not change the keyboard's listener(it has a default one).
 * If the keyboard's listener is not implemented carefully it can cause some issues such as input delay,
 * non expected behaviors, etc
 * A keyboard should be added to the main window in order to actually behave as expected.
 * @author David Almeida 
 * @since 1.0
 */
public class Keyboard {

	private Map<Integer,Behavior> keyPressedBehaviors = new HashMap<>();
	private Map<Integer,Behavior> keyReleasedBehaviors = new HashMap<>();
	private KeyListener listener = new DefaultKeyListener(this);
	private KeyboardInputManager inputManager;
	private boolean mutipleKeys;
    
	/**
	 * 
	 * @param mutipleKeys true if the keyboard should read multiple inputs
	 * @since 1.0
	 */
    public Keyboard(boolean mutipleKeys){
    	inputManager = new KeyboardInputManager(this);
    	this.mutipleKeys = mutipleKeys;
    }

	/**
	 * 
	 * @param keyCode the key code of the key
	 * @param behavior the behavior to execute when the key is pressed/clicked
	 * @since 1.0
	 */
	public void addPressBehavior(int keyCode, Behavior behavior){
		keyPressedBehaviors.put(keyCode, behavior);
	}
	
	/**
	 * 
	 * @param keyCode the key code of the key
	 * @param behavior the behavior to execute when the key is released
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
	
	/**
	 * Notifies the input manager that a certain key was pressed
	 * @param keyCode the key code of the pressed key
	 * @since 1.0
	 */
	void press(int keyCode){
		inputManager.keyPressed(keyCode);
	}
	
	/**
	 * Notifies the input manager that a certain key was released
	 * @param keyCode the key code of the released key
	 * @since 1.0
	 */
	void release(int keyCode){
		inputManager.keyReleased(keyCode);
	}
	
	/**
	 * @return true if the keyboard supports multiple inputs mutipleKeys
	 * @since 1.0
	 */
	public boolean supportsMutipleKeys() {
		return mutipleKeys;
	}
	
	
	/**
	 * @return the key listener
	 * @since 1.0
	 */
	public KeyListener getKeyListener() {
		return listener;
	}

	/**
	 * It is highly recommended to not change the keyboard's listener. Only do it if it is 
	 * absolutely necessary.
	 * @param listener the key listener to set
	 * @since 1.0
	 */
	public void setKeyListener(KeyListener listener) {
		this.listener = listener;
	}	
}
