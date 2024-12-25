package Day25;

import java.util.Arrays;

public class Key extends LockOrKey {

	public Key(char[][] lockOrKey) {
		super(lockOrKey);
	}

	public void setHeights() {
		for (int row = 0; row < lockOrKey.length - 1; row++) {
			for (int col = 0; col < lockOrKey[row].length; col++) {
				if (lockOrKey[row][col] == '#') {
					heights[col]++;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Key:  heights=" + Arrays.toString(heights);
	}

}
