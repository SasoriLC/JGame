package jgame.structures.time;

import java.util.TimerTask;

import jgame.Behavior;

/**
 * This timer executes a certain tasks after a certain millisecond interval
 * @author David Almeida
 * @see jgame.structures.time.Timer
 * @since 1.0
 */
public class PeriodicTimer extends Timer{

	private long timeBetweenTasks;
	
	/**
	 * @param milli the milliseconds of the task
	 * @param timeBetweenTasks the time between two successive tasks in milliseconds
	 * @param task the task to execute
	 * @since 1.0
	 */
	public PeriodicTimer(long milli,long timeBetweenTasks, Behavior task) {
		this(milli,timeBetweenTasks,task,null);
	}
	
	/**
 	 * @param milli the milliseconds of the task
 	 * @param timeBetweenTasks the time between two successive tasks in milliseconds
	 * @param task the task to execute
	 * @param taskOnTimerEnd the task to execute when the timer ends
	 * @since 1.0
	 */
	public PeriodicTimer(long milli,long timeBetweenTasks, Behavior task, Behavior taskOnTimerEnd){
		super(milli,task,taskOnTimerEnd);
		this.timeBetweenTasks = timeBetweenTasks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		createTimerTask();
		timer.scheduleAtFixedRate(task, timeBetweenTasks, timeBetweenTasks);
	}

	/**
	 * Creates a timer task that decrements a millisecond
	 * @since 1.0
	 */
	private void createTimerTask(){
		task = new TimerTask(){
			@Override
			public void run(){
				milliseconds -= timeBetweenTasks;
				if(timerExecutionTask != null)
					timerExecutionTask.run();
				if(isTaskOver()){ //end of the timer
					stop();
					return;
				}
			}
		};
	}
	
	
	/**
	 * @return the timeBetweenTasks
	 * @since 1.0
	 */
	public long getTimeBetweenTasks() {
		return timeBetweenTasks;
	}

}
