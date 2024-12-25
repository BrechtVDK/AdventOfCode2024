package Day15;

import java.util.Objects;

public class Pos {
	public int row, col;
	public boolean clearFlag;

	public Pos(int row, int col) {
		setRow(row);
		setCol(col);

	}

	public Pos(int row, int col, boolean clearFlag) {
		this(row, col);
		setClearFlag(clearFlag);
	}

	public  void setClearFlag(boolean flag) {
		this.clearFlag = flag;
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

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int kol) {
		this.col = kol;
	}

	public void goRight() {
		setCol(++col);
	}

	public void goRightTwice() {
		setCol(col + 2);
	}

	public void goLeft() {
		setCol(--col);
	}

	public void goLeftTwice() {
		setCol(col - 2);
	}

	public void goUp() {
		setRow(--row);
	}

	public void goDown() {
		setRow(++row);
	}

	@Override
	public String toString() {
		return "Pos [row=" + row + ", col=" + col + ", clearFlag=" + clearFlag + "]";
	}
	
}
