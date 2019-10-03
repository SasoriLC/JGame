package jgame.listeners.keyboard;
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
class DefaultKeyListener implements KeyListener{
	private Keyboard keyboard;

	
	/**
	 * 
	 * @param k - the keyboard
	 * @since 1.0
	 */
	public DefaultKeyListener(Keyboard k){
		keyboard = k;
	}
	
	@Override
	public synchronized void keyPressed(KeyEvent event) {
		//keyboard.executeBehavior(event.getKeyCode());
		keyboard.press(event.getKeyCode());
	}

	@Override
	public synchronized void keyReleased(KeyEvent event) {
		GameObject player = Camera.getInstance().getTarget();
		keyboard.executeBehavior(event.getKeyCode());
		player.moveX(0);
		player.moveY(0);
		keyboard.release(event.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent event) {}
	
}
