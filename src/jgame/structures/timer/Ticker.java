package jgame.structures.timer;
import java.util.TimerTask;
/**
 * This class represents a timer that as millisecond precision
 * TODO
 * @author David Almeida
 * @since 1.0
 * @see jgame.structures.timer.Timer
 */
abstract class Ticker{
	private int milliseconds;
	private java.util.Timer timer;
	private TimerTask task;
	
	/**
	 * 
	 * @param mili the milliseconds of the task
	 * @since 1.0
	 */
	protected Ticker(int milli){
		this.milliseconds = milli;
		timer = new java.util.Timer();
	}
	
	/**
	 * Starts the timer
	 * @since 1.0
	 */
	public void start(){
		createTimerTask();
		timer.scheduleAtFixedRate(task, 0, 1);
	}
	
	/**
	 * Starts the timer
	 * @param delay the delay in milliseconds before the task starts
	 * @since 1.0
	 */
	public void start(int delay){
		createTimerTask();
		timer.scheduleAtFixedRate(task, delay, 1);
	}
	
	/**
	 * 
	 * @return true if the timer is over, that is, has passed exactly {@value milliseconds} milliseconds
	 * @since 1.0
	 */
	public boolean isTaskOver(){
		return milliseconds <= 0;
	}
	
	/**
	 * Creates a timer task that decrements a millisecond
	 * @since 1.0
	 */
	private void createTimerTask(){
		task = new TimerTask(){
			@Override
			public void run(){
				milliseconds--;
				if(isTaskOver()){ //end of the timer
					stop();
					return;
				}
				task();
			}
		};
	}
	
	public void stop(){
		task.cancel();
		timer.cancel();
		onTimerEnd();
	}
	
	/**
	 * 
	 * @return the time left on the timer
	 * @since 1.0
	 */
	public int getTimeLeft(){
		return milliseconds;
	}
	
	/**
	 * The task to be done each millisecond
	 * @since 1.0
	 */
	public abstract void task();
	
	/**
	 * This method is called when a the timer ends
	 * @since 1.0
	 */
	public abstract void onTimerEnd();
}
