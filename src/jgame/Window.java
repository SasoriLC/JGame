package jgame;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.JFrame;

/**
 * 
 * This class represents a window.
 * <br>
 * A window is use to representing a scene
 * @author David Almeida
 * @since 1.0
 */
@SuppressWarnings("serial")
public class Window extends JFrame{
	private static Window instance;

	private Scene scene;
	//private BufferStrategy buffer;
	//private Graphics graphics;

	/**
	 * 
	 * @param scene the scene
	 * @param width the width of the window
	 * @param height the height of the window
	 * @since 1.0
	 */
	public Window(Scene scene, int width, int height){
		super();
		this.scene = scene;
		setUndecorated(true);
		setSize(width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setAutoRequestFocus(true);
		setContentPane(scene);
		setVisible(true);
//		createBufferStrategy(3);
//		buffer = getBufferStrategy();
//		graphics = buffer.getDrawGraphics();
		
		instance = this;
	}

	/**
	 * 
	 * @return the scene that the window is representing
	 * @since 1.0
	 */
	public Scene getScene(){
		return scene;
	}
	
	/**
	 * 
	 * @return the window's instance
	 * @since 1.0
	 */
	public static Window getInstance(){
		return instance;
	}
	
	/**
	 * 
	 * @param keyboard the keyboard to set
	 * @since 1.0
	 */
	public void setKeyboard(Keyboard keyboard){
		if(this.getKeyListeners().length == 1) //remove the last keyboard 
			this.removeKeyListener(this.getKeyListeners()[0]);
		this.addKeyListener(new KeyboardWrapper(keyboard));		
	}
	
	/**
	 * 
	 * @param mouse the mouse to set
	 * @since 1.0
	 */
	public void setMouse(Mouse mouse){
		if(this.getMouseListeners().length == 1) //remove the last mouse
			this.removeMouseListener(this.getMouseListeners()[0]);
		this.addMouseListener(mouse.getListener());		
	}
	
//	public void update(){
//		//buffer.show();
//		graphics.dispose();
//		Toolkit.getDefaultToolkit().sync();
//		//graphics = buffer.getDrawGraphics();
//	}
//
//	/**
//	 * @return the graphics of the window
//	 * @since 1.0
//	 */
//	public Graphics getGraphics(){
//		return graphics;
//	}

}
