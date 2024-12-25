package Day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Dag5 {

	public static void main(String[] args) {
		Dag5 d = new Dag5();

	}

	private String fileName = "src\\Day5\\input.txt";
	private List<int[]> rules = new ArrayList<>();
	private List<List<Integer>> updates = new ArrayList<>();
	private List<List<Integer>> correctUpdates = new ArrayList<>();
	int sumMiddles = 0;

	public Dag5() {
		readFile();
		calculate();
		System.out.println("sum= " + sumMiddles);

	}

	private void calculate() {
		for (List<Integer> update : updates) {
			boolean flag = false;
			for (int i = 0; i < update.size(); i++) {
				for (int[] rule : rules) {
					if (rule[0] == update.get(i)) {
						for (int j = 0; j < i; j++) {
							if (update.get(j) == rule[1]) {
								flag = true;
								break;
							}
						}
					}
				}
			}
			if (!flag) {
				correctUpdates.add(update);
			}
		}
		for (List<Integer> list : correctUpdates) {
			int size = list.size();
			int middle = list.get(size / 2);
			sumMiddles += middle;
		}
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
