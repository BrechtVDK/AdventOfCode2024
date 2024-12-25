package Day21;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import Day20.Dag20;

public class Dag21 {
	public static void main(String[] args) {
		Dag21 d = new Dag21();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";

	public Dag21() {
		long startTime = System.currentTimeMillis();
		readFile();

		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void readFile() {

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
