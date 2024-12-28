package Day12;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class Region {
	private char plant;
	private Set<Pos> area;
	private int perimeter, numberOfSides;

	public Region(char plant, Set<Pos> area) {
		this.plant = plant;
		this.area = area;
		setPerimeter();
		setNumberOfSides();
	}

	private void setPerimeter() {
		for (Pos pos : area) {
			if (pos.row == 0 || !area.contains(new Pos(pos.row - 1, pos.col))) {
				perimeter++;
			}
			if (pos.col == 0 || !area.contains(new Pos(pos.row, pos.col - 1))) {
				perimeter++;
			}
			if (pos.row == Dag12.GRIDHEIGHT - 1 || !area.contains(new Pos(pos.row + 1, pos.col))) {
				perimeter++;
			}

			if (pos.col == Dag12.GRIDWIDTH - 1 || !area.contains(new Pos(pos.row, pos.col + 1))) {
				perimeter++;
			}
		}
	}

	private void setNumberOfSides() {
		Map<Integer, TreeSet<Pos>> rows = new HashMap<>();
		Map<Integer, TreeSet<Pos>> cols = new HashMap<>();
		for (Pos pos : area) {
			rows.computeIfAbsent(pos.row, k -> new TreeSet<>()).add(pos);
			cols.computeIfAbsent(pos.col, k -> new TreeSet<>()).add(pos);
		}

		for (Entry<Integer, TreeSet<Pos>> entry : rows.entrySet()) {
			boolean first = true;
			int col = 0;
			for (Pos pos : entry.getValue()) {
				if (entry.getKey() == 0 || !area.contains(new Pos(pos.row - 1, pos.col))) {
					if (first) {
						numberOfSides++;
						first = false;
					} else {
						if (pos.col - col != 1) {
							numberOfSides++;
						}
					}
					col = pos.col;
				}
			}
		}

		for (Entry<Integer, TreeSet<Pos>> entry : rows.entrySet()) {
			boolean first = true;
			int col = 0;
			for (Pos pos : entry.getValue()) {
				if (entry.getKey() == Dag12.GRIDHEIGHT - 1 || !area.contains(new Pos(pos.row + 1, pos.col))) {
					if (first) {
						numberOfSides++;
						first = false;
					} else {
						if (pos.col - col != 1) {
							numberOfSides++;
						}

					}
					col = pos.col;

				}
			}
		}
		for (Entry<Integer, TreeSet<Pos>> entry : cols.entrySet()) {
			boolean first = true;
			int row = 0;
			for (Pos pos : entry.getValue()) {
				if (entry.getKey() == 0 || !area.contains(new Pos(pos.row, pos.col - 1))) {
					if (first) {
						numberOfSides++;
						first = false;
					} else {
						if (pos.row - row != 1) {
							numberOfSides++;
						}
					}
					row = pos.row;
				}
			}
		}
		for (Entry<Integer, TreeSet<Pos>> entry : cols.entrySet()) {
			boolean first = true;
			int row = 0;
			for (Pos pos : entry.getValue()) {
				if (entry.getKey() == Dag12.GRIDWIDTH - 1 || !area.contains(new Pos(pos.row, pos.col + 1))) {
					if (first) {
						numberOfSides++;
						first = false;
					} else {
						if (pos.row - row != 1) {
							numberOfSides++;
						}
					}
					row = pos.row;
				}
			}
		}
	}

	public int getAreaSize() {
		return area.size();
	}

	public int getPerimeter() {
		return perimeter;
	}

	public int getNumberOfSides() {
		return numberOfSides;
	};

	@Override
	public String toString() {
		return "Region: plant = " + plant + ", size = " + getAreaSize() + ", perimeter = " + getPerimeter()
				+ ", sides = " + getNumberOfSides();
	}

}
