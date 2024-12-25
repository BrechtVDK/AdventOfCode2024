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
	private char[][] map;
	private Map<Character, List<int[]>> antennas = new HashMap<>();
	private int mapWidth, mapHeight;

	public Dag8() {
		readFile();
		mapWidth = map[0].length;
		mapHeight = map.length;
		mapAntennas();
		printAntennas();
		for (Character c : antennas.keySet()) {
			determineAllAntinodesOfAntenna(c);
		}
		printMap();
		System.out.printf("nr of unique antennas = %d%n", nrOfUniqueAntinodes());
	}

	private long nrOfUniqueAntinodes() {
		int counter = 0;
		for (char[] row : map) {
			for (char c : row) {
				if (c == 'X') {
					counter++;
				}
			}
		}
		return counter;
	}

	private void printMap() {
		Arrays.stream(map).forEach(x -> System.out.println(x));

	}

	private void determineAllAntinodesOfAntenna(char c) {
		for (int i = 0; i < antennas.get(c).size() - 1; i++) {
			for (int j = i + 1; j < antennas.get(c).size(); j++) {
				determineAntinodesOfAntennas(antennas.get(c).get(i), antennas.get(c).get(j));
			}

		}

	}

	private void determineAntinodesOfAntennas(int[] a1, int[] a2) {
		int distX = Math.abs(a1[1] - a2[1]);
		int distY = Math.abs(a1[0] - a2[0]);
		if (a2[1] > a1[1]) {
			if (a1[1] - distX >= 0) {
				if (a2[0] > a1[0]) {
					if (a1[0] - distY >= 0) {
						map[a1[0] - distY][a1[1] - distX] = 'X';

					}
				} else {
					if (a1[0] + distY < mapHeight) {
						map[a1[0] + distY][a1[1] - distX] = 'X';

					}
				}
			}
			if (a2[1] + distX < mapWidth) {
				if (a2[0] > a1[0]) {
					if (a2[0] - distY >= 0) {
						map[a2[0] - distY][a2[1] + distX] = 'X';

					}
				} else {
					if (a2[0] + distY < mapHeight) {
						map[a2[0] + distY][a2[1] + distX] = 'X';

					}
				}
			}
		} else {
			if (a1[1] + distX < mapWidth) {
				if (a2[0] > a1[0]) {
					if (a1[0] - distY >= 0) {
						map[a1[0] - distY][a1[1] + distX] = 'X';

					}
				} else {
					if (a1[0] + distY < mapHeight) {
						map[a1[0] + distY][a1[1] + distX] = 'X';

					}
				}
			}
			if (a2[1] - distX >= 0) {
				if (a2[0] > a1[0]) {
					if (a2[0] - distY >= 0) {
						map[a2[0] - distY][a2[1] - distX] = 'X';
					}
				} else {
					if (a2[0] + distY < mapHeight) {
						map[a2[0] + distY][a2[1] - distX] = 'X';

					}
				}
			}
		}

	}

	private void printAntennas() {
		antennas.entrySet().forEach(x -> System.out.printf("%s %s%n", x.getKey(), x.getValue().stream()
				.map(y -> String.format("[%2d-%2d]", y[0], y[1])).collect(Collectors.joining(" "))));

	}

	private void mapAntennas() {
		for (int row = 0; row < map.length; row++) {
			for (int kol = 0; kol < map[row].length; kol++) {
				char c = map[row][kol];
				if (c != '.') {
					if (!antennas.containsKey(c)) {
						antennas.put(c, new ArrayList<>(List.of(new int[] { row, kol })));
					} else {
						antennas.get(c).add(new int[] { row, kol });
					}
				}
			}
		}

	}

	private void readFile() {
		List<char[]> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				list.add(line.toCharArray());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		map = new char[list.size()][list.get(0).length];
		int counter = 0;
		for (char[] row : list) {
			map[counter++] = row;
		}

	}
}
