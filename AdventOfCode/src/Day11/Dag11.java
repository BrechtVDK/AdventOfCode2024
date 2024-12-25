package Day11;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Dag11 {

	public static void main(String[] args) {
		Dag11 d = new Dag11();

	}

	private String fileName = "src\\Day11\\input.txt";
	private List<Stone> stones = new ArrayList<>();
	long sum = 0;

	public Dag11() {
		readFile();
		/*
		 * for (int i = 0; i < 25; i++) { blinkToStones(); // stones.forEach(x ->
		 * System.out.print(x.toString())); // System.out.println();
		 * System.out.printf("Stones after blinking %d times: %d%n", i + 1,
		 * stones.size()); }
		 */
		int counter = 1;
		for (Stone stone : stones) {
			System.out.printf("Stone %d%n", counter++);
			sum += blinkToStoneXTimes(75, stone);
		}
		System.out.printf("Stones after blinking 75 times: %d%n", sum);

	}

	private int blinkToStoneXTimes(int times, Stone stone) {
		List<Stone> list = List.of(stone);
		for (int i = 0; i < times; i++) {
			System.out.printf("%d ",i);
			List<Stone> newStoneList = new ArrayList<>();
			list.forEach(s -> s.blink().forEach(blinkStone -> newStoneList.add(blinkStone)));
			list = newStoneList;
		}
		System.out.println();
		return list.size();
	}

	private void blinkToStones() {
		List<Stone> newStoneList = new ArrayList<>();
		stones.forEach(stone -> stone.blink().forEach(blinkStone -> newStoneList.add(blinkStone)));
		stones = newStoneList;
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				Scanner sc = new Scanner(line);
				while (sc.hasNext()) {
					stones.add(new Stone(Long.parseLong(sc.next())));
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
