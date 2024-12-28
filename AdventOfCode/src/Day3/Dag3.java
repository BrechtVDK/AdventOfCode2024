package Day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import Day2.Dag2;

public class Dag3 {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Dag3 d = new Dag3();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	List<int[]> mulList = new ArrayList<>();
	List<int[]> mulList2 = new ArrayList<>();
	Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
	Pattern pattern2 = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don\\'t\\(\\)");
	boolean read = true;

	public Dag3() {
		readFile(true);
		calculate(mulList);
		System.out.println(calculate(mulList));
		readFile(false);
		System.out.println(calculate(mulList2));

	}

	private long calculate(List<int[]> list) {
		return list.stream().mapToLong(x -> x[0] * x[1]).reduce(Long::sum).getAsLong();
	}

	private void readFile(boolean part1) {
		try (Stream<String> stream = Files.lines(Paths.get("src\\Day3\\input.txt"))) {
			stream.forEach(line -> {
				Scanner sc = new Scanner(line);
				if (part1) {
					String match = sc.findInLine(pattern);
					while (match != null) {
						if (match != null) {
							String removeMulAndParenthesis = match.substring(4).replace(")", "");
							String[] numbers = removeMulAndParenthesis.split(",");
							int[] intNumbers = { Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]) };
							mulList.add(intNumbers);
						}
						match = sc.findInLine(pattern);
					}
				}
				if (!part1) {
					String match = sc.findInLine(pattern2);
					while (match != null) {
						if (match.equals("do()")) {
							read = true;
						} else if (match.equals("don't()")) {
							read = false;
						} else if (read) {
							String removeMulAndParenthesis = match.substring(4).replace(")", "");
							String[] numbers = removeMulAndParenthesis.split(",");
							int[] intNumbers = { Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]) };
							mulList2.add(intNumbers);
						}
						match = sc.findInLine(pattern2);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
