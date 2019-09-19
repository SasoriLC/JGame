package jgame;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {

	private Map<Integer,Behavior> keyBehaviors = new HashMap<>();
	
	
	/**
	 * 
	 * @param keyCode
	 * @param behaviour - the behavior to implement when the key is pressed/clicked
	 * @since 1.0
	 */
	public void addBehavior(int keyCode, Behavior behaviour){
		keyBehaviors.put(keyCode, behaviour);
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
	
}
