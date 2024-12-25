package Day7;

import java.util.Arrays;

public class Equation {
	private long result;
	private long[] values;
	private boolean isSolvablePart1;
	private boolean isSolvablePart2;

	public Equation(long result, long[] values) {
		this.result = result;
		this.values = values;
		setIsSolvablePart1();
		setIsSolvablePart2();
	}

	private void setIsSolvablePart1() {
		recursionPart1(values[0], 0);
	}

	private void setIsSolvablePart2() {
		recursionPart2(values[0], 0);
	}

	private void recursionPart1(long res, int index) {
		// end of calculation
		if (index == values.length - 1) {
			if (res == this.result) {
				isSolvablePart1 = true;
			}
			return;
		}
		index++;
		recursionPart1(Math.addExact(res, values[index]), index);
		recursionPart1(Math.multiplyExact(res, values[index]), index);
	}

	private void recursionPart2(long res, int index) {
		// end of calculation
		if (index >= values.length - 1) {
			if (res == this.result) {
				isSolvablePart2 = true;
			}
			return;
		}
		index++;
		recursionPart2(Math.addExact(res, values[index]), index);
		recursionPart2(Math.multiplyExact(res, values[index]), index);
		recursionPart2(Long.parseLong(String.format("%d%d", res, values[index])), index);
	}

	public boolean isSolvablePart1() {
		return isSolvablePart1;
	}

	public boolean isSolvablePart2() {
		return isSolvablePart2;
	}

	public long getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "result=" + result + " values=" + Arrays.toString(values) + " " + isSolvablePart1 + " "
				+ isSolvablePart2;
	}

}
