package jgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jgame.entity.Camera;
import jgame.entity.GameObject;
/**
 * This class represents adds a level of indirection between the keyboard and the window 
 * @author David Almeida
 * @since 1.0
 * @see jgame.Window and jgame.Keyboard
 */
class KeyboardWrapper implements KeyListener{
	private Keyboard keyboard;
	
	/**
	 * 
	 * @param k - the keyboard
	 * @since 1.0
	 */
	public KeyboardWrapper(Keyboard k){
		keyboard = k;
	}
	
	@Override
	public synchronized void keyPressed(KeyEvent event) {
		keyboard.executeBehavior(event.getKeyCode());
	}

	@Override
	public synchronized void keyReleased(KeyEvent event) {
		Camera cam = Camera.getInstance();
		GameObject player = cam.getTarget();
		keyboard.executeBehavior(event.getKeyCode());
		player.moveX(0);
		player.moveY(0);
	}

	@Override
	public void keyTyped(KeyEvent event) {}
	
}
