package Day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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

	public Dag6() {
		long startTime = System.currentTimeMillis();
		readFile();

		part1();

		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);

	}

	private void part1() {
		Pos start = null;
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

	private void goUp(Pos start) {
		for (int row = start.row; row >= 0; row--) {
			if (grid[row][start.col] == '.') {
				set.add(new Pos(row, start.col));
			} else {
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
				goUp(new Pos(start.row, col + 1));
				return;
			}
		}
	}

	record Pos(int row, int col) {
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			grid = stream.map(String::toCharArray).toArray(char[][]::new);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}