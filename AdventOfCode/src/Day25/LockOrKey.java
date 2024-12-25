package Day25;

import java.util.Arrays;

abstract class LockOrKey {
	public char[][] lockOrKey = new char[7][5];
	public int[] heights = new int[5];

	public LockOrKey(char[][] lockOrKey) {
		this.lockOrKey = lockOrKey;
		setHeights();
	}

	public abstract void setHeights();


}
