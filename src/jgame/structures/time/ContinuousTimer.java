package jgame.structures.time;

import java.util.TimerTask;

import jgame.Behavior;

public class ContinuousTimer extends Timer{

	/**
	 * {@inheritDoc}
	 */
	public ContinuousTimer(int milli, Behavior task) {
		super(milli, task);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ContinuousTimer(int milli, Behavior task, Behavior taskOnTimerEnd){
		super(milli,task);
	}

	/**
	 * {@inheritDoc}
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
				if(timerExecutionTask != null)
					timerExecutionTask.run();
			}
		};
	}
}
