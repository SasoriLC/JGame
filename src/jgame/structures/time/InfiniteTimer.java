package jgame.structures.time;

import java.util.TimerTask;

import jgame.Behavior;

/**
 * This class should be used when a timer is supposed to be in execution forever
 * @author David Almeida
 * @since 1.0
 */
public class InfiniteTimer extends Timer{

	private int timeBetweenTasks;
	
	/**
	 * 
	 * @param milli the time between two successive tasks
	 * @param task the task to execute
	 * @since 1.0
	 */
	public InfiniteTimer(int milli, Behavior task) {
		super(-1, task);
		timeBetweenTasks = milli;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		task = new TimerTask(){
			@Override
			public void run() {
				timerExecutionTask.run();
			}	
		};
		timer.schedule(task, timeBetweenTasks, timeBetweenTasks);
	}
}
