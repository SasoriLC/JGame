package test;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import jgame.GameObjectCollisionDetector;
import jgame.Keyboard;
import jgame.Mouse;
import jgame.Scene;
import jgame.Window;
import jgame.entity.Camera;
import jgame.entity.GameObject;
import jgame.sprite.LoopingSprite;
import jgame.sprite.Sprite;
import jgame.tile.Tile;
import jgame.tile.TileType;
public class Main {

	private static float velocity = 1.5f;
	private static final String MAP = "cave.scn";
	private static final String PLAYER = "Sprites/sprite.png";
	private static long time = 100;
	private static Scene scene;
	private static Window window;

	private static GameObject player;

	public static void main(String[] args){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = 800;//gd.getDisplayMode().getWidth();
		int height = 600;//gd.getDisplayMode().getHeight();

		Sprite s = new Sprite(PLAYER,16);
		player = new GameObject(s,700,500);
		new Camera(player,width,height,0,0);
		player.getSprite().setSequence(3, 4,1);

		scene = new Scene(MAP);
		scene.addGameObject(player); //me
		player.addObserver(new GameObjectCollisionDetector());

		//add collisions. Note that this only will work correctly in the following maps: cave and Collision test scene
		ArrayList<ArrayList<Tile>> tiles = scene.getTiles();
		for(ArrayList<Tile> row: tiles){
			for(Tile tile: row){
				if(tile.getTileNumber() == 2) 
					tile.setTileType(TileType.WALL);
				else 
					tile.setTileType(TileType.NORMAL);
			}
		}

		//add more game objets
		addObjects();


		window = new Window(scene,width,height);

		Keyboard k = new Keyboard();

		//create the keyboard behaviors
		k.addBehavior(KeyEvent.VK_UP, () -> { 
			player.moveY(-velocity);
			player.getSprite().setSequence(12,16, time);
		});
		k.addBehavior(KeyEvent.VK_DOWN,() -> {
			player.moveY(velocity);
			player.getSprite().setSequence(0, 4, time);
		});
		k.addBehavior(KeyEvent.VK_LEFT, () -> {
			player.moveX(-velocity);
			player.getSprite().setSequence(4,8, time);
		});
		k.addBehavior(KeyEvent.VK_RIGHT, () -> {
			player.moveX(velocity);
			player.getSprite().setSequence(8,12, time);
		});

		window.setKeyboard(k);

		Mouse m = new Mouse();
		m.setListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Heyo\n"+ e.getX() + "\\" + e.getY());
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
		window.setMouse(m);
		//while(true)
		//window.repaint();

		run();
	}

	private static void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 180.0; //180fps max
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
				updates++;
			}
			frames++;
			if(System.currentTimeMillis() - timer > 1000){ //1 second
				timer += 1000;
				System.out.println("Updates: " + updates + "\nFps: " + frames);
				frames = updates = 0;
			}
		}
	}

	private static void addObjects(){
		GameObject torch = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),725,370);
		scene.addGameObject(torch);

		GameObject torch1 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),575,370);
		scene.addGameObject(torch1);

		GameObject torch2 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),575,420);
		scene.addGameObject(torch2);

		GameObject torch3 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),575,470);
		scene.addGameObject(torch3);

		GameObject torch4 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),575,520);
		scene.addGameObject(torch4);

		GameObject torch5 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),575,570);
		scene.addGameObject(torch5);

		GameObject torch6 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),725,420);
		scene.addGameObject(torch6);

		GameObject torch7 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),725,470);
		scene.addGameObject(torch7);

		GameObject torch8 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),725,520);
		scene.addGameObject(torch8);

		GameObject torch9 = new GameObject(new LoopingSprite("Sprites/torch.png",9,150),725,570);
		scene.addGameObject(torch9);
	}
}