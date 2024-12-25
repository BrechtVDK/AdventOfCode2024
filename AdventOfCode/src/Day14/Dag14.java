package Day14;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class Dag14 {

	public static void main(String[] args) {
		Dag14 d = new Dag14();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private int[][] field = new int[Robot.SOUTH][Robot.EAST];
	private List<int[][]> quadrants = new ArrayList<>();
	private List<Robot> robotList = new ArrayList<>();
	private Map<Long, Integer> safetyMap = new HashMap<>();

	public Dag14() {
		readFile();
		// PART1

		moveRobots(100);
		mapRobotsToField();
		// printField(field);
		splitInQuadrants();
		// quadrants.forEach(x -> printField(x));
		System.out.printf("safety factor after 100s: %d%n", calculateSafetyFactor());

		// PART 2
		robotList.forEach(x -> x.reset());
		int counter = 1;
		while (counter <= 10000) {
			// reset
			field = new int[Robot.SOUTH][Robot.EAST];
			quadrants = new ArrayList<>();
			moveRobots(1);
			mapRobotsToField();
			splitInQuadrants();
			long safetyFactor = calculateSafetyFactor();
			safetyMap.put(safetyFactor, counter);
			counter++;
		}
		long lowestRating = safetyMap.keySet().stream().min(Long::compare).get();
		System.out.printf("Lowest rating is after %d seconds = christmas tree%n", safetyMap.get(lowestRating));
		
		//image of christmas tree
		robotList.forEach(x -> x.reset());
		field = new int[Robot.SOUTH][Robot.EAST];
		moveRobots(safetyMap.get(lowestRating));
		mapRobotsToField();
		saveFieldAsImage(field, "field_image.png", safetyMap.get(lowestRating));
	}

	private long calculateSafetyFactor() {
		int safetyFactor = 1;
		for (int[][] quadrant : quadrants) {
			int sumQuadrant = 0;
			for (int[] row : quadrant) {
				sumQuadrant += Arrays.stream(row).reduce(Integer::sum).getAsInt();
			}
			if (sumQuadrant > 0) {
				safetyFactor *= sumQuadrant;
			}
		}
		return safetyFactor;

	}

	private void splitInQuadrants() {
		int lengthHorizontal = field[0].length / 2;
		int modHorizontal = field[0].length % 2;
		int lengthVertical = field.length / 2;
		int modVertical = field.length % 2;

		// NORTHWEST
		int[][] quadrant1 = new int[lengthVertical][lengthHorizontal];
		for (int row = 0; row < lengthVertical; row++) {
			for (int kol = 0; kol < lengthHorizontal; kol++) {
				quadrant1[row][kol] = field[row][kol];
			}
		}
		// NORTHEAST
		int[][] quadrant2 = new int[lengthVertical][lengthHorizontal];
		for (int row = 0; row < lengthVertical; row++) {
			for (int kol = 0; kol < lengthHorizontal; kol++) {
				quadrant2[row][kol] = field[row][kol + lengthHorizontal + modHorizontal];
			}
		}
		// SOUTHEAST
		int[][] quadrant3 = new int[lengthVertical][lengthHorizontal];
		for (int row = 0; row < lengthVertical; row++) {
			for (int kol = 0; kol < lengthHorizontal; kol++) {
				quadrant3[row][kol] = field[row + lengthVertical + modVertical][kol + lengthHorizontal + modHorizontal];
			}
		}
		// SOUTHWEST
		int[][] quadrant4 = new int[lengthVertical][lengthHorizontal];
		for (int row = 0; row < lengthVertical; row++) {
			for (int kol = 0; kol < lengthHorizontal; kol++) {
				quadrant4[row][kol] = field[row + lengthVertical + modVertical][kol];
			}
		}

		quadrants.add(quadrant1);
		quadrants.add(quadrant2);
		quadrants.add(quadrant3);
		quadrants.add(quadrant4);
	}

	private void saveFieldAsImage(int[][] field, String fileName, int counter) {
		int cellSize = 10; // Size of each cell in the grid
		int width = field[0].length * cellSize;
		int height = field.length * cellSize;

		// Create a BufferedImage
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();

		// Draw the grid
		for (int row = 0; row < field.length; row++) {
			for (int col = 0; col < field[row].length; col++) {
				if (field[row][col] == 0) {
					g.setColor(Color.white);
				} else if (field[row][col] > 0) {
					g.setColor(Color.black);
				}
				// Draw a filled rectangle
				g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
			}
		}

		g.dispose();

		// Save the image to a file
		try {
			ImageIO.write(image, "png",
					new File("src\\" + this.getClass().getPackage().getName() + "\\images\\" + counter + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printField(int[][] field) {
		for (int row = 0; row < field.length; row++) {
			for (int kol = 0; kol < field[row].length; kol++) {
				System.out.printf("%s", field[row][kol]);
			}
			System.out.println();
		}
		System.out.println();

	}

	private void moveRobots(int times) {
		for (int i = 0; i < times; i++) {
			robotList.forEach(Robot::moveOnce);
		}

	}

	private void mapRobotsToField() {
		for (Robot robot : robotList) {
			int[] robotPos = robot.getPos();
			field[robotPos[1]][robotPos[0]]++;
		}

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				String subLine = line.substring(2);
				int spaceIndex = subLine.indexOf(" ");
				String[] pos = subLine.substring(0, spaceIndex).split(",");
				String[] velocity = subLine.substring(spaceIndex + 3, subLine.length()).split(",");
				robotList.add(new Robot(new int[] { Integer.parseInt(pos[0]), Integer.parseInt(pos[1]) },
						Integer.parseInt(velocity[0]), Integer.parseInt(velocity[1])));

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}