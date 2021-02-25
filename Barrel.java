

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Barrel {
	public int x = 120;
	public int y = 50;
	public int height;
	public int widht;
	public int barrelMoveX = 2;
	private int r = 0;
	public ArrayList<Barrel> Barrels = new ArrayList<Barrel>();
	int timer = 350;

	public static BufferedImage[] barrelb;
	SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("a.png"));
	private Animation barrela;

	public Barrel() {
		barrelb = new BufferedImage[4];
		barrelb[0] = sheet.crop(66, 258, 12, 10);
		barrelb[1] = sheet.crop(81, 258, 12, 10);
		barrelb[2] = sheet.crop(66, 270, 12, 10);
		barrelb[3] = sheet.crop(81, 270, 12, 10);
		barrela = new Animation(250, barrelb);

	}

	public void move() {

		if (r == 0) {
			this.setX(this.getX() + this.getBarrelMoveX());
		}

		if (this.getX() == 566 || this.getX() == 4) {

			this.setY(this.getY() + 4);
			r++;
			if (r == 35) {
				this.setX(this.getX() - this.getBarrelMoveX());
				r = 0;
				this.setBarrelMoveX((-1) * this.getBarrelMoveX());
			}
		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidht() {
		return widht;
	}

	public void setWidht(int widht) {
		this.widht = widht;
	}

	public int getBarrelMoveX() {
		return barrelMoveX;
	}

	public void setBarrelMoveX(int barrelMoveX) {
		this.barrelMoveX = barrelMoveX;
	}

	public void tick() {
		barrela.tick();
		if (timer == 350) {
			Barrel newBarrel = new Barrel();
			Barrels.add(newBarrel);
			timer = 0;
		}

		if (Barrels.size() == 7) {

			Barrels.remove(0);
		}
		timer++;

		for (int i = 0; i < Barrels.size(); i++) {

			Barrels.get(i).move();
		}

	}

	public void render(Graphics g) {
		for (int i = 0; i < Barrels.size(); i++) { // Draws barrels
			g.drawImage(barrela.getCurrentFrame(), Barrels.get(i).getX(), Barrels.get(i).getY(), 30, 30, null);
		}

	}

}
