package Day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Dag12 {
	public static void main(String[] args) {
		Dag12 d = new Dag12();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private char[][] grid;
	private Set<Pos> checkedPosSet = new HashSet<>();
	public static int GRIDHEIGHT, GRIDWIDTH;

	private List<Region> list = new ArrayList<>();

	public Dag12() {
		long startTime = System.currentTimeMillis();
		readFile();
		part1();
		part2();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void part1() {
		GRIDHEIGHT = grid.length;
		GRIDWIDTH = grid[0].length;

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				Pos pos = new Pos(row, col);
				if (!checkedPosSet.contains(pos)) {
					screenRegion(pos);
				}
			}
		}

		System.out.println(list.stream().mapToLong(region -> region.getAreaSize() * region.getPerimeter())
				.reduce(Long::sum).getAsLong());
	}

	private void part2() {
		System.out.println(list.stream().mapToLong(region -> region.getAreaSize() * region.getNumberOfSides())
				.reduce(Long::sum).getAsLong());
	}

	private void screenRegion(Pos pos) {
		char plant = grid[pos.row][pos.col];
		Set<Pos> area = new HashSet<>();
		area.add(pos);
		checkedPosSet.add(pos);
		area = expand(area, plant, pos);

		list.add(new Region(plant, area));
	}

	private Set<Pos> expand(Set<Pos> area, char plant, Pos pos) {
		if (pos.col < grid[0].length - 1 && grid[pos.row][pos.col + 1] == plant) {
			Pos newPos = new Pos(pos.row, pos.col + 1);
			area = checkPos(newPos, area, plant);
		}
		if (pos.col > 0 && grid[pos.row][pos.col - 1] == plant) {
			Pos newPos = new Pos(pos.row, pos.col - 1);
			area = checkPos(newPos, area, plant);
		}
		if (pos.row < grid.length - 1 && grid[pos.row + 1][pos.col] == plant) {
			Pos newPos = new Pos(pos.row + 1, pos.col);
			area = checkPos(newPos, area, plant);
		}
		if (pos.row > 0 && grid[pos.row - 1][pos.col] == plant) {
			Pos newPos = new Pos(pos.row - 1, pos.col);
			area = checkPos(newPos, area, plant);
		}
		return area;

	}

	private Set<Pos> checkPos(Pos pos, Set<Pos> area, char plant) {
		if (!area.contains(pos)) {
			area.add(pos);
			checkedPosSet.add(pos);
			area = expand(area, plant, pos);
		}
		return area;
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			grid = stream.map(String::toCharArray).toArray(char[][]::new);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
