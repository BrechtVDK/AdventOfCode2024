package Day14;

public class Robot {
	int[] start;
	int[] pos = new int[2];
	int veloX, veloY;
	public static int NORTH = 0, WEST = 0, EAST = 101, SOUTH = 103;

	public Robot(int[] start, int veloX, int veloY) {
		this.start = start;
		reset();
		this.veloX = veloX;
		this.veloY = veloY;
	}

	public int[] getPos() {
		return pos;
	}

	public void reset() {
		pos[0] = start[0];
		pos[1] = start[1];
	}

	public void moveOnce() {
		int posX = pos[0];
		int posY = pos[1];
		int newPosX = posX + veloX;
		if (newPosX < WEST) {
			newPosX = EAST + newPosX;
		} else if (newPosX >= EAST) {
			newPosX = newPosX - EAST;
		}
		int newPosY = posY + veloY;
		if (newPosY < NORTH) {
			newPosY = SOUTH + newPosY;
		} else if (newPosY >= SOUTH) {
			newPosY = newPosY - SOUTH;
		}
		pos[0] = newPosX;
		pos[1] = newPosY;

	}
}
