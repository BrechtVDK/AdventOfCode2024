package Day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Dag5 {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Dag5 d = new Dag5();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);

	}

	private String fileName = "src\\Day5\\input.txt";
	private List<int[]> rules = new ArrayList<>();
	private List<List<Integer>> updates = new ArrayList<>();
	private List<List<Integer>> correctUpdates = new ArrayList<>();
	private List<List<Integer>> incorrectUpdates = new ArrayList<>();
	private List<List<Integer>> updated = new ArrayList<>();

	public Dag5() {

		readFile();
		part1();
		part2();

	}

	private void part1() {
		for (List<Integer> update : updates) {
			boolean correct = true;
			for (int i = 0; i < update.size(); i++) {
				for (int[] rule : rules) {
					if (rule[0] == update.get(i)) {
						for (int j = 0; j < i; j++) {
							if (update.get(j) == rule[1]) {
								correct = false;
								break;
							}
						}
					}
				}
			}
			if (correct) {
				correctUpdates.add(update);
			} else {
				incorrectUpdates.add(update);
			}
		}
		System.out.println(sumOfMiddlePages(correctUpdates));
	}

	private int sumOfMiddlePages(List<List<Integer>> updates) {
		int sum = 0;
		for (List<Integer> list : updates) {
			sum += list.get(list.size() / 2);
		}
		return sum;
	}

	private void part2() {
		for (List<Integer> update : incorrectUpdates) {
			boolean correct = false;
			while (!correct) {
				boolean breakFlag = false;
				for (int i = 0; i < update.size(); i++) {
					for (int[] rule : rules) {
						if (rule[0] == update.get(i)) {
							for (int j = 0; j < i; j++) {
								if (update.get(j) == rule[1]) {
									int temp = update.get(j);
									update.set(j, update.get(i));
									update.set(i, temp);
									breakFlag = true;
									break;
								}
							}
							if (breakFlag)
								break;
						}
					}
					if (breakFlag)
						break;
				}
				if (breakFlag)
					continue;
				correct = true;
			}

		}
		System.out.println(sumOfMiddlePages(incorrectUpdates));
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				Scanner sc = new Scanner(line);
				String next = sc.next();
				if (next.length() == 5) {
					String[] ruleString = next.split("\\|");
					int[] rule = new int[2];
					rule[0] = Integer.parseInt(ruleString[0]);
					rule[1] = Integer.parseInt(ruleString[1]);
					rules.add(rule);
				} else if (next.equals("skip")) {

				} else {
					String[] updateString = next.split(",");
					List<Integer> update = new ArrayList<>();
					for (String s : updateString) {
						update.add(Integer.parseInt(s));
					}
					updates.add(update);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
