

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameOver extends State {
	private BufferedImage lose;

	public GameOver(Game game) {
		super(game);
		lose = ImageLoader.loadImage("lose.png");
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
		g.drawImage(lose, 0, 0, 600, 640, null);
	}

}
