package Day6;

import java.util.Objects;

public class Pos {
	public int row, col;
	private char dir;

	public Pos(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Pos(int row, int col, char dir) {
		this(row, col);
		this.dir = dir;
	}

	@Override
	public int hashCode() {
		return Objects.hash(col, dir, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pos other = (Pos) obj;
		return col == other.col && dir == other.dir && row == other.row;
	}

}
