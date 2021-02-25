public class Ladder {
	public int x;
	public int ybottom;
	public int height;
	public int width;

	public Ladder(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.ybottom = y;
	}
	public void creation() {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getYbottom() {
		return ybottom;
	}

	public void setYbottom(int ybottom) {
		this.ybottom = ybottom;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}