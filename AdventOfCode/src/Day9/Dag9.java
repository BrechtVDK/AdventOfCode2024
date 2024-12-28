package Day9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

public class Dag9 {
	public static void main(String[] args) {
		Dag9 d = new Dag9();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private String diskMap;
	private String[] diskArray, diskArray2;
	private Map<String, Integer> mapBlocks = new HashMap<>();
	private Map<Integer, Integer> mapFree = new HashMap<>();

	public Dag9() {
		long startTime = System.currentTimeMillis();
		readFile();
		part1();
		part2();
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void part1() {
		buildDisk();
		compactDisk();
		System.out.println(calculateCheckSum(diskArray));
	}

	private void part2() {
		compactDisk2();
		System.out.println(calculateCheckSum(diskArray2));
	}

	private void buildDisk() {
		boolean free = false;
		int nr = 0;
		int totalSize = 0;

		for (char c : diskMap.toCharArray()) {
			totalSize += Character.getNumericValue(c);
		}

		diskArray = new String[totalSize];
		int index = 0;

		for (char c : diskMap.toCharArray()) {
			int count = Character.getNumericValue(c);
			String fill = free ? "." : Integer.toString(nr);
			if (free) {
				nr++;
				mapFree.put(index, count);
			} else {
				mapBlocks.put(Integer.toString(nr), count);
			}
			for (int i = 0; i < count; i++) {
				diskArray[index++] = fill;
			}
			free = !free;
		}

		// for part2: copy
		diskArray2 = Arrays.copyOf(diskArray, totalSize);
	}

	private void compactDisk() {
		Deque<Integer> blockIndexes = new ArrayDeque<>();
		Queue<Integer> freeIndexes = new ArrayDeque<>();
		int index = 0;
		for (String c : diskArray) {
			if (c != ".") {
				blockIndexes.push(index);
			} else {
				freeIndexes.offer(index);
			}
			index++;
		}
		while (!isCompleted()) {
			int firstFreeIndex = freeIndexes.poll();
			int indexBlock = blockIndexes.pop();
			diskArray[firstFreeIndex] = diskArray[indexBlock];
			diskArray[indexBlock] = ".";
		}

	}

	private void compactDisk2() {
		Deque<Integer[]> blockIndexes = new ArrayDeque<>();
		Deque<Integer[]> freeIndexes = new ArrayDeque<>();

		for (int index = 0; index < diskArray2.length; index = index + 0) {
			String c = diskArray2[index];
			int number = 0;
			if (c != ".") {
				number = mapBlocks.get(c);
				blockIndexes.push(new Integer[] { index, number });
			} else {
				number = mapFree.get(index);
				freeIndexes.offer(new Integer[] { index, number });
			}
			index += number;
		}

		while (freeIndexes.peek()[0] < blockIndexes.peek()[0]) {
			Integer[] freeIndex = freeIndexes.poll();
			Integer[] indexBlock = blockIndexes.pop();
			Deque<Integer[]> stack = new ArrayDeque<>();

			while (freeIndex[0] < indexBlock[0]) {
				int startIndex = freeIndex[0];
				if (freeIndex[1] >= indexBlock[1]) {
					for (int i = 0; i < indexBlock[1]; i++) {
						diskArray2[freeIndex[0]++] = diskArray2[indexBlock[0]];
						diskArray2[indexBlock[0]++] = ".";
					}
					if (freeIndex[1] != indexBlock[1]) {
						Integer diff = freeIndex[1] - indexBlock[1];
						freeIndex = new Integer[] { startIndex + indexBlock[1], diff };
						stack.push(freeIndex);
					}
					break;
				} else {
					stack.push(freeIndex);
					if (freeIndexes.isEmpty()) {
						break;
					}
					freeIndex = freeIndexes.poll();
				}
			}
			while (!stack.isEmpty()) {
				freeIndexes.addFirst(stack.pop());
			}
		}

	}

	private boolean isCompleted() {
		boolean freePlace = false;
		for (String s : diskArray) {
			if (s.equals(".")) {
				freePlace = true;
			} else if (freePlace) { // block after freeplace
				return false;
			}
		}
		return freePlace;
	}

	private Long calculateCheckSum(String[] array) {
		long sum = 0l;
		for (int i = 0; i < array.length; i++) {
			if (!array[i].equals(".")) {
				sum += Long.parseLong(array[i]) * i;
			}
		}
		return sum;
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			diskMap = stream.findFirst().get();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
