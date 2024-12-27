package Day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Dag6 {
	public static void main(String[] args) {
		Dag6 d = new Dag6();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private char[][] grid;
	private Set<Pos> set = new HashSet<>();
	private Set<Pos> set2 = new HashSet<>();
	private boolean part2 = false;
	private Pos start;
	private int loop = 0;

	public Dag6() {
		long startTime = System.currentTimeMillis();
		readFile();
		part1();
		part2();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void part1() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == '^') {
					start = new Pos(row, col);
					grid[row][col] = '.';
					break;
				}
			}
		}
		set.add(start);
		goUp(start);
		System.out.println(set.size());

	}

	private void part2() {
		part2 = true;

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == '.') {
					checkForLoop(row, col);
				}
			}

		}
		System.out.println(loop);

	}

	private void checkForLoop(int row, int col) {
		set2.clear();
		grid[row][col] = '#';
		set.add(new Pos(start.row, start.col, '^'));
		goUp(start);
		grid[row][col] = '.';
	}

	private void goUp(Pos start) {
		for (int row = start.row; row >= 0; row--) {
			if (grid[row][start.col] == '.') {
				set.add(new Pos(row, start.col));
			} else {
				if (part2) {
					if (checkSet(new Pos(row + 1, start.col, '>'))) {
						return;
					} else {
						set2.add(new Pos(row + 1, start.col, '>'));
					}
				}
				goRight(new Pos(row + 1, start.col));
				return;
			}
		}

	}

	private void goRight(Pos start) {
		for (int col = start.col; col < grid.length; col++) {

			if (grid[start.row][col] == '.') {
				set.add(new Pos(start.row, col));
			} else {
				if (part2) {
					if (checkSet(new Pos(start.row, col - 1, 'v'))) {
						return;
					} else {
						set2.add(new Pos(start.row, col - 1, 'v'));
					}
				}
				goDown(new Pos(start.row, col - 1));
				return;
			}
		}
	}

	private void goDown(Pos start) {
		for (int row = start.row; row < grid.length; row++) {
			if (grid[row][start.col] == '.') {
				set.add(new Pos(row, start.col));
			} else {
				if (part2) {
					if (checkSet(new Pos(row - 1, start.col, '<'))) {
						return;
					} else {
						set2.add(new Pos(row - 1, start.col, '<'));
					}
				}
				goLeft(new Pos(row - 1, start.col));
				return;
			}
		}

	}

	private void goLeft(Pos start) {
		for (int col = start.col; col >= 0; col--) {
			if (grid[start.row][col] == '.') {
				set.add(new Pos(start.row, col));
			} else {
				if (part2) {
					if (checkSet(new Pos(start.row, col + 1, '^'))) {
						return;
					}
					set2.add(new Pos(start.row, col + 1, '^'));
				}

				goUp(new Pos(start.row, col + 1));
				return;
			}
		}

	}

	private boolean checkSet(Pos pos) {
		if (set2.contains(pos)) {
			loop++;
			return true;
		}
		return false;
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			grid = stream.map(String::toCharArray).toArray(char[][]::new);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}