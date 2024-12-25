package Day13;

public class Location {
	private long x, y;

	public Location(long x, long y) {
		setX(x);
		setY(y);
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public void move(long x, long y) {
		setX(getX() + x);
		setY(getY() + y);
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
}
