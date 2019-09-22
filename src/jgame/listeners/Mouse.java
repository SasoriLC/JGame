package jgame.listeners;
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
 * 
 * @author David Almeida
 * TODO
 */
public class Mouse{

	private MouseListener listener;
	
	public void setMouseCursor(String path, String cursorName) throws IOException{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage image = ImageIO.read(new File(path));
		Cursor newCursor = toolkit.createCustomCursor(image, 
				new Point(image.getWidth(), image.getHeight()), cursorName);
		Window.getInstance().setCursor(newCursor);
	}
	
	public void setMouseListener(MouseListener m){
		listener = m;
	}
	
	public MouseListener getMouseListener(){
		return listener;
	}
	
}
