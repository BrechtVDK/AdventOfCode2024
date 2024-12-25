package Day19;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Dag19 {

	public static void main(String[] args) throws Exception {
		Dag19 d = new Dag19();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private TreeSet<String> towels = new TreeSet<>();
	private Set<String> designs = new HashSet<>();
	private Map<String, Long> cache = new HashMap<>();
	int designsPossible = 0;
	long differentWaysPossible = 0;

	public Dag19() {
		long startTime = System.currentTimeMillis();
		readFile();
		checkDesigns();
		System.out.println(designsPossible);
		part2();
		System.out.println(differentWaysPossible);
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void checkDesigns() {
		for (String design : designs) {
			if (isPossible(design, design, 0)) {
				designsPossible += 1;
			}
		}
	}

	private boolean isPossible(String design, String subString, int index) {
		if (index == design.length()) {
			return true;
		}

		String start = subString.substring(0, 1);
		String end = String.valueOf((char) (start.charAt(0) + 1));
		for (String towel : towels.subSet(start, end)) {
			if (subString.startsWith(towel)
					&& isPossible(design, design.substring(index + towel.length()), index + towel.length())) {
				return true;
			}
		}

		return false;

	}

	private void part2() {
		designs.forEach(design -> differentWaysPossible += countPossibleWays(design));
	}

	private Long countPossibleWays(String design) {
		if (cache.containsKey(design)) {
			return cache.get(design);
		}
		long count = 0L;
		for (String towel : towels) {
			if (design.equals(towel)) {
				++count;
			} else if (design.startsWith(towel)) {
				count += countPossibleWays(design.substring(towel.length()));
			}
		}
		cache.put(design, count);
		return count;

	}

	private void readFile() {

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				if (line.contains(",")) {
					Arrays.stream(line.split(", ")).forEach(t -> towels.add(t));
				} else if (line.isBlank()) {
					//
				} else {
					designs.add(line);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
