package jgame.structures.time;

import java.util.TimerTask;

import jgame.Behavior;

public class PeriodicTimer extends Timer{

	private int timeBetweenTasks;
	
	/**
	 * {@inheritDoc}
	 * @param timerBetweenTasks the time between two successive tasks in milliseconds
	 */
	public PeriodicTimer(int milli,int timeBetweenTasks, Behavior task) {
		this(milli,timeBetweenTasks,task,null);
	}
	
	/**
	 * {@inheritDoc}
	 * @param timerBetweenTasks the time between two successive tasks in milliseconds
	 */
	public PeriodicTimer(int milli,int timeBetweenTasks, Behavior task, Behavior taskOnTimerEnd){
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
	public int getTimeBetweenTasks() {
		return timeBetweenTasks;
	}

}
