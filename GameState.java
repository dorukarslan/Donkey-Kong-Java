import java.awt.Graphics;




public class GameState extends State {
	Game game;
	private Mario mario;
	private Kong kong;
	private Barrel barrel;

	public GameState(Game game) {
		super(game);
		barrel = new Barrel();
		mario = new Mario(game, barrel, 50, 750, 39, 28);
		kong = new Kong(game, 0, 760);
	}

	@Override
	public void tick() {
		mario.tick();
		barrel.tick();
		kong.tick();
	}

	@Override
	public void render(Graphics g) {
		mario.render(g);
		barrel.render(g);
		kong.render(g);

	}

}
