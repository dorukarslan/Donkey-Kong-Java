
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	public boolean up, left, right, jump, down, start, res;
	private int counter = 1;
	static boolean check = true;

	public KeyManager() {
		keys = new boolean[256];
	}

	public void tick() {
		jump = keys[KeyEvent.VK_SPACE];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		start = keys[KeyEvent.VK_ENTER];
		res = keys[KeyEvent.VK_UP];
	}

	public boolean isenter() {
		return start;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;

		int c = e.getKeyCode();
		if (c == KeyEvent.VK_SPACE) {
			if (check) {
				Mario.marioPressed = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;

		int c = e.getKeyCode();
		if (c == KeyEvent.VK_SPACE) {

			Mario.marioPressed = false;
			if (check && Mario.marioPressed == false) {
				check = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public boolean[] getKeys() {
		return keys;
	}

	public void setKeys(boolean[] keys) {
		this.keys = keys;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
