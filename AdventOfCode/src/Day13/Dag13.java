package Day13;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import Day11.Stone;

public class Dag13 {

	public static void main(String[] args) {
		Dag13 d = new Dag13();
	}

	private String fileName = "src\\Day13\\input.txt";
	private List<Machine> machines = new ArrayList<>();
	private List<Machine> machines2 = new ArrayList<>();

	public Dag13() {
		long startTime = System.nanoTime(); // Starttijd meten
		readFile(false);

		long tokens = calculateTokens(machines);
		System.out.printf("1. fewest tokens needed part 1 = %d%n", tokens);
		readFile(true);

		tokens = calculateTokens2(machines2);
		System.out.printf("fewest tokens needed part 2 = %d%n", tokens);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000; //
		System.out.printf("Execution time: %d ms%n", duration);
	}

	private long calculateTokens(List<Machine> list) {


		return list.stream().filter(m -> m.isPricePossible()).map(Machine::getTokenCost).reduce(0l, Long::sum);

	}
	private long calculateTokens2(List<Machine> list) {

		return list.stream().filter(m -> m.isPricePossible2()).map(Machine::getTokenCost2).reduce(0l, Long::sum);

	}

	private void readFile(boolean part2) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			List<String> buffer = new ArrayList<>();
			stream.forEach(line -> {
				if (!line.isBlank()) {
					buffer.add(line);
					if (buffer.size() == 3) {
						processThreeLines(buffer, part2);
						buffer.clear();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private int teller = 0;

	private void processThreeLines(List<String> buffer, boolean part2) {
		String buttonALine = buffer.get(0);
		String buttonBLine = buffer.get(1);
		String priceLine = buffer.get(2);

		buttonALine = buttonALine.substring(12);
		String[] xy = buttonALine.split(", Y\\+");
		Button buttonA = new Button(Long.parseLong(xy[0]), Long.parseLong(xy[1]));

		buttonBLine = buttonBLine.substring(12);
		xy = buttonBLine.split(", Y\\+");
		Button buttonB = new Button(Long.parseLong(xy[0]), Long.parseLong(xy[1]));

		priceLine = priceLine.substring(9);
		xy = priceLine.split(", Y\\=");
		Location priceLocation;
		if (!part2) {
			priceLocation = new Location(Long.parseLong(xy[0]), Long.parseLong(xy[1]));
			machines.add(new Machine(buttonA, buttonB, priceLocation));
		} else {
			priceLocation = new Location(Long.parseLong(xy[0]) + 10000000000000l,
					Long.parseLong(xy[1]) + 10000000000000l);
			machines2.add(new Machine(buttonA, buttonB, priceLocation));
		}

	}
}
