package Day24;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Dag24 {

	public static void main(String[] args) {
		Dag24 m = new Dag24();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private Map<String, Integer> map = new HashMap<>();
	private List<Instruction> instructions = new ArrayList<>();

	public Dag24() {
		long startTime = System.currentTimeMillis();
		readFile();
		part1();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void part1() {
		while (!instructions.stream().allMatch(x -> x.done)) {
			for (Instruction instruction : instructions) {
				if (map.containsKey(instruction.input1) && map.containsKey(instruction.input2)) {
					Integer result = calculate(map.get(instruction.input1), map.get(instruction.input2),
							instruction.gate);
					instruction.done = true;
					map.put(instruction.output, result);
				}
			}
		}
		long i = 1l;
		long number = 0l;
		for (String key : new TreeSet<>(map.keySet())) {
			if (key.startsWith("z")) {
				number += map.get(key) * i;
				i *= 2l;
			}
		}
		System.out.println(number);

	}

	private Integer calculate(Integer input1, Integer input2, String gate) {
		int result; // Variable to store the result

		switch (gate) {
		case "AND":
			result = (input1 == 1 && input2 == 1) ? 1 : 0;
			break;
		case "OR":
			result = (input1 == 1 || input2 == 1) ? 1 : 0;
			break;
		case "XOR":
			result = (input1 != input2) ? 1 : 0;
			break;
		default:
			throw new IllegalArgumentException("Unknown gate: " + gate); // Handle unknown gate
		}

		return result;
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				if (line.contains(":")) {
					String[] split = line.split(": ");
					map.put(split[0], Integer.parseInt(split[1]));
				} else if (line.contains("->")) {
					Scanner sc = new Scanner(line);
					String input1 = sc.next();
					String gate = sc.next();
					String input2 = sc.next();
					sc.next();
					String output = sc.next();
					instructions.add(new Instruction(input1, input2, gate, output));
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class Instruction {
		public String input1, input2, gate, output;
		public boolean done;

		public Instruction(String input1, String input2, String gate, String output) {
			this.input1 = input1;
			this.input2 = input2;
			this.output = output;
			this.gate = gate;
		}

	}
}