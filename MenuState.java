
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MenuState extends State {

	public static BufferedImage[] kong;
	SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("a.png"));
	private Animation kongAnim;
	Random random;
	int timer = 300;
	public static BufferedImage[] barrelb;
	private Animation barrela;

	public MenuState(Game game) {
		super(game);
		random = new Random();
		kong = new BufferedImage[2];
		kong[0] = sheet.crop(58, 152, 46, 32);
		kong[1] = sheet.crop(202, 152, 46, 32);
		kongAnim = new Animation(300, kong);
		barrelb = new BufferedImage[4];
		barrelb[0] = sheet.crop(66, 258, 12, 10);
		barrelb[1] = sheet.crop(81, 258, 12, 10);
		barrelb[2] = sheet.crop(66, 270, 12, 10);
		barrelb[3] = sheet.crop(81, 270, 12, 10);
		barrela = new Animation(180, barrelb);
	}

	@Override
	public void tick() {
		kongAnim.tick();
		barrela.tick();
		if (game.getKeyManager().start) {
			State.setState(game.gameState);
		}
	}

	@Override
	public void render(Graphics g) {
		g.clearRect(0, 0, 600, 800);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 600, 800);
		g.drawImage(kongAnim.getCurrentFrame(), 235, 90, 100, 100, null);
		float r = (float) (random.nextFloat());
		float s = (float) (random.nextFloat());
		float b = (float) (random.nextFloat());
		g.drawImage(barrela.getCurrentFrame(), 30, 90, 100, 100, null);
		g.drawImage(barrela.getCurrentFrame(), 470, 90, 100, 100, null);
		g.setColor(new Color(r, s, b));
		g.setFont(new Font("TimesRoman PLAIN", Font.BOLD, 40));
		g.drawString("DONKEY KONG", 140, 70);
		
		// the names showed at menu state
		g.drawString("Alkın ALKAN", 165, 240);
		g.drawString("Doruk ARSLAN", 145, 310);
		g.drawString("Melih BENLİOĞLU", 120, 380);
		g.drawString("Doğuhan CUMAOĞLU", 100, 450);
		g.setColor(Color.RED);
		g.drawString("Press enter to play", 125, 600);

	}

}
