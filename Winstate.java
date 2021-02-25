

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Winstate extends State {
	private BufferedImage win;

	public Winstate(Game game) {
		super(game);
		win = ImageLoader.loadImage("final.png");
	}

	@Override
	public void tick() {
		if (game.getKeyManager().res) {
			State.setState(game.menuState);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 600, 800);
		g.drawImage(win, 0, 0, 600, 640, null);
	}

}
