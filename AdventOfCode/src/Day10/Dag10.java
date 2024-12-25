package Day10;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Dag10 {

	public static void main(String[] args) {
		Dag10 d = new Dag10();
	}

	private String fileName = "src\\" + this.getClass().getPackage().getName() + "\\input.txt";
	private int[][] topo;
	private List<int[]> trailheads = new ArrayList<>();
	// headnr and score
	private Map<Integer, Integer> map = new HashMap<>();
	private Map<Integer, Integer> map2 = new HashMap<>();

	public Dag10() {
		readFile();
		mapStartpoints();
		for (int i = 0; i < trailheads.size(); i++) {
			goToTop(trailheads.get(i), i, makeCopy(topo), false);
			goToTop(trailheads.get(i), i, makeCopy(topo), true);
		}
		System.out.printf("sum of the scores of all trailheads %d%n%n",
				map.values().stream().reduce(Integer::sum).get());
		System.out.printf("sum of the ratings of all trailheads %d", map2.values().stream().reduce(Integer::sum).get());

	}

	private int[][] makeCopy(int[][] topo) {
		int[][] copy = new int[topo.length][topo[0].length];
		for (int row = 0; row < topo.length; row++) {
			for (int kol = 0; kol < topo[row].length; kol++) {
				copy[row][kol] = topo[row][kol];
			}
		}
		return copy;
	}

	private void goToTop(int[] coord, int nrHead, int[][] copyTopo, boolean part2) {
		if (isUpOneUp(coord, copyTopo)) {
			if (copyTopo[coord[0] - 1][coord[1]] == 9) {
				if (!part2) {
					copyTopo[coord[0] - 1][coord[1]] = 99;
					map.replace(nrHead, map.get(nrHead) + 1);
				} else {
					map2.replace(nrHead, map2.get(nrHead) + 1);
				}
			} else {
				goToTop(new int[] { coord[0] - 1, coord[1] }, nrHead, copyTopo, part2);
			}
		}
		if (isDownOneUp(coord, copyTopo)) {
			if (copyTopo[coord[0] + 1][coord[1]] == 9) {
				if (!part2) {
					copyTopo[coord[0] + 1][coord[1]] = 99;
					map.replace(nrHead, map.get(nrHead) + 1);
				} else {
					map2.replace(nrHead, map2.get(nrHead) + 1);
				}
			} else {
				goToTop(new int[] { coord[0] + 1, coord[1] }, nrHead, copyTopo, part2);
			}
		}
		if (isRightOneUp(coord, copyTopo)) {
			if (copyTopo[coord[0]][coord[1] + 1] == 9) {
				if (!part2) {
					copyTopo[coord[0]][coord[1] + 1] = 99;

				} else {
					map2.replace(nrHead, map2.get(nrHead) + 1);
				}
			} else {
				goToTop(new int[] { coord[0], coord[1] + 1 }, nrHead, copyTopo, part2);
			}
		}
		if (isLeftOneUp(coord, copyTopo)) {
			if (copyTopo[coord[0]][coord[1] - 1] == 9) {
				if (!part2) {
					copyTopo[coord[0]][coord[1] - 1] = 99;
					map.replace(nrHead, map.get(nrHead) + 1);
				} else {
					map2.replace(nrHead, map2.get(nrHead) + 1);
				}
			} else {
				goToTop(new int[] { coord[0], coord[1] - 1 }, nrHead, copyTopo, part2);
			}
		}

	}

	private boolean isUpOneUp(int[] coord, int[][] copyTopo) {
		if (coord[0] == 0) {
			return false;
		}
		if (copyTopo[coord[0] - 1][coord[1]] == copyTopo[coord[0]][coord[1]] + 1) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isDownOneUp(int[] coord, int[][] copyTopo) {
		if (coord[0] == copyTopo.length - 1) {
			return false;
		}
		if (copyTopo[coord[0] + 1][coord[1]] == copyTopo[coord[0]][coord[1]] + 1) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isLeftOneUp(int[] coord, int[][] copyTopo) {
		if (coord[1] == 0) {
			return false;
		}
		if (copyTopo[coord[0]][coord[1] - 1] == copyTopo[coord[0]][coord[1]] + 1) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isRightOneUp(int[] coord, int[][] copyTopo) {
		if (coord[1] == copyTopo[0].length - 1) {
			return false;
		}
		if (copyTopo[coord[0]][coord[1] + 1] == copyTopo[coord[0]][coord[1]] + 1) {
			return true;
		} else {
			return false;
		}
	}

	private void mapStartpoints() {
		int counter = 0;
		for (int row = 0; row < topo.length; row++) {
			for (int kol = 0; kol < topo[row].length; kol++) {
				if (topo[row][kol] == 0) {
					trailheads.add(new int[] { row, kol });
					map.put(counter, 0);
					map2.put(counter++, 0);
				}
			}

		}

	}

	private void readFile() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			int[] row = { 0 };
			stream.forEach(line -> {
				if (this.topo == null) {
					this.topo = new int[line.length()][line.length()];
				}
				String[] split = line.split("");
				for (int i = 0; i < split.length; i++) {
					topo[row[0]][i] = Integer.parseInt(split[i].equals(".") ? "99" : split[i]);
				}
				row[0]++;
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
