package Day15;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dag15 {

	public static void main(String[] args) {
		Dag15 d = new Dag15();

	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private char[][] map, map2;
	private List<Character> instructions = new ArrayList<>();
	private Pos posRobot;
	private Set<Pos> setOfPosDown = new HashSet<>();
	private Set<Pos> setOfPosUp = new HashSet<>();

	public Dag15() {
		readFile();
		determineMap2();
		determinePosRobot(map);
		doAllInstructions(map);
		System.out.printf("Sum of all boxes part 1: %d%n", sumOfAllBoxes(map));
		determinePosRobot(map2);
		//doAllInstructions(map2);
		consoleApp();
		//printMap(map2);
		System.out.printf("Sum of all boxes part 2: %d%n", sumOfAllBoxes(map2));

	}

	private void consoleApp() {
		Scanner sc = new Scanner(System.in);
		printMap(map2);
		boolean go = true;
		while (go) {
			System.out.println("move with arrows (0 to quit)");
			int move = sc.nextInt();
			switch (move) {
			case 2 -> goDown(map2);
			case 4 -> goLeft(map2);
			case 6 -> goRight(map2);
			case 8 -> goUp(map2);
			case 0 -> go = false;
			}
			printMap(map2);
		}
	}

	private void determineMap2() {
		map2 = new char[map.length][map[0].length * 2];
		for (int row = 0; row < map.length; row++) {
			int newKol = 0;
			{
			}
			for (int kol = 0; kol < map[row].length; kol++) {
				if (map[row][kol] == 'O') {
					map2[row][newKol++] = '[';
					map2[row][newKol++] = ']';
				} else if (map[row][kol] == '@') {
					map2[row][newKol++] = '@';
					map2[row][newKol++] = '.';
				} else {
					map2[row][newKol++] = map[row][kol];
					map2[row][newKol++] = map[row][kol];
				}
			}
		}

	}

	private long sumOfAllBoxes(char[][] map) {
		long sum = 0;
		for (int row = 0; row < map.length; row++) {
			for (int kol = 0; kol < map[row].length; kol++) {
				if (map[row][kol] == 'O') {
					sum += 100 * row + kol;
				}
				if(map[row][kol]=='[') {
					sum += 100 * row + kol;
				}
			}
		}
		return sum;

	}

	private void doAllInstructions(char[][] map) {
		for (Character instruction : instructions) {
			doInstruction(instruction, map);

		}
	}

	private void determinePosRobot(char[][] map) {
		for (int row = 0; row < map.length; row++) {
			for (int kol = 0; kol < map[row].length; kol++) {
				if (map[row][kol] == '@') {
					posRobot = new Pos(row, kol);
				}
			}
		}

	}

	private void doInstruction(Character instruction, char[][] map) {
		switch (instruction) {
		case '^' -> goUp(map);
		case 'v' -> goDown(map);
		case '<' -> goLeft(map);
		case '>' -> goRight(map);
		}

	}

	private void goRight(char[][] map) {

		if (map[posRobot.row][posRobot.col + 1] == '#') {

		}
		// swap
		else if (map[posRobot.row][posRobot.col + 1] == '.') {
			swap(posRobot, new Pos(posRobot.row, posRobot.col + 1), map);
			posRobot.goRight();

		}
		// 'O'
		else if (map[posRobot.row][posRobot.col + 1] == 'O') {
			Pos firstPosO = new Pos(posRobot.row, posRobot.col + 1);
			Pos posO = new Pos(firstPosO.row, firstPosO.col);
			while (map[posO.row][posO.col + 1] == 'O') {
				posO.goRight();
			}
			if (map[posO.row][posO.col + 1] == '.') {
				posO.goRight();
				swap(posRobot, posO, map);
				posRobot.setCol(posO.col);
				swap(posRobot, firstPosO, map);
				posRobot.setCol(firstPosO.col);
			}

		} // '['
		else {
			goToRight2(map, new Pos(posRobot.row, posRobot.col), posRobot.col);

		}

	}

	private void goToRight2(char[][] map, Pos pos, int startCol) {
		if (map[posRobot.row][posRobot.col + 3] == '#') {
			return;
		} else if (map[pos.row][pos.col + 3] == '[') {
			pos.goRightTwice();
			goToRight2(map, pos, startCol);

		} else if (map[pos.row][pos.col + 3] == '.') {
			map[pos.row][startCol] = '.';
			map[pos.row][startCol + 1] = '@';
			posRobot.setCol(startCol + 1);
			for (int col = startCol + 2; col <= pos.col + 3; col++) {
				switch (map[pos.row][col]) {
				case '[', '.' -> map[pos.row][col] = ']';
				case ']' -> map[pos.row][col] = '[';
				}
			}
		}

	}

	private void goLeft(char[][] map) {
		if (map[posRobot.row][posRobot.col - 1] == '#') {

		}
		// swap
		else if (map[posRobot.row][posRobot.col - 1] == '.') {
			swap(posRobot, new Pos(posRobot.row, posRobot.col - 1), map);
			posRobot.goLeft();

		}
		// 'O'
		else if (map[posRobot.row][posRobot.col - 1] == 'O') {
			Pos firstPosO = new Pos(posRobot.row, posRobot.col - 1);
			Pos posO = new Pos(firstPosO.row, firstPosO.col);
			while (map[posO.row][posO.col - 1] == 'O') {
				posO.goLeft();
			}
			if (map[posO.row][posO.col - 1] == '.') {
				posO.goLeft();
				swap(posRobot, posO, map);
				posRobot.setCol(posO.col);
				swap(posRobot, firstPosO, map);
				posRobot.setCol(firstPosO.col);
			}
		}
		// ']'
		else {
			goToLeft2(map, new Pos(posRobot.row, posRobot.col), posRobot.col);
		}

	}

	private void goToLeft2(char[][] map, Pos pos, int startCol) {

		if (map[posRobot.row][posRobot.col - 3] == '#') {
			return;
		}
		if (map[pos.row][pos.col - 3] == ']') {
			pos.goLeftTwice();
			goToLeft2(map, pos, startCol);
			return;
		}
		if (map[pos.row][pos.col - 3] == '.') {
			map[pos.row][startCol] = '.';
			map[pos.row][startCol - 1] = '@';
			posRobot.setCol(startCol - 1);
			for (int col = startCol - 2; col >= pos.col - 3; col--) {
				switch (map[pos.row][col]) {
				case '[' -> map[pos.row][col] = ']';
				case ']', '.' -> map[pos.row][col] = '[';
				}
				
			}
		}

	}

	private void goDown(char[][] map) {
		if (map[posRobot.row + 1][posRobot.col] == '#') {

			return;
		}
		// swap
		if (map[posRobot.row + 1][posRobot.col] == '.') {
			swap(posRobot, new Pos(posRobot.row + 1, posRobot.col), map);
			posRobot.goDown();

			return;
		}
		// 'O'
		if (map[posRobot.row + 1][posRobot.col] == 'O') {
			Pos firstPosO = new Pos(posRobot.row + 1, posRobot.col);
			Pos posO = new Pos(firstPosO.row, firstPosO.col);
			while (map[posO.row + 1][posO.col] == 'O') {
				posO.goDown();
			}
			if (map[posO.row + 1][posO.col] == '.') {
				posO.goDown();
				swap(posRobot, posO, map);
				posRobot.setRow(posO.row);
				swap(posRobot, firstPosO, map);
				posRobot.setRow(firstPosO.row);
			}
		}
		// '[ or ]'
		else {
			goDown2(map, new Pos(posRobot.row, posRobot.col), posRobot.row);
		}

	}

	private void goDown2(char[][] map, Pos pos, int startRow) {
		// easiest
		if (map[pos.row + 1][pos.col] == '[') {
			// free to move
			if (map[pos.row + 2][pos.col] == '.' && map[pos.row + 2][pos.col + 1] == '.') {
				swap(new Pos(pos.row + 1, pos.col), new Pos(pos.row + 2, pos.col), map);
				swap(new Pos(pos.row + 1, pos.col + 1), new Pos(pos.row + 2, pos.col + 1), map);
				if (pos.row == startRow) {
					swap(posRobot, new Pos(pos.row + 1, pos.col), map);
					posRobot.goDown();
				}
				// easy check
			} else {
				setOfPosDown.clear();
				goDownAndRemember(posRobot);
				checkTheDownSet();
			}

		} else if (map[pos.row + 1][pos.col] == ']') {
			// free to move{}
			if (map[pos.row + 2][pos.col] == '.' && map[pos.row + 2][pos.col - 1] == '.') {
				swap(new Pos(pos.row + 1, pos.col), new Pos(pos.row + 2, pos.col), map);
				swap(new Pos(pos.row + 1, pos.col - 1), new Pos(pos.row + 2, pos.col - 1), map);
				if (pos.row == startRow) {
					swap(posRobot, new Pos(pos.row + 1, pos.col), map);
					posRobot.goDown();
				}
			} else {
				setOfPosDown.clear();
				goDownAndRemember(posRobot);
				checkTheDownSet();
			}

		}
	}

	private void checkTheDownSet() {
		int lowestRow = setOfPosDown.stream().map(p -> p.row).max(Integer::compareTo).get();
		boolean lowestRowFree = setOfPosDown.stream().filter(p -> p.row == lowestRow).allMatch(p -> p.clearFlag);
		List<Integer> cols = setOfPosDown.stream().map(p -> p.col).distinct().collect(Collectors.toList());
		boolean lowestRowEachColumnFree = true;
		for (int col : cols) {
			int lowest = setOfPosDown.stream().filter(p -> p.col == col).map(p -> p.row).max(Integer::compareTo).get();
			if (!setOfPosDown.stream().filter(p -> p.row == lowestRow && p.col == col).allMatch(p -> p.clearFlag)) {
				lowestRowEachColumnFree = false;
				break;
			}
		}
		if (lowestRowEachColumnFree && lowestRowFree) {
			moveTheDownSet();
		}

	}

	private void moveTheDownSet() {
		char[][] map2Copy = new char[map2.length][map2[0].length];
		for (int row = 0; row < map2.length; row++) {
			for (int col = 0; col < map2[row].length; col++) {
				map2Copy[row][col] = map2[row][col];
			}
		}

		for (Pos pos : setOfPosDown) {
			if (pos.equals(posRobot)) {
				map2Copy[posRobot.row][posRobot.col] = '.';
				map2Copy[posRobot.row + 1][posRobot.col] = '@';
			}
			map2Copy[pos.row + 1][pos.col] = map2[pos.row][pos.col];
			if (setOfPosDown.contains(new Pos(pos.row - 1, pos.col))) {
				map2Copy[pos.row][pos.col] = map2[pos.row - 1][pos.col];
			} else {
				map2Copy[pos.row][pos.col] = '.';
			}
		}
		posRobot.goDown();
		for (int row = 0; row < map2.length; row++) {
			for (int col = 0; col < map2[row].length; col++) {
				map2[row][col] = map2Copy[row][col];
			}
		}

	}

	private void checkTheUpSet() {
		int highestRow = setOfPosUp.stream().map(p -> p.row).min(Integer::compareTo).get();
		boolean highestRowFree = setOfPosUp.stream().filter(p -> p.row == highestRow).allMatch(p -> p.clearFlag);
		List<Integer> cols = setOfPosUp.stream().map(p -> p.col).distinct().collect(Collectors.toList());
		boolean highestRowEachColumnFree = true;
		for (int col : cols) {
			int highest = setOfPosUp.stream().filter(p -> p.col == col).map(p -> p.row).min(Integer::compareTo).get();
			if (!setOfPosUp.stream().filter(p -> p.row == highestRow && p.col == col).allMatch(p -> p.clearFlag)) {
				highestRowEachColumnFree = false;
				break;
			}
		}
		if (highestRowEachColumnFree && highestRowFree) {
			moveTheUpSet();
		}

	}

	private void moveTheUpSet() {
		char[][] map2Copy = new char[map2.length][map2[0].length];
		for (int row = 0; row < map2.length; row++) {
			for (int col = 0; col < map2[row].length; col++) {
				map2Copy[row][col] = map2[row][col];
			}
		}

		for (Pos pos : setOfPosUp) {
			if (pos.equals(posRobot)) {
				map2Copy[posRobot.row][posRobot.col] = '.';
				map2Copy[posRobot.row - 1][posRobot.col] = '@';
			}
			map2Copy[pos.row - 1][pos.col] = map2[pos.row][pos.col];
			if (setOfPosUp.contains(new Pos(pos.row + 1, pos.col))) {
				map2Copy[pos.row][pos.col] = map2[pos.row + 1][pos.col];
			} else {
				map2Copy[pos.row][pos.col] = '.';
			}
		}
		posRobot.goUp();
		for (int row = 0; row < map2.length; row++) {
			for (int col = 0; col < map2[row].length; col++) {
				map2[row][col] = map2Copy[row][col];
			}
		}

	}

	private void goDownAndRemember(Pos pos) {
		if (map2[pos.row + 1][pos.col] == '.') {
			pos.setClearFlag(true);
			setOfPosDown.add(pos);
		} else if (map2[pos.row + 1][pos.col] == '#') {
			setOfPosDown.add(pos);
		} else { // +1[ or ]
			setOfPosDown.add(pos);
			goDownAndRemember(new Pos(pos.row + 1, pos.col));
		}
		// neighbours when not @
		if (map2[pos.row][pos.col] == ']') {
			Pos leftPos = new Pos(pos.row, pos.col - 1);
			if (!setOfPosDown.contains(leftPos)) {
				goDownAndRemember(leftPos);
			}
		} else if (map2[pos.row][pos.col] == '[') {
			Pos rightPos = new Pos(pos.row, pos.col + 1);
			if (!setOfPosDown.contains(rightPos)) {
				goDownAndRemember(rightPos);
			}
		}

	}

	private void goUpAndRemember(Pos pos) {
		if (map2[pos.row - 1][pos.col] == '.') {
			pos.setClearFlag(true);
			setOfPosUp.add(pos);
		} else if (map2[pos.row - 1][pos.col] == '#') {
			setOfPosUp.add(pos);
		} else { // -1[ or ]
			setOfPosUp.add(pos);
			goUpAndRemember(new Pos(pos.row - 1, pos.col));
		}
		// neighbours when not @
		if (map2[pos.row][pos.col] == ']') {
			Pos leftPos = new Pos(pos.row, pos.col - 1);
			if (!setOfPosUp.contains(leftPos)) {
				goUpAndRemember(leftPos);
			}
		} else if (map2[pos.row][pos.col] == '[') {
			Pos rightPos = new Pos(pos.row, pos.col + 1);
			if (!setOfPosUp.contains(rightPos)) {
				goUpAndRemember(rightPos);
			}
		}

	}

	private void goUp(char[][] map) {
		if (map[posRobot.row - 1][posRobot.col] == '#') {

		}
		// swap
		else if (map[posRobot.row - 1][posRobot.col] == '.') {
			swap(posRobot, new Pos(posRobot.row - 1, posRobot.col), map);
			posRobot.goUp();
			return;
		}
		// 'O'
		else if (map[posRobot.row - 1][posRobot.col] == 'O') {
			Pos firstPosO = new Pos(posRobot.row - 1, posRobot.col);
			Pos posO = new Pos(firstPosO.row, firstPosO.col);
			while (map[posO.row - 1][posO.col] == 'O') {
				posO.goUp();
			}
			if (map[posO.row - 1][posO.col] == '.') {
				posO.goUp();
				swap(posRobot, posO, map);
				posRobot.setRow(posO.row);
				swap(posRobot, firstPosO, map);
				posRobot.setRow(firstPosO.row);
			}
		}
		// '[ or ]'
		else {
			goUp2(map, new Pos(posRobot.row, posRobot.col), posRobot.row);
		}
	}

	private void goUp2(char[][] map, Pos pos, int startRow) {
		// easiest
		if (map[pos.row - 1][pos.col] == '[') {
			// free to move
			if (map[pos.row - 2][pos.col] == '.' && map[pos.row - 2][pos.col + 1] == '.') {
				swap(new Pos(pos.row - 1, pos.col), new Pos(pos.row - 2, pos.col), map);
				swap(new Pos(pos.row - 1, pos.col + 1), new Pos(pos.row - 2, pos.col + 1), map);
				if (pos.row == startRow) {
					swap(posRobot, new Pos(pos.row - 1, pos.col), map);
					posRobot.goUp();
				}
				// easy check
			} else {
				setOfPosUp.clear();
				goUpAndRemember(posRobot);
				checkTheUpSet();
			}

		} else if (map[pos.row - 1][pos.col] == ']') {
			// free to move{}
			if (map[pos.row - 2][pos.col] == '.' && map[pos.row - 2][pos.col - 1] == '.') {
				swap(new Pos(pos.row - 1, pos.col), new Pos(pos.row - 2, pos.col), map);
				swap(new Pos(pos.row - 1, pos.col - 1), new Pos(pos.row - 2, pos.col - 1), map);
				if (pos.row == startRow) {
					swap(posRobot, new Pos(pos.row - 1, pos.col), map);
					posRobot.goUp();
				}
			} else {
				setOfPosUp.clear();
				goUpAndRemember(posRobot);
				checkTheUpSet();
			}

		}
	}

	private void swap(Pos pos1, Pos pos2, char[][] map) {
		char temp = map[pos1.row][pos1.col];
		map[pos1.row][pos1.col] = map[pos2.row][pos2.col];
		map[pos2.row][pos2.col] = temp;
	}

	private void printMap(char[][] map) {
		Arrays.stream(map).forEach(x -> System.out.println(x));
	}

	private void readFile() {
		List<char[]> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				if (line.isBlank()) {
					// nothing
				}
				// map
				else if (line.startsWith("#")) {
					list.add(line.toCharArray());
				}
				// instructions
				else {
					Arrays.stream(line.split("")).map(str -> str.charAt(0))
							.forEach(instruction -> instructions.add(instruction));
				}
			});
			map = new char[list.size()][list.get(0).length];
			for (int i = 0; i < list.size(); i++) {
				map[i] = list.get(i);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
