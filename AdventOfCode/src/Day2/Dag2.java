package Day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Dag2 {
	private List<int[]> list = new ArrayList<>();
	private List<Integer> unsafeList = new ArrayList<>();
	private int sum = 0;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Dag2 d = new Dag2();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	public Dag2() {
		readFile();
		part1();
		part2();

	}

	private void part1() {
		for (int i = 0; i < list.size(); i++) {
			if (checkReport(list.get(i))) {
				sum++;
			} else {
				unsafeList.add(i);
			}
		}
		System.out.println(sum);

	}

	private void part2() {
		for (Integer i : unsafeList) {
			if (checkReportWithDampener(list.get(i))) {
				sum++;
			}
		}
		System.out.println(sum);
	}

	private boolean checkReportWithDampener(int[] report) {
		boolean safe = true;
		boolean up = true;
		int wrongIndex = 0;
		for (int i = 0; i < report.length; i++) {
			if (i == 0) {
				if (report[i] > report[i + 1]) {
					up = false;
				}
			}
			if (i < report.length - 1) {
				int diff = Math.abs(report[i] - report[i + 1]);
				if (diff < 1 || diff > 3) {
					wrongIndex = i;
					break;
				}
				if (up && report[i] > report[i + 1]) {
					wrongIndex = i;
					break;
				}
				if (!up && report[i] < report[i + 1]) {
					wrongIndex = i;
					break;
				}
			}
		}
		
		//testing the report by removing one level
		for (int diff = -1; diff <= 1; diff++) {
			if (wrongIndex == 0 && diff == -1) {
				continue;
			} else if (wrongIndex == report.length - 1 && diff == 1) {
				continue;
			}
			int[] newReport = new int[report.length - 1];
			int index = 0;
			for (int i = 0; i < report.length; i++) {
				if (i != wrongIndex + diff) {
					newReport[index++] = report[i];
				}
			}
			if (checkReport(newReport)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkReport(int[] report) {
		boolean safe = true;
		boolean up = true;
		for (int i = 0; i < report.length; i++) {
			if (i == 0) {
				if (report[i] > report[i + 1]) {
					up = false;
				}
			}
			if (i < report.length - 1) {
				int diff = Math.abs(report[i] - report[i + 1]);
				if (diff < 1 || diff > 3) {
					safe = false;
					break;
				}
				if (up && report[i] > report[i + 1]) {
					safe = false;
					break;
				}

				if (!up && report[i] < report[i + 1]) {
					safe = false;
					break;
				}
			}
		}
		return safe;
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get("src\\Day2\\input.txt"))) {
			list = stream.map(line -> line.split(" ")).map(split -> {
				int[] report = new int[split.length];
				for (int i = 0; i < split.length; i++) {
					report[i] = Integer.parseInt(split[i]);
				}
				return report;
			}).toList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
