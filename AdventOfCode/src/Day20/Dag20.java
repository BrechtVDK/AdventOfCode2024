package Day20;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Dag20 {
	public static void main(String[] args) {
		Dag20 d = new Dag20();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	// Savings, number
	private Map<Integer, Integer> map = new HashMap<>();
	private int NUMBER_OF_ROWS, NUMBER_OF_COLS;
	private Pos[][] raceTrack;

	public Dag20() {
		long startTime = System.currentTimeMillis();
		readFile();
		NUMBER_OF_ROWS = raceTrack.length;
		NUMBER_OF_COLS = raceTrack[0].length;
		setRanks();
		calculateCheatWaysPart1();
		System.out.println(map.entrySet().stream().filter(x -> x.getKey() >= 100).map(x -> x.getValue())
				.reduce(Integer::sum).orElse(0));
		calcualteCheatWaysPart2();
		System.out.println(map.entrySet().stream().filter(x -> x.getKey() >= 100).map(x -> x.getValue())
				.reduce(Integer::sum).orElse(0));

		map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getKey))
				.forEach(x -> System.out.printf("%d: %d%n", x.getKey(), x.getValue()));
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void calcualteCheatWaysPart2() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int col = 0; col < NUMBER_OF_COLS; col++) {
				if ("S.".indexOf(raceTrack[row][col].c) > -1) {
					checkAllPossibleEndpoints(raceTrack[row][col]);
				}
			}
		}

	}

	private void checkAllPossibleEndpoints(Pos start) {
		for (int i = 3; i <= 20; i++) {
			for (Direction dir : Direction.values()) {
				int newRow = start.row + i * dir.rowDelta;
				int newCol = start.col + i * dir.colDelta;
				if (newRow >= 0 && newRow < NUMBER_OF_ROWS && newCol >= 0 && newCol < NUMBER_OF_COLS) {
					Pos end = raceTrack[newRow][newCol];
					if (end.c > -1) {
						checkTwoPos(start, end, i);
					}
				}
			}
		}

	}

	private void calculateCheatWaysPart1() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int col = 0; col < NUMBER_OF_COLS; col++) {
				if (raceTrack[row][col].c == '#') {
					Pos up = getNeighbor(row - 1, col);
					Pos down = getNeighbor(row + 1, col);
					if (checkTwoPos(up, down, 2))
						continue;

					Pos left = getNeighbor(row, col - 1);
					Pos right = getNeighbor(row, col + 1);
					checkTwoPos(left, right, 2);
				}
			}
		}
	}

	private Pos getNeighbor(int row, int col) {
		if (row < 0 || row >= NUMBER_OF_ROWS || col < 0 || col >= NUMBER_OF_COLS) {
			return null;
		}
		return raceTrack[row][col];
	}

	private boolean checkTwoPos(Pos pos1, Pos pos2, int seconds) {
		if (pos1 != null && pos2 != null && pos1.rank > -1 && pos2.rank > -1) {
			int saving = Math.abs(pos1.rank - pos2.rank) - seconds;
			map.merge(saving, 1, Integer::sum);
			return true;
		}
		return false;

	}

	private void setRanks() {
		Pos pos = null;
		int rank = 0;
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int col = 0; col < NUMBER_OF_COLS; col++) {
				if (raceTrack[row][col].c == 'S') {
					pos = raceTrack[row][col];
					pos.setRank(rank++);
					break;
				}
			}
		}

		while (pos.c != 'E') {
			pos = getNextPos(pos);
			pos.setRank(rank++);
		}
	}

	private Pos getNextPos(Pos pos) {

		for (Direction dir : Direction.values()) {
			int newRow = pos.row + dir.rowDelta;
			int newCol = pos.col + dir.colDelta;

			if (newRow >= 0 && newRow < NUMBER_OF_ROWS && newCol >= 0 && newCol < NUMBER_OF_COLS) {
				Pos neighbor = raceTrack[newRow][newCol];
				if ((neighbor.c == '.' || neighbor.c == 'E') && neighbor.rank == -1) {
					return neighbor;
				}
			}
		}
		return null; // Geen geldige buur gevonden
	}

	private enum Direction {
		UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

		private final int rowDelta;
		private final int colDelta;

		Direction(int rowDelta, int colDelta) {
			this.rowDelta = rowDelta;
			this.colDelta = colDelta;
		}

	}

	private class Pos {
		public int col, row, rank;
		public char c;

		public Pos(int col, int row, char c, int rank) {
			this.col = col;
			this.row = row;
			this.c = c;
			this.rank = rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		@Override
		public String toString() {
			return "[col=" + col + ", row=" + row + ", rank=" + rank + ", c=" + c + "]";
		}
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			List<String> lines = stream.toList();
			raceTrack = new Pos[lines.size() - 2][]; // Vermijd randen
			for (int row = 1; row < lines.size() - 1; row++) { // Vermijd randen
				String line = lines.get(row);
				Pos[] rowArray = new Pos[line.length() - 2];
				for (int col = 1; col < line.length() - 1; col++) { // Vermijd buitenste kolommen
					rowArray[col - 1] = new Pos(col - 1, row - 1, line.charAt(col), -1);
				}
				raceTrack[row - 1] = rowArray; // Voeg direct toe aan raceTrack
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
