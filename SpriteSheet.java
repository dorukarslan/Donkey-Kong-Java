

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage sheet;
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	public BufferedImage crop(int x, int y, int widht, int height) {
		return sheet.getSubimage(x, y, widht, height);
	}

}