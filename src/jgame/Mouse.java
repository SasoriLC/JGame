package jgame;
import java.awt.PointerInfo;
import java.awt.event.MouseListener;
/**
 * 
 * @author David Almeida
 * TODO
 */
public class Mouse{

	private MouseListener listener;
	
	public void setListener(MouseListener m){
		listener = m;
	}
	
	public MouseListener getListener(){
		return listener;
	}
}
