package Day11;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dag11 {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Dag11 d = new Dag11();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);

	}

	private String fileName = "src\\Day11\\input.txt";
	private List<Stone> stones = new ArrayList<>();
	private Map<BlinkToStone, Long> memo = new HashMap<>();
	long sum = 0;

	public Dag11() {
		readFile();
		for (Stone stone : stones) {
			sum += blinkToStoneXTimes(25, stone);
		}
		System.out.println(sum);
		sum = 0;
		for (Stone stone : stones) {
			sum += blinkToStoneXTimes(75, stone);
		}
		System.out.println(sum);

	}

	record BlinkToStone(long nrOnStone, int blinkXTimes) {
	}

	private long blinkToStoneXTimes(int times, Stone stone) {
		if (times == 0) {
			return 1;
		}
		BlinkToStone key = new BlinkToStone(stone.getNr(), times);
		if (memo.containsKey(key)) {
			return memo.get(key);
		}

		long sum = 0;

		List<Stone> newStoneList = new ArrayList<>();
		stone.blink().forEach(newStoneList::add);
		for (Stone s : newStoneList) {
			sum += blinkToStoneXTimes(times - 1, s);
		}
		memo.put(key, sum);
		return sum;
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
