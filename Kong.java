import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Kong extends Entity {

	public static BufferedImage[] kong;
	SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("a.png"));
	private Animation kongAnim;
	public boolean a = true;

	public Kong(Game game, float x, float y) {
		super(x, y);
		kong = new BufferedImage[2];
		kong[0] = sheet.crop(58, 152, 46, 32);
		kong[1] = sheet.crop(202, 152, 46, 32);
		kongAnim = new Animation(820, kong);
	}
	@Override
	public void tick() {
		kongAnim.tick();
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(kongAnim.getCurrentFrame(), 60, 5, 75, 75, null);
	}
}