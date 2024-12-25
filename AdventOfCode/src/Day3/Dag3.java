package Day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Dag3 {

	public static void main(String[] args) {
		Dag3 d = new Dag3();
	}

	private String fileName = "src\\Day3\\input.txt";
	List<int[]> mulList = new ArrayList<>();
	List<int[]>  mulList2 = new ArrayList<>();
	Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
	Pattern pattern2 = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
	boolean read = true;
	Long sum = 0l;

	public Dag3() {
		readFile(true);
		calculate(mulList);
		System.out.printf("Sum of multiples= %d%n", sum);
		readFile(false);
		sum=0l;
		calculate(mulList2);

		System.out.printf("Sum of multiples part2= %d", sum);
	}

	private void calculate(List<int[]> list) {
		list.forEach(x -> sum += (x[0] * x[1]));
	}

	private void readFile(boolean part1) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				Scanner sc = new Scanner(line);
				String match = sc.findInLine(pattern);
				if (part1) {
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
					while (match != null) {
					
						if (match.equals("do()")) {
							read = true;
						} else if (match.equals("don't()")) {
							read = false;
						}else if(read) {
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
