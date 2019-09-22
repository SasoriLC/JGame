package jgame;

import java.io.File;
import java.util.function.Consumer;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Audio {

	private BasicPlayer player;
	private Thread t;
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
