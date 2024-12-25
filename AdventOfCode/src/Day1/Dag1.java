package Day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Dag1 {

	public static void main(String[] args) {
		Dag1 d = new Dag1();
	}

	private String fileName = "src\\Day1\\input.txt";
	private List<Integer> leftList = new ArrayList<>();
	private List<Integer> rightList = new ArrayList<>();
	private Long sumOfDistances = 0L;
	private Long simularity =0L;

	public Dag1() {
		readFile();
		calculate1();
		System.out.printf("sum: %d%n%n",sumOfDistances);
		readFile();
		calculate2();
		System.out.printf("simularity: %d",simularity);
		
	}

	private void calculate1() {
		while (leftList.size() > 0) {
			int minLeft = leftList.stream().min(Integer::compare).get();
			int minRight = rightList.stream().min(Integer::compare).get();
			sumOfDistances += Math.abs(minLeft - minRight);
			leftList.remove(leftList.indexOf(minLeft));
			rightList.remove(rightList.indexOf(minRight));
		}
	}
	private void calculate2() {
		for(int i:leftList) {
			simularity+= i*(rightList.stream().filter(x -> x==i).count());
		}
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				Scanner sc = new Scanner(line);
				leftList.add(Integer.parseInt(sc.next()));
				rightList.add(Integer.parseInt(sc.next()));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
