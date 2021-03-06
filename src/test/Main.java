package test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import jgame.Audio;
import jgame.Scene;
import jgame.Window;
import jgame.components.BoxCollider;
import jgame.entity.Camera;
import jgame.entity.GameObject;
import jgame.entity.Tile;
import jgame.exceptions.EntityIsAlreadyInSceneException;
import jgame.exceptions.SpriteException;
import jgame.listeners.keyboard.Keyboard;
import jgame.listeners.mouse.Mouse;
import jgame.sprite.LoopingSprite;
import jgame.sprite.Sprite;
public class Main {

	private static float velocity = 1.5f;
	private static final String MAP = "cave.scn";
	private static final String PLAYER = "Sprites/greenman.png";
	private static long time = 400;
	private static Scene scene;
	private static Window window;

	private static GameObject player;
	private static GameObject follower;

	public static void main(String[] args) throws Exception{
		//GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = 800;//gd.getDisplayMode().getWidth();
		int height = 600;//gd.getDisplayMode().getHeight();

		Sprite s = new Sprite(PLAYER,48);
		player = new GameObject(s,350,800,4);
		Camera.create(player,width,height,0,0);
		player.getSprite().setSequence(3, 4,1);
		player.addComponent(new BoxCollider(player));
		player.setName("player");
		scene = new Scene(MAP);
		scene.addGameObject(player); //me
		
		follower = new GameObject(new Sprite("Sprites/test.png",4),100,450,1);
		follower.getSprite().setSequence(0, 1, 9999);
		follower.addComponent(new BoxCollider(follower));
		//add collisions. Note that this only will work correctly in the following maps: cave and Collision test scene
		ArrayList<ArrayList<Tile>> tiles = scene.getTiles();
		for(ArrayList<Tile> row: tiles){
			for(Tile tile: row){
				if(tile.getTileNumber() == 2){
					tile.addComponent(new BoxCollider(tile));
				}
			}
		}

		//add more game objects
		addObjects();


		window = Window.create(scene,width,height);

		Keyboard k = new Keyboard(true);


		Audio a = new Audio("Sounds/grass.mp3");

		//create the keyboard behaviors
		k.addPressBehavior(KeyEvent.VK_UP, () -> { 
			System.out.println(player.position);
			player.moveYContinuously(-velocity);
			try {
				player.getSprite().setSequence(36,48, time,a);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//			follower.position.moveTowards(player.position, 1000);

		});
		k.addPressBehavior(KeyEvent.VK_DOWN,() -> {
			player.moveYContinuously(velocity);
			try {
				player.getSprite().setSequence(24, 36, time,a);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//			follower.position.moveTowards(player.position, 1000);
		});
		k.addPressBehavior(KeyEvent.VK_LEFT, () -> {
			player.moveXContinuously(-velocity);
			try {
				player.getSprite().setSequence(12,24, time,a);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//			follower.position.moveTowards(player.position, 1000);
		});
		k.addPressBehavior(KeyEvent.VK_RIGHT, () -> {
			player.moveXContinuously(velocity);
			try {
				player.getSprite().setSequence(0,12, time,a);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//			follower.position.moveTowards(player.position, 1000);
		});

		//release
		k.addReleaseBehavior(KeyEvent.VK_UP, () -> { 
			player.moveYContinuously(0);
			player.moveXContinuously(0);
			player.getSprite().getAnimation().stop();
		});
		k.addReleaseBehavior(KeyEvent.VK_DOWN,() -> {
			player.moveYContinuously(0);
			player.moveXContinuously(0);
			player.getSprite().getAnimation().stop();
		});
		k.addReleaseBehavior(KeyEvent.VK_LEFT, () -> {
			player.moveXContinuously(0);
			player.moveYContinuously(0);
			player.getSprite().getAnimation().stop();
		});
		k.addReleaseBehavior(KeyEvent.VK_RIGHT, () -> {
			player.moveXContinuously(0);
			player.moveYContinuously(0);
			player.getSprite().getAnimation().stop();
		});

		window.setKeyboard(k);

		Mouse m = new Mouse();
		m.setMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

		});
		//m.setMouseCursor("images.png", "troll");
		window.setMouse(m);

		scene.addGameObject(follower);

		GameObject follower2 = (GameObject)follower.clone();
		follower2.moveX(200);
		follower2.setName("follower2");
		System.out.println(follower.position.x);
		scene.addGameObject(follower2);
		run();
	}

	private static void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 240.0; //240fps max
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				window.repaint();
				delta--;
				frames++;
			}
			updates++;
			if(System.currentTimeMillis() - timer > 1000){ //1 second
				timer += 1000;
				System.out.println("Updates: " + updates + "\nFps: " + frames);
				frames = updates = 0;
			}
		}
	}

	private static void addObjects() throws EntityIsAlreadyInSceneException{
		int time = 1200;
		try{
			GameObject torch = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),725,370,0);
			scene.addGameObject(torch);

			GameObject torch1 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),575,370,0);
			scene.addGameObject(torch1);

			GameObject torch2 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),575,420,0);
			scene.addGameObject(torch2);

			GameObject torch3 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),575,470,0);
			scene.addGameObject(torch3);

			GameObject torch4 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),575,520,0);
			scene.addGameObject(torch4);

			GameObject torch5 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),575,570,0);
			scene.addGameObject(torch5);

			GameObject torch6 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),725,420,0);
			scene.addGameObject(torch6);

			GameObject torch7 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),725,470,0);
			scene.addGameObject(torch7);

			GameObject torch8 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),725,520,0);
			scene.addGameObject(torch8);

			GameObject torch9 = new GameObject(new LoopingSprite("Sprites/torch.png",9,time),725,570,0);
			scene.addGameObject(torch9);
		}catch (SpriteException | IOException e) {
			e.printStackTrace();
		}
	}
}
