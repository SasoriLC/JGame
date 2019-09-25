package jgame.structures;

import jgame.entity.GameObject;
import jgame.structures.timer.Timer;

/**
 * This class represents a vector in a 2 dimensional space. 
 * The pair (x,y) is a R pair, this is, both are real numbers
 * @author David Almeida
 * @since 1.0
 */
public class Vector2 {
	public float x;
	public float y;
	private Timer currentTimer;
	
	/**
	 * 
	 * @param x - the x
	 * @param y - the y
	 * @since 1.0
	 */
	public Vector2(float x,float y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param newX of the target
	 * @param newY of the target
	 * @param timer the timer to be used while the vector reaches its destination. 
	 * The timer cannot be started before.
	 * @since 1.0
	 */
	public void moveTowards (float newX, float newY, Timer timer){
		float moveX = Math.abs(x - newX) / timer.getTimeLeft();
		moveX = newX > x ? moveX : -moveX;
		final float incX = moveX;
		
		float moveY = Math.abs(y - newY) / timer.getTimeLeft();
		moveY = newY > y ? moveY : -moveY;
		final float incY = moveY;
		if(currentTimer != null)
			currentTimer.stop();
		
		currentTimer = new Timer(timer.getTimeLeft() + 2){
			@Override
			public void task() {
				timer.task();
				x+=incX;
				y+=incY;

			}

			@Override
			public void onTimerEnd() {
				timer.onTimerEnd();
			}
		};
		currentTimer.start();
	}
	
	/**
	 * 
	 * @param x of the target
	 * @param y of the target
	 * @param time the time in milliseconds
	 * @since 1.0
	 */
	public void moveTowards (float x, float y, int time){
		moveTowards(x, y,new Timer(time));
	}
	
	/**
	 * 
	 * @param target the target vector
	 * @param timer the timer to be used while the vector reaches its destination. 
	 * The timer cannot be started before.
	 * @since 1.0
	 */
	public void moveTowards (Vector2 target, Timer timer){
		moveTowards(target.x,target.y,timer);
	}
	
	/**
	 * 
	 * @param target the target vector
	 * @param time the time in milliseconds
	 * @since 1.0
	 */
	public void moveTowards (Vector2 target, int time){
		moveTowards(target.x,target.y,time);
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2 other = (Vector2) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
}
