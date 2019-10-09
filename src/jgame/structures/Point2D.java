package jgame.structures;

import jgame.Behavior;
import jgame.structures.time.ContinuousTimer;
import jgame.structures.time.PeriodicTimer;
import jgame.structures.time.Timer;

/**
 * This class represents a point in a 2 dimensional space. 
 * The pair (x,y) is a R pair, this is, both are real numbers
 * @author David Almeida
 * @since 1.0
 */
public class Point2D {
	public float x,y;
	private Timer currentTimer;

	/**
	 * 
	 * @param x - the x
	 * @param y - the y
	 * @since 1.0
	 */
	public Point2D(float x,float y){
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @param x of the target
	 * @param y of the target
	 * @param time the time in milliseconds to move to the desired target
	 * @param b the function to be executed while the target reaches its destination
	 * @since 1.0
	 */
	public void moveTowards (float newX, float newY,int time, Behavior b){
		float moveX = Math.abs(x - newX) / time;
		moveX = newX > x ? moveX : -moveX;
		final float incX = moveX;

		float moveY = Math.abs(y - newY) / time;
		moveY = newY > y ? moveY : -moveY;
		final float incY = moveY;
		if(currentTimer != null)
			currentTimer.stop();


		currentTimer = new ContinuousTimer(time + 2,
				() -> {
					if(b != null)
						b.run();
					x+=incX;
					y+=incY;
				});

		currentTimer.start();
	}

	/**
	 * 
	 * @param x of the target
	 * @param y of the target
	 * @param time the time in milliseconds to move to the desired target
	 * @since 1.0
	 */
	public void moveTowards (float x, float y, int time){
		moveTowards(x, y,time,null);
	}

	/**
	 * 
	 * @param target the target vector
	 * @param b the function to be executed while the target reaches its destination
	 * @param time the time in milliseconds to move to the desired target
	 * @since 1.0
	 */
	public void moveTowards (Point2D target, int time, Behavior b){
		moveTowards(target.x,target.y,time,b);
	}

	/**
	 * 
	 * @param target the target vector
	 * @param time the time in milliseconds to move to the desired target
	 * @since 1.0
	 */
	public void moveTowards (Point2D target, int time){
		moveTowards(target.x,target.y,time);
	}

	public static float distance(float x1,float y1,float x2,float y2){
		return (float)Math.sqrt(
				Math.pow(x1-x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static float distance(Point2D p1,Point2D p2){
		return distance(p1.x,p1.y,p2.x,p2.y);
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
		Point2D other = (Point2D) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
}