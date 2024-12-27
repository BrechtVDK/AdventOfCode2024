package Day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dag8 {
	public static void main(String[] args) {
		Dag8 d = new Dag8();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private char[][] grid;
	private Map<Character, List<Pos>> antennas = new HashMap<>();
	private int gridWidth, gridHeight;

	public Dag8() {
		long startTime = System.currentTimeMillis();
		readFile();
		gridWidth = grid[0].length;
		gridHeight = grid.length;
		mapAntennas();
		antennas.keySet().forEach(c -> determineAllAntinodesOfAntenna(c, false));
		System.out.printf("%d%n", nrOfUniqueAntinodes());
		antennas.keySet().forEach(c -> determineAllAntinodesOfAntenna(c, true));
		System.out.printf("%d%n", nrOfUniqueAntinodesPart2());
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private long nrOfUniqueAntinodes() {
		int counter = 0;
		for (char[] row : grid) {
			for (char c : row) {
				if (c == '#') {
					counter++;
				}
			}
		}
		return counter;
	}

	private long nrOfUniqueAntinodesPart2() {
		int counter = 0;
		for (char[] row : grid) {
			for (char c : row) {
				if (c != '.') {
					counter++;
				}
			}
		}
		return counter;
	}

	private void determineAllAntinodesOfAntenna(char c, boolean part2) {
		for (int i = 0; i < antennas.get(c).size() - 1; i++) {
			for (int j = i + 1; j < antennas.get(c).size(); j++) {
				determineAntinodesOfAntennas(antennas.get(c).get(i), antennas.get(c).get(j), part2);
			}

		}

	}

	private void determineAntinodesOfAntennas(Pos a1, Pos a2, boolean part2) {
		int distCol = Math.abs(a1.col - a2.col);
		int distRow = Math.abs(a1.row - a2.row);
		int an1Row, an2Row, an1Col, an2Col;
		if (a1.row < a2.row) {
			an1Row = a1.row - distRow;
			an2Row = a2.row + distRow;
		} else if (a1.row > a2.row) {
			an1Row = a1.row + distRow;
			an2Row = a2.row - distRow;
		} else {
			an1Row = a1.row;
			an2Row = a2.row;
		}

		if (a1.col < a2.col) {
			an1Col = a1.col - distCol;
			an2Col = a2.col + distCol;
		} else if (a1.col > a2.col) {
			an1Col = a1.col + distCol;
			an2Col = a2.col - distCol;
		} else {
			an1Col = a1.col;
			an2Col = a2.col;
		}

		if (isInGrid(an1Row, an1Col)) {
			grid[an1Row][an1Col] = '#';
		}
		if (isInGrid(an2Row, an2Col)) {
			grid[an2Row][an2Col] = '#';
		}

		if (part2) {
			while (isInGrid(an1Row, an1Col)) {
				grid[an1Row][an1Col] = '#';
				if (a1.row < a2.row) {
					an1Row -= distRow;
				} else if (a1.row > a2.row) {
					an1Row += distRow;
				}
				if (a1.col < a2.col) {
					an1Col -= distCol;
				} else if (a1.col > a2.col) {
					an1Col += distCol;
				}

			}

			while (isInGrid(an2Row, an2Col)) {
				grid[an2Row][an2Col] = '#';
				if (a1.row < a2.row) {
					an2Row += distRow;
				} else if (a1.row > a2.row) {
					an2Row -= distRow;
				}
				if (a1.col < a2.col) {
					an2Col += distCol;
				} else if (a1.col > a2.col) {
					an2Col -= distCol;
				}

			}
		}

	}

	private boolean isInGrid(int row, int col) {
		return !(row < 0 || col < 0 || row >= grid.length || col >= grid[0].length);
	}

	record Pos(int row, int col) {
	}

	private void mapAntennas() {
		for (int row = 0; row < gridHeight; row++) {
			for (int kol = 0; kol < gridWidth; kol++) {
				char c = grid[row][kol];
				if (grid[row][kol] != '.') {
					antennas.computeIfAbsent(c, k -> new ArrayList<>()).add(new Pos(row, kol));
				}
			}
		}

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			grid = stream.map(String::toCharArray).toArray(char[][]::new);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
