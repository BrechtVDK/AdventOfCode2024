package Day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Dag4 {

	public static void main(String[] args) {
		Dag4 m = new Dag4();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private char[][] grid;

	public Dag4() {
		long startTime = System.currentTimeMillis();
		readFile();
		part1();
		part2();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void part1() {
		int counter = 0;

		// horizontal left to right
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length - 3; col++) {
				if (grid[row][col] == 'X' && grid[row][col + 1] == 'M' && grid[row][col + 2] == 'A'
						&& grid[row][col + 3] == 'S') {
					counter++;
				}
			}
		}
		// horizontal right to left
		for (int row = 0; row < grid.length; row++) {
			for (int col = grid[row].length - 1; col >= 3; col--) {
				if (grid[row][col] == 'X' && grid[row][col - 1] == 'M' && grid[row][col - 2] == 'A'
						&& grid[row][col - 3] == 'S') {
					counter++;
				}
			}
		}
		// vertical up to down & diagonal left to right down & diagonal right to left
		// down
		for (int row = 0; row < grid.length - 3; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 'X' && grid[row + 1][col] == 'M' && grid[row + 2][col] == 'A'
						&& grid[row + 3][col] == 'S') {
					counter++;
				}

				if (col < grid[row].length - 3 && grid[row][col] == 'X' && grid[row + 1][col + 1] == 'M'
						&& grid[row + 2][col + 2] == 'A' && grid[row + 3][col + 3] == 'S') {
					counter++;

				}
				if (col >= 3 && grid[row][col] == 'X' && grid[row + 1][col - 1] == 'M' && grid[row + 2][col - 2] == 'A'
						&& grid[row + 3][col - 3] == 'S') {
					counter++;

				}
			}
		}
		// vertical down to up & diagonal left to right up & diagonal right to left up
		for (int row = grid.length - 1; row >= 3; row--) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == 'X' && grid[row - 1][col] == 'M' && grid[row - 2][col] == 'A'
						&& grid[row - 3][col] == 'S') {
					counter++;

				}
				if (col < grid[row].length - 3 && grid[row][col] == 'X' && grid[row - 1][col + 1] == 'M'
						&& grid[row - 2][col + 2] == 'A' && grid[row - 3][col + 3] == 'S') {
					counter++;

				}
				if (col >= 3 && grid[row][col] == 'X' && grid[row - 1][col - 1] == 'M' && grid[row - 2][col - 2] == 'A'
						&& grid[row - 3][col - 3] == 'S') {
					counter++;

				}
			}
		}
		System.out.println(counter);
	}

	private void part2() {
		int counter = 0;

		for (int row = 1; row < grid.length - 1; row++) {
			for (int col = 1; col < grid[row].length - 1; col++) {
				if (grid[row][col] == 'A' && ((grid[row + 1][col + 1] == 'M' && grid[row - 1][col - 1] == 'S'
						&& grid[row - 1][col + 1] == 'S' && grid[row + 1][col - 1] == 'M')

						|| (grid[row + 1][col + 1] == 'S' && grid[row - 1][col - 1] == 'M'
								&& grid[row - 1][col + 1] == 'M' && grid[row + 1][col - 1] == 'S')

						|| (grid[row + 1][col + 1] == 'M' && grid[row - 1][col - 1] == 'S'
								&& grid[row - 1][col + 1] == 'M' && grid[row + 1][col - 1] == 'S')

						|| (grid[row + 1][col + 1] == 'S' && grid[row - 1][col - 1] == 'M'
								&& grid[row - 1][col + 1] == 'S' && grid[row + 1][col - 1] == 'M'))) {
					counter++;
				}
			}
		}
		System.out.println(counter);
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			grid = stream.map(String::toCharArray).toArray(char[][]::new);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
