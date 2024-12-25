package Day25;

import java.util.Arrays;

public class Lock extends LockOrKey {

	public Lock(char[][] lockOrKey) {
		super(lockOrKey);
	}

	public void setHeights() {
		for (int row = 1; row < lockOrKey.length; row++) {
			for (int col = 0; col < lockOrKey[row].length; col++) {
				if (lockOrKey[row][col] == '#') {
					heights[col]++;
				}
			}
		}
	}

	public boolean insertKey(Key key) {
		for (int pin = 0; pin < key.heights.length; pin++) {
			if (key.heights[pin] + heights[pin] > 5) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Lock: heights=" + Arrays.toString(heights);
	}

}
