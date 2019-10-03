package jgame.structures.time;
import java.util.TimerTask;

import jgame.Behavior;
/**
 * This class represents a timer that as millisecond precision
 * @author David Almeida
 * @since 1.0
 * @see jgame.structures.time.Timer
 */
public abstract class Timer{
	protected int milliseconds;
	protected java.util.Timer timer;
	protected TimerTask task;
	protected Behavior timerExecutionTask, onTimerEndTask;
		
	/**
	 * 
	 * @param mili the milliseconds of the task
	 * @param task the task to execute
	 * @since 1.0
	 */
	public Timer(int milli, Behavior task){
		timer = new java.util.Timer();
		timerExecutionTask = task;
		this.milliseconds = milli;
	}
	
	/**
	 * 
	 * @param mili the milliseconds of the task
	 * @param task the task to execute
	 * @param taskOnTimerEnd the task to execute when the timer ends
	 * @since 1.0
	 */
	public Timer(int milli, Behavior task, Behavior taskOnTimerEnd){
		this(milli,task);
		onTimerEndTask = taskOnTimerEnd;
	}
		
	/**
	 * Starts the timer
	 * @since 1.0
	 */
	public abstract void start();
	
	/**
	 * 
	 * @return true if the timer is over, that is, has passed exactly {@value milliseconds} milliseconds
	 * @since 1.0
	 */
	public final boolean isTaskOver(){
		return milliseconds <= 0;
	}
	
	public final void stop(){
		task.cancel();
		timer.cancel();
		if(onTimerEndTask != null)
			onTimerEndTask.run();
	}
	
	/**
	 * 
	 * @return the time left on the timer
	 * @since 1.0
	 */
	public final int getTimeLeft(){
		return milliseconds;
	}
	
	/**
	 * @return the task that the timer is executing
	 */
	public final Behavior getTimerExecutionTask() {
		return timerExecutionTask;
	}

	/**
	 * @return the task that the timer will execute when it ends
	 */
	public  final Behavior getOnTimerEndTask() {
		return onTimerEndTask;
	}
}
