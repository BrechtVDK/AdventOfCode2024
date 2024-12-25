package Day22;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dag22 {
	public static void main(String[] args) {
		Dag22 d = new Dag22();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private List<Long> secretNumbers = new ArrayList<>();
	private Map<Integer, List<Long[]>> map = new HashMap<>();
	private Map<Integer, Map<Long, Long>> sequences = new HashMap<>();
	private Long maxBananas = 0l;

	public Dag22() {
		long startTime = System.currentTimeMillis();
		readFile();
		for (int i = 0; i < 2000; i++) {
			if (i == 0) {
				oneCycle(true, i);
			} else {
				oneCycle(false, i);
			}
		}
		calculateSequences();
		bruteForceSequences();
		// secretNumbers.forEach(System.out::println);
		// map.values()
		// .forEach(x -> x.stream().forEach(y -> System.out.printf("%2d %2d%n", y[0],
		// y.length > 1 ? y[1] : -1)));
		System.out.println(secretNumbers.stream().reduce(Long::sum).get());

		System.out.println(maxBananas);
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void bruteForceSequences() {
		for (int i = 0; i < sequences.keySet().size(); i++) {
			System.out.println(i);
			for (Long sequence : sequences.get(i).keySet()) {
				long bananas = 0l;
				bananas += sequences.get(i).get(sequence);
				// andere overlopen en zoeken op sequence
				for (int j = 0; j < sequences.keySet().size(); j++) {
					if (i != j) {
						Long b = sequences.get(j).get(sequence);
						if (b != null) {
							bananas += b;
						}

					}
				}
				if (bananas > maxBananas) {
					maxBananas = bananas;
				}
			}
		}
	}

	private void calculateSequences() {
		for (int i = 0; i < map.keySet().size(); i++) {
			sequences.put(i, new HashMap<>());
			for (int j = 4; j < map.get(i).size(); j++) {
				Long[] sequence = new Long[] { map.get(i).get(j - 3)[1], map.get(i).get(j - 2)[1],
						map.get(i).get(j - 1)[1], map.get(i).get(j)[1] };
				Long result =sequence[0]*1000+sequence[1]*100+sequence[2]*10+sequence[3];
				Map<Long, Long> m = sequences.get(i);
				m.put(result, map.get(i).get(j)[0]);
				sequences.put(i, m);
			}
		}

	}

	private void oneCycle(boolean first, int cycle) {
		for (int i = 0; i < secretNumbers.size(); i++) {
			if (first) {
				List<Long[]> list = new ArrayList<>();
				list.add(new Long[] { secretNumbers.get(i) % 10 });
				map.put(i, list);
			} else {
				List<Long[]> list = map.get(i);
				Long nr = secretNumbers.get(i) % 10;
				Long diff = nr - map.get(i).get(cycle - 1)[0];
				list.add(new Long[] { nr, diff });
				map.replace(i, list);
			}
			Long nr = secretNumbers.get(i) * 64;
			secretNumbers.set(i, secretNumbers.get(i) ^ nr);
			secretNumbers.set(i, secretNumbers.get(i) % 16777216);
			nr = secretNumbers.get(i) / 32;
			secretNumbers.set(i, secretNumbers.get(i) ^ nr);
			secretNumbers.set(i, secretNumbers.get(i) % 16777216);
			nr = secretNumbers.get(i) * 2048;
			secretNumbers.set(i, secretNumbers.get(i) ^ nr);
			secretNumbers.set(i, secretNumbers.get(i) % 16777216);

		}

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			List<String> lines = stream.toList();
			lines.forEach(line -> secretNumbers.add(Long.parseLong(line)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
