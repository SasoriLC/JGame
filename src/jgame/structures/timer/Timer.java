package jgame.structures.timer;
/**
 * This class represents a timer that as millisecond precision
 * @author David Almeida
 * @since 1.0
 * @see jgame.structures.timer.Ticker
 */
public class Timer extends Ticker{

	/**
	 * 
	 * @param mili the milliseconds of the task
	 * @since 1.0
	 */
	public Timer(int milli) {
		super(milli);
	}

	/**
	 * The task to be done each millisecond
	 * @since 1.0
	 */
	@Override
	public void task() {
		//this method is left purposefully empty since going to be overwritten by the user
	}

	/**
	 * This method is called when a the timer ends
	 * @since 1.0
	 */
	@Override
	public void onTimerEnd() {
		//this method is left purposefully empty since going to be overwritten by the user
	}

}
