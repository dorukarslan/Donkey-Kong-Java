

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Mario extends Entity {

	private Game game;
	private Barrel barrel;
	public ArrayList<Integer> levelsy = new ArrayList<Integer>();
	// Mario positions
	public float x;
	public float y;
	public int height = 39;
	public int width = 28;
	// Movement speed
	public int plusX = 1;
	public int plusY = 20;
	// Colliding status
	public boolean isCollidingLadder;
	public boolean isCollidingBarrel;
	public static boolean isCollidingHealth;
	public static boolean isCollidingBoost;
	public static boolean checkB = false;
	public boolean checkP = false;
	// Jump
	public int currentLevely;
	public int currentLevelEdge;
	public boolean on_edge = false;
	public boolean isJumping;
	public static boolean canjump = false;
	public boolean falling = false;
	public double gravity = 1.0;
	public static boolean marioPressed = false;
	public int jc = 1;
	// Levels positions
	public int level0y = 40;
	public int level1y = 185;
	public int level2y = 330;
	public int level3y = 475;
	public int level4y = 615;
	public int level5y = 760;
	// win&&lose condition
	public boolean iswin;
	public boolean isAlive;
	public static int health = 2;
	public int barTimer = 0;
	public int healthTimer = 0;
	// Animation check
	public boolean right, left, constant;

	public boolean stay_constant = true;
	// Animations
	public static BufferedImage[] mario_right;
	public static BufferedImage[] mario_left;
	public static BufferedImage[] marioS;
	public static BufferedImage[] fixed_left;
	public static BufferedImage[] fixed_right;
	private Animation animleft;
	private Animation animright;
	private Animation marios;

	public Mario(Game game, Barrel barrel, float x, float y, int height, int width) {
		super(x, y);
		this.game = game;
		this.barrel = barrel;
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.isAlive = true;
		this.currentLevely = level5y;

		canjump = true;
		isJumping = false;
		levelsy.add(level0y);
		levelsy.add(level1y);
		levelsy.add(level2y);
		levelsy.add(level3y);
		levelsy.add(level4y);
		levelsy.add(level5y);
		marioPressed = true;
		health = 2;

		// Animation
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("a.png"));
		fixed_left = new BufferedImage[1];
		fixed_right = new BufferedImage[1];
		marioS = new BufferedImage[1];
		mario_right = new BufferedImage[3];
		mario_left = new BufferedImage[3];

		marioS[0] = sheet.crop(145, 24, 16, 15);

		mario_right[0] = sheet.crop(158, 3, 14, 16);
		mario_right[1] = sheet.crop(176, 4, 15, 15);
		mario_right[2] = sheet.crop(197, 3, 15, 16);

		mario_left[0] = sheet.crop(136, 3, 14, 16);
		mario_left[1] = sheet.crop(115, 4, 15, 15);
		mario_left[2] = sheet.crop(94, 3, 15, 16);

		animleft = new Animation(130, mario_left);
		animright = new Animation(130, mario_right);
		marios = new Animation(130, marioS);

	}

	@Override
	public void tick() {

		animleft.tick();
		animright.tick();
		FindFloor();
		// For lose
		if (health <= 0) {
			isAlive = false;
			if (!isAlive) {
				State.setState(game.loseState);
			}
		}
		// For wim
		if (CheckPrincessCollision()) {
			iswin = true;
			if (iswin) {
				State.setState(game.winState);
			}
		}
		
		if (on_edge) {
			y += 25;
			if (y >= 900) {
				isAlive = false;
				if (!isAlive) {
					State.setState(game.loseState);
				}
			}
			on_edge = false;
		}
		
		else {
			if (!marioPressed && isCollidingLadder == false) {
				falling = true;
				fall();
				if (this.y + 5 >= currentLevely) {
					KeyManager.check = true;
				}

			}
			if (game.getKeyManager().jump && isCollidingLadder == false) {
				if (marioPressed == false) {
				}
				if (marioPressed) {
					if (canjump) {
						y -= (int) gravity * 3;
						if (this.y < currentLevely - 95) {
							canjump = false;
							falling = true;
						}
					}
					if (falling) {
						fall();
					}
				}
			}
			if (game.getKeyManager().up) {
				move("up");
			}
			if (game.getKeyManager().down) {
				move("down");
			}

			if (game.getKeyManager().left) {
				left = true;
				right = false;
				constant = false;
				stay_constant = false;
				if (isCollidingLadder) {
				} else {
					move("left");
				}
			}
			if (game.getKeyManager().right) {
				right = true;
				left = false;
				constant = false;
				stay_constant = false;
				if (isCollidingLadder) {
				} else {
					move("right");
				}
			}

			if (game.getKeyManager().jump) {
				move("jump");
			}
			if (CheckBarrelCollision() && barTimer == 0) {
				health--;
				barTimer = 100;
			}
			if (barTimer != 0) {
				barTimer--;
			}
			if (isCollidingHealth && healthTimer == 0) {
				if (health < 2) {
					health++;
					healthTimer = 50;
				}
			}
			if (healthTimer != 0) {
				healthTimer--;
			}
		}
		CheckHealthCollision();
		CheckBoostCollision();
	}

	public void move(String direct) {
		String direction = direct;
		switch (direction) {
		case "left":
			if (this.x <= 0) {
				this.x = 0;
			} else {
				this.x -= plusX * 1.5;
			}
			break;
		case "right":
			if (this.x >= 560) {
				this.x = 560;
			} else {
				this.x += plusX * 1.5;
			}
			break;
		case "jump":

			for (int i = 1; i < 7; i++) {
				this.y -= 10 * (35 - 10 * i);
				game.getKeyManager().setCounter(game.getKeyManager().getCounter() + 1);
			}
			jc++;
			if (jc == 7) {
				jc = 1;
			}
			break;
		case "up":
			if (CollisionCheck()) {
				this.y--;
			}
			break;
		case "down":
			if (CollisionCheck()) {
				this.y++;
			}
			break;
		}
	}

	public void fall() {
		if (falling) {

			y += (int) gravity * 2;
			if (this.y >= currentLevely - 10) {
				Setground();
				falling = false;
				canjump = true;
			}
		}
	}

	public void Setground() {
		this.y = currentLevely;
		gravity = 1;
	}

	public void FindFloor() {
		if (this.y - 10 >= 610) {
			currentLevely = 750;
			if (this.x <= 1) {
				on_edge = true;
			}
		}
		if (this.y - 10 < level4y && this.y - 10 > level3y) {
			currentLevely = level4y - 5;
			if (this.x >= 554) {
				currentLevely += 5;
				on_edge = true;
			}
		}
		if (this.y - 10 < level3y && this.y - 10 > level2y) {
			currentLevely = level3y - 10;
			if (this.x <= 1) {
				on_edge = true;
			}
		}
		if (this.y - 10 < level2y && this.y - 10 > level1y) {
			currentLevely = level2y - 5;
			if (this.x >= 559) {
				on_edge = true;
			}
		}
		if (this.y - 10 < level1y && this.y - 10 > level0y) {
			currentLevely = level1y;
			if (this.x <= 2) {
				on_edge = true;
			}
		}
		if (this.y - 10 < level0y) {
			currentLevely = level0y + 5;
			if (this.x >= 559) {
				currentLevely += 3;
				on_edge = true;
			}
		}
	}

	public boolean CollisionCheck() {
		for (int i = 0; i < game.Ladders.size(); i++) {
			if (game.Ladders.get(i).x + 25 <= x && game.Ladders.get(i).x + game.Ladders.get(i).width + 15 >= x) {

				if (game.getKeyManager().up) {

					if (game.Ladders.get(i).ybottom - 24 <= y
							&& game.Ladders.get(i).ybottom + game.Ladders.get(i).height >= y) {

						isCollidingLadder = true;
					} else {
						isCollidingLadder = false;
					}
				} else if (game.getKeyManager().down) {
					if (game.Ladders.get(i).ybottom - 25 <= y
							&& game.Ladders.get(i).ybottom + game.Ladders.get(i).height - 38 >= y) {

						isCollidingLadder = true;
					} else {
						isCollidingLadder = false;
					}
				}
			}

		}
		return isCollidingLadder;
	}

	public boolean CheckBarrelCollision() {
		for (int i = 0; i < barrel.Barrels.size(); i++) {
			if (barrel.Barrels.get(i).x <= x + width - 3 && barrel.Barrels.get(i).x + 20 >= x + 3) {

				if (barrel.Barrels.get(i).y + 8 <= y + height
						&& barrel.Barrels.get(i).y + barrel.Barrels.get(i).height >= y - 24) {
					isCollidingBarrel = true;

				} else {
					isCollidingBarrel = false;

				}
			}
		}
		return isCollidingBarrel;
	}

	public boolean CheckHealthCollision() {
		if (game.health.getX() <= x + width && game.health.getX() + game.health.getWidth() - 15 >= x) {

			if (game.health.getY() + 10 <= y + height && game.health.getY() + game.health.getHeight() >= y) {

				if (health < 2) {
					health++;
					isCollidingHealth = true;
				} else if (health == 2) {
					isCollidingHealth = false;
				}
			} else {
				isCollidingHealth = false;
			}
		}

		return isCollidingHealth;
	}

	public boolean CheckBoostCollision() {
		if ((game.boost.getX() <= x + width && game.boost.getX() + game.boost.getWidth() - 18 >= x)
				&& (game.boost.getY() + 8 <= y + height && game.boost.getY() + game.boost.getHeight() >= y)) {

			if (plusX <= 2) {
				plusX += 1.5;
			}

			checkB = true;

		} else {
			checkB = false;
		}
		if ((Boost.a == 0) && (y <= 615)) {
			plusX = 1;
		}
		if ((Boost.a == 1) && ((y <= 330) || (y > 490))) {
			plusX = 1;
		}
		if ((Boost.a == 2) && ((y <= 45) || (y > 200))) {
			plusX = 1;
		}

		return checkB;
	}

	public boolean CheckPrincessCollision() {
		if ((game.princess.getX() + game.princess.getWidth() - 5 >= x) && (game.princess.getY() + 25 >= y)) {
			checkP = true;
		} else {
			checkP = false;
		}
		return checkP;
	}

	@Override
	public void render(Graphics g) {

		if (constant == false && stay_constant == false) {
			g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, 40, 40, null);
			stay_constant = true;
		} else if (stay_constant == true) {
			g.drawImage(marios.getCurrentFrame(), (int) x, (int) y, 40, 40, null);
		} else if (constant == true) {
			g.drawImage(getCurrentAnimationFrame(), 55, 745, 40, 40, null);
		}

	}

	private BufferedImage getCurrentAnimationFrame() {
		if (right == true) {
			return animright.getCurrentFrame();
		} else if (left == true) {
			return animleft.getCurrentFrame();
		} else {
			constant = true;
			return marios.getCurrentFrame();
		}

	}

}