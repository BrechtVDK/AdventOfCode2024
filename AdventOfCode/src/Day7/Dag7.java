package Day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Dag7 {
	public static void main(String[] args) {
		Dag7 d = new Dag7();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private List<Equation> equations = new ArrayList<>();

	public Dag7() {
		readFile();
		System.out.printf("Sum of totals of solvable equations part 1 = %d%n", sumOfSolvablePart1Equations());
		System.out.printf("Sum of totals of solvable equations part 2 = %d", sumOfSolvablePart2Equations());
	}

	private long sumOfSolvablePart1Equations() {
		return equations.stream().filter(Equation::isSolvablePart1).map(Equation::getResult).reduce(Long::sum).orElse(0l);
	}

	private long sumOfSolvablePart2Equations() {
		return equations.stream().filter(Equation::isSolvablePart2).map(Equation::getResult).reduce(Long::sum).orElse(0l);
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				String[] lineSplit = line.split(":");
				equations.add(new Equation(Long.parseLong(lineSplit[0]),
						Arrays.stream(lineSplit[1].trim().split(" ")).mapToLong(Long::parseLong).toArray()));
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
