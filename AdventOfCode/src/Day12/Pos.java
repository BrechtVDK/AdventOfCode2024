package Day12;

import java.util.Objects;

public class Pos implements Comparable<Pos> {
	public int row, col;

	public Pos(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public int hashCode() {
		return Objects.hash(col, row);
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
		return col == other.col && row == other.row;
	}

	@Override
	public String toString() {
		return "Pos [" + row + "," + col + "]";
	}

	@Override
	public int compareTo(Pos o) {
		int rowComp = this.row - o.row;
		if (rowComp != 0) {
			return rowComp;
		}
		return this.col - o.col;
	}

}