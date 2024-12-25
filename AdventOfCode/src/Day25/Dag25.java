package Day25;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Day24.Dag24;

public class Dag25 {

	public static void main(String[] args) {
		Dag25 d = new Dag25();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private List<Lock> locks = new ArrayList<>();
	private List<Key> keys = new ArrayList<>();

	public Dag25() {
		long startTime = System.currentTimeMillis();
		readFile();
		part1();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void part1() {
		int matches = 0;
		for (Lock lock : locks) {
			for (Key key : keys) {
				if (lock.insertKey(key)) {
					matches++;
				}
			}
		}

		System.out.println(matches);

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			List<String> lines = stream.filter(line -> !line.isBlank()).toList();
			boolean lock = false;
			int i = 0;
			char[][] field = new char[7][5];
			for (String line : lines) {
				if (i == 0) {
					lock = line.contains("#");
				}
				field[i++] = line.toCharArray();
				if (i == 7) {
					if (lock) {
						locks.add(new Lock(field));
					} else {
						keys.add(new Key(field));
					}
					i = 0;
					field = new char[7][5];
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}