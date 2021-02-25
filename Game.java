
import java.awt.Color;
import java.awt.Graphics;
//import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game implements Runnable {

	// Game positions
	private int height, width;
	private String title;
	// Arrays for levels&&ladders
	public ArrayList<Level> Levels = new ArrayList<Level>();
	public ArrayList<Ladder> Ladders = new ArrayList<Ladder>();
	// Levels
	Level level0 = new Level(35, 775, 600, 25);
	Level level1 = new Level(0, 635, 565, 15);
	Level level2 = new Level(35, 490, 600, 15);
	Level level3 = new Level(0, 350, 565, 15);
	Level level4 = new Level(35, 210, 600, 15);
	Level level5 = new Level(0, 70, 565, 15);
	// Health&&boost&&princess
	Princess princess = new Princess(10, 25);
	Health health = new Health();
	Boost boost = new Boost();

	// Game
	private Display display;
	private Thread thread;
	private boolean running = false;

	// Animations
	private BufferedImage background, ladder, platform, healthImage, boostImage, princessImage;
	private KeyManager keyManager;
	BufferedImage[] bars;
	public int counter = 0;
	private BufferStrategy bs;
	private Graphics g;
	// States
	public State gameState;
	public State menuState;
	public State winState;
	public State loseState;
	// Ladders
	public Ladder ladder0, ladder1, ladder2, ladder3, ladder4;

	public Game(String title, int height, int widht) {
		this.title = title;
		this.height = height;
		this.width = widht;
		keyManager = new KeyManager();
		Levels.add(level0);
		Levels.add(level1);
		Levels.add(level2);
		Levels.add(level3);
		Levels.add(level4);
		Levels.add(level5);
	}

	private void init() {
		display = new Display(title, height, width);
		display.getFrame().addKeyListener(keyManager);
		platform = ImageLoader.loadImage("platform.png");
		ladder = ImageLoader.loadImage("ladder.png");
		background = ImageLoader.loadImage("background-2.jpg");
		healthImage = ImageLoader.loadImage("health.png");
		boostImage = ImageLoader.loadImage("boost.png");
		princessImage = ImageLoader.loadImage("premses.png");
		SpriteSheet barSheet = new SpriteSheet(ImageLoader.loadImage("bar.png"));

		bars = new BufferedImage[2];
		bars[0] = barSheet.crop(1, 926, 629, 148);
		bars[1] = barSheet.crop(1, 371, 629, 148);

		loseState = new GameOver(this);
		winState = new Winstate(this);
		gameState = new GameState(this);
		menuState = new MenuState(this);
		State.setState(menuState);

	}

	private void tick() {
		if (State.getState() == this.menuState) {
			if (counter == 0) {
				ladder4 = new Ladder((int) (Math.random() * (200) + 300), 70, 154 / 10, 383 * 2 / 5);
				ladder3 = new Ladder((int) (Math.random() * (195) + 5), 210, 154 / 10, 383 * 2 / 5);
				ladder2 = new Ladder((int) (Math.random() * (200) + 300), 350, 154 / 10, 383 * 2 / 5);
				ladder1 = new Ladder((int) (Math.random() * (195) + 5), 490, 154 / 10, 383 * 2 / 5);
				ladder0 = new Ladder((int) (Math.random() * (200) + 300), 635, 140 / 10, 383 * 2 / 5);
				Ladders.add(ladder0);
				Ladders.add(ladder1);
				Ladders.add(ladder2);
				Ladders.add(ladder3);
				Ladders.add(ladder4);
				counter = 1;
			}
			princess = new Princess(10, 25);
			health = new Health();
			boost = new Boost();
			gameState = new GameState(this);
		}

		keyManager.tick();
		if (State.getState() != null) {
			State.getState().tick();
		}

	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics(); // Paint Brush
		g.clearRect(0, 0, width, height); // Cleans the screen

		// Drawings
		g.drawImage(background, 0, 0, width, height, null);

		g.setColor(Color.RED);
		for (int i = 0; i < Levels.size(); i++) { // Draws levels
			g.drawImage(platform, Levels.get(i).getX(), Levels.get(i).getY(), Levels.get(i).getWidth(),
					Levels.get(i).getHeight(), null);
		}
		for (int i = 0; i < Ladders.size(); i++) { // Draws ladders
			g.drawImage(ladder, Ladders.get(i).getX(), Ladders.get(i).getYbottom(), ladder.getWidth() / 5,
					ladder.getHeight() * 2 / 7, null);
		}

		if (Mario.health == 2) {

			g.drawImage(bars[0], 528, 5, 629 / 9, 148 / 9, null);

		} else if (Mario.health == 1) {
			g.drawImage(bars[1], 528, 5, 629 / 9, 148 / 9, null);

		}
		if (!Mario.isCollidingHealth) {
			g.drawImage(healthImage, health.getX(), health.getY(), health.getWidth(), health.getHeight(), null);
		} else if (Mario.isCollidingHealth) {
			health.setY(1000);
		}

		g.drawImage(princessImage, 10, 25, princessImage.getWidth() / 7, princessImage.getHeight() / 7, null);

		if (!Mario.checkB) {
			g.drawImage(boostImage, boost.getX(), boost.getY(), boost.getWidth(), boost.getHeight(), null);

		} else if (Mario.checkB) {
			boost.setY(1000);
		}

		if (State.getState() != null) {
			State.getState().render(g);
		}
		bs.show();
		g.dispose();
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public void run() {
		init();
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				delta--;
				if (timer >= 1000000000) {
					timer = 0;
				}
			}
		}
		stop();
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Level> getLevels() {
		return Levels;
	}

	public void setLevels(ArrayList<Level> levels) {
		Levels = levels;
	}

	public ArrayList<Ladder> getLadders() {
		return Ladders;
	}

	public void setLadders(ArrayList<Ladder> ladders) {
		Ladders = ladders;
	}

}
