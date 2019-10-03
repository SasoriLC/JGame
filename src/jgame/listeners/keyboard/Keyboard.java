package jgame.listeners.keyboard;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import jgame.Behavior;
import jgame.Window;
import jgame.entity.Camera;
import jgame.entity.GameObject;

public class Keyboard {

	private Map<Integer,Behavior> keyBehaviors = new HashMap<>();
	private KeyListener listener = new DefaultKeyListener(this);
	private KeyboardInputManager inputManager;
    
    public Keyboard(){
    	inputManager = new KeyboardInputManager(this);
    }

	/**
	 * 
	 * @param keyCode
	 * @param behavior - the behavior to implement when the key is pressed/clicked
	 * @since 1.0
	 */
	public void addBehavior(int keyCode, Behavior behavior){
		keyBehaviors.put(keyCode, behavior);
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
	 * 
	 * @param keyCode
	 * <p>
	 * The behavior associated to the key code
	 * @since 1.0
	 */
	Behavior getBehavior(int keyCode){
		return keyBehaviors.get(keyCode);
	}
	
	public void press(int keyCode){
		inputManager.keyPressed(keyCode);
	}
	
	public void release(int keyCode){
		inputManager.keyReleased(keyCode);
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
