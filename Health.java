

public class Health {

	private int x = 100;
	private int y = 50;
	private int width = 50;
	private int height = 50;

	public Health() {
		int a = (int) (Math.random() * (3) + 0);
		if (a == 2) {
			y = 25;
			x = (int) (Math.random() * (385) + 140);

		} else if (a == 1) {
			y = 305;
			x = (int) (Math.random() * (525) + 0);

		} else if (a == 0) {
			y = 585;
			x = (int) (Math.random() * (525) + 0);
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
