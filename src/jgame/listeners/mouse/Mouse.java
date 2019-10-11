package jgame.listeners.mouse;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jgame.Window;
/**
 * This class represents a mouse. A mouse as its own listener that specifies what should be 
 * done when a certain action occurs, for example when a button is clicked.
 * Note that a mouse should be added to the main window in order to actually behave as expected.
 * @author David Almeida
 * @since 1.0
 */
public class Mouse{

	private MouseListener listener;
	
	/**
	 * Sets a new mouse cursor
	 * @param path the file's path
	 * @param cursorName the name of the cursor
	 * @throws IOException if the file is not found
	 * @since 1.0
	 */
	public void setMouseCursor(String path, String cursorName) throws IOException{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage image = ImageIO.read(new File(path));
		Cursor newCursor = toolkit.createCustomCursor(image, 
				new Point(image.getWidth(), image.getHeight()), cursorName);
		Window.getInstance().setCursor(newCursor);
	}
	
	/**
	 * Sets the listener for this mouse
	 * @param m the listeners
	 * @since 1.0
	 */
	public void setMouseListener(MouseListener m){
		listener = m;
	}
	
	/**
	 * 
	 * @return the current mouse's listener
	 * @since 1.0
	 */
	public MouseListener getMouseListener(){
		return listener;
	}
	
}
