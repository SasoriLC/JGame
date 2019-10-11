package jgame.structures.time;

import java.util.TimerTask;

import jgame.Behavior;
/**
 * This timer executes a certain tasks after a 1 millisecond interval.
 * This timer can be implement using a PeriodicTimer with a 1 millisecond interval between 
 * two consecutive tasks. 
 * @author David Almeida
 * @see jgame.structures.time.Timer
 * @since 1.0
 */
public class ContinuousTimer extends Timer{

	/**
	 * {@inheritDoc}
	 */
	public ContinuousTimer(long milli, Behavior task) {
		super(milli, task);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ContinuousTimer(long milli, Behavior task, Behavior taskOnTimerEnd){
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
