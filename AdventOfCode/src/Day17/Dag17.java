package Day17;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dag17 {

	public static void main(String[] args) throws Exception {
		Dag17 d = new Dag17();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private long registerA, registerB, registerC;
	private int pointer = 0;
	private List<Integer> instructions = new ArrayList<>();
	private StringBuilder output = new StringBuilder();
	private String goalString;
	private boolean flag = true;

	public Dag17() throws Exception {
		readFile();
		readInstructions(false);
		System.out.printf("Output part 1: %s%n", output.toString());
		goalString = instructions.stream().map(x -> Integer.toString(x)).collect(Collectors.joining(","));
		part2();
	}

	private void part2() throws Exception {
		int startA=0;
		for (int i = 45015824; i < 100000000l; i++) {
			flag = true;
			registerA = i;
			registerB = 0;
			registerC = 0;
			pointer = 0;
			output = new StringBuilder();
			readInstructions(true);
			System.out.printf("%d: %s%n", i, output.toString());
			if (flag) {
				startA = 1;
				break;
			}
		}
		System.out.printf("Register A must be %d for part 2", startA);
	}

	private void readInstructions(boolean part2) throws Exception {
		while (pointer < instructions.size() - 1) {
			if (!flag) {
				break;
			}
			execute(instructions.get(pointer), instructions.get(pointer + 1), part2);
		}
	}

	private void execute(int opcode, int operand, boolean part2) throws Exception {
		switch ((int) opcode) {
		case 0 -> {
			registerA = (long) (registerA / (Math.pow(2, comboOperand(operand))));
		}
		case 1 -> {
			registerB = registerB ^ operand;
		}
		case 2 -> {
			registerB = comboOperand(operand) % 8;
		}
		case 3 -> {
			if (registerA != 0) {
				pointer = operand - 2;
			}
		}
		case 4 -> {
			registerB = registerB ^ registerC;
		}
		case 5 -> {
			appendOutput(comboOperand(operand) % 8);
			if (part2 && !goalString.startsWith(output.toString())) {
				flag = false; // stop
			}

		}
		case 6 -> {
			registerB = (long) (registerA / (Math.pow(2, comboOperand(operand))));
		}
		case 7 -> {
			registerC = (long) (registerA / (Math.pow(2, comboOperand(operand))));
		}
		}
		pointer += 2;

	}

	private void appendOutput(Object result) {
		if (!output.isEmpty()) {
			output.append(",");
		}
		output.append(result);
	}

	private long comboOperand(int operand) throws Exception {
		return switch ((int) operand) {
		case 0, 1, 2, 3 -> operand;
		case 4 -> registerA;
		case 5 -> registerB;
		case 6 -> registerC;
		default -> throw new Exception();
		};

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				if (line.startsWith("Register A")) {
					registerA = Long.parseLong(line.split(": ")[1]);
				} else if (line.startsWith("Register B")) {
					registerB = Long.parseLong(line.split(": ")[1]);
				} else if (line.startsWith("Register C")) {
					registerC = Long.parseLong(line.split(": ")[1]);
				} else if (line.startsWith("Program")) {
					Arrays.stream(line.split(": ")[1].split(",")).forEach(x -> instructions.add(Integer.parseInt(x)));
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
