package jgame;
import java.io.File;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * This class is responsible for playing audio. 
 * The audio file can be in any recognizable format. 
 * @author David Almeida
 * @since 1.0
 */
public class Audio{

	private BasicPlayer player;
	private Thread t;
	
	/**
	 * Creates an audio source to play audio in any format
	 * @param path the path of the audio file
	 * @since 1.0
	 */
	public Audio(String path){
		player = new BasicPlayer();
		try {
			player.open(new File(path));
		} catch (BasicPlayerException e) {
			System.out.println("Could not find file: " + path);
			e.printStackTrace();
		}
	}

	/**
	 * Starts the given audio
	 * @since 1.0
	 */
	public void play(){
		startThread(() -> {
			try {
				player.play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Resumes the given audio
	 * @since 1.0
	 */
	public void resume(){
		startThread(() -> {
			try {
				player.resume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Pauses the given audio
	 * @since 1.0
	 */
	public void pause(){
		startThread(() -> {
			try {
				player.pause();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Stops the given audio
	 * @since 1.0
	 */
	public void stop(){
		startThread(() -> {
			try {
				player.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Starts a new thread that executes a function which is implemented by @function
	 * @param function The function to execute
	 * @since 1.0
	 */
	private void startThread(Behavior function){
		if(t != null){
			t.interrupt();
		}
		t = new Thread(){
			@Override
			public void run(){
				function.run();	
			}
		};
		t.start();
	}
}
