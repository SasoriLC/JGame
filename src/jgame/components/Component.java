package jgame.components;

import jgame.entity.Observable;
import jgame.entity.Observer;

public abstract class Component extends Observable implements Observer{
	
	public String componentName;
	
	protected Component(String componentName){
		this.componentName = componentName;
	}
	
	public final String getComponentName(){
		return componentName;
	}

}
