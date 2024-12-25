package Day23;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dag23 {
	public static void main(String[] args) {
		Dag23 d = new Dag23();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private Map<String, Set<String>> map = new HashMap<>();
	private List<String[]> koppels = new ArrayList<>();
	private Set<List<String>> set = new HashSet<>();
	private Set<List<String>> set2 = new HashSet<>();

	public Dag23() {
		long startTime = System.currentTimeMillis();
		readFile();
		part(false);
		System.out.println(set.stream().filter(x -> x.size() == 3 && x.stream().anyMatch(pc -> pc.startsWith("t"))).count());
		part(true);
		System.out.println(Collections.max(set2, Comparator.comparingInt(List::size)).stream().collect(Collectors.joining(",")));
		System.out.printf("%n%d ms%n", System.currentTimeMillis() - startTime);
	}

	private void part(boolean part2) {
		if (!part2) {
			for (String[] koppel : koppels) {
				map.computeIfAbsent(koppel[0], k -> new HashSet<>()).add(koppel[1]);
				map.computeIfAbsent(koppel[1], k -> new HashSet<>()).add(koppel[0]);
			}
		}
		for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
			for (String pc : entry.getValue()) {
				List<String> list = new ArrayList<>();
				list.add(entry.getKey());
				list.add(pc);

				for (String pc2 : map.get(pc)) {
					if (entry.getValue().contains(pc2)) {
						boolean flag = true;
						for (String s : list) {
							if (!map.get(s).contains(pc2)) {
								flag = false;
								break;
							}
						}
						if (flag) {
							list.add(pc2);
						}
					}
				}

				Collections.sort(list);

				if (part2) {
					set2.add(list);
				} else {
					if (list.size() > 3) {
						List<List<String>> combinations = generateCombinations(list, 3);
						for (List<String> l : combinations) {
							Collections.sort(l);
							set.add(l);
						}
					} else {
						set.add(list);
					}
				}
			}
		}

	}

	private List<List<String>> generateCombinations(List<String> items, int r) {
		List<List<String>> result = new ArrayList<>();
		combine(items, r, 0, new ArrayList<>(), result);
		return result;
	}

	private void combine(List<String> items, int r, int start, List<String> current, List<List<String>> result) {
		if (current.size() == r) {
			result.add(new ArrayList<>(current));
			return;
		}

		for (int i = start; i < items.size(); i++) {
			current.add(items.get(i));
			combine(items, r, i + 1, current, result);
			current.remove(current.size() - 1);
		}
	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			List<String> lines = stream.toList();
			lines.forEach(line -> koppels.add(line.split("-")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}