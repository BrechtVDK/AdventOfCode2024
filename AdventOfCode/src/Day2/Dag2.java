package Day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Dag2 {
	private String fileName = "src\\Day2\\input.txt";
	private List<List<Integer>> list = new ArrayList<>();
	int sum1 = 0;

	public static void main(String[] args) {
		Dag2 d = new Dag2();
	}

	public Dag2() {
		readFile();
		calculate1();
		System.out.printf("puzzel 1: %d%n",sum1);

	}

	private void calculate1() {
		for (List<Integer> row : list) {
			boolean flag = true;
			boolean up=true;
			for (int i = 0; i < row.size(); i++) {
				if(i==0) {
					if(row.get(i)>row.get(i+1)) {
						up=false;
					}
				}
				
				if (i < row.size() - 1) {
					int diff = Math.abs(row.get(i) - row.get(i + 1));
					if (diff < 1 || diff > 3) {
						flag = false;
						break;
					}
					if(up) {
						if(row.get(i)>row.get(i+1)) {
							flag=false;
						}
					}
					if(!up) {
						if(row.get(i)<row.get(i+1)) {
							flag=false;
						}
					}
				}
			}
			if(flag) {
				sum1+=1;
			}
		}

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> {
				Scanner sc = new Scanner(line);
				List<Integer> row = new ArrayList<>();
				while (sc.hasNext()) {
					row.add(Integer.parseInt(sc.next()));
				}
				list.add(row);

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
