package jgame.listeners.keyboard;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void keyPressed(KeyEvent event) {
		keyboard.press(event.getKeyCode());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void keyReleased(KeyEvent event) {
		keyboard.release(event.getKeyCode());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyTyped(KeyEvent event) {}
	
}
