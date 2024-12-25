package Day11;

import java.util.List;

public class Stone {
	private long nr;
	private Boolean hasEvenNumberDigits;

	public Stone(long nr) {
		this.nr = nr;
		setHasEvenNumberDigits();
	}

	private void setHasEvenNumberDigits() {
		hasEvenNumberDigits = String.format("%d", this.nr).length() % 2 == 0;
	}

	public long getNr() {
		return nr;
	}

	public List<Stone> blink() {
		if (this.nr == 0) {
			return List.of(new Stone(1));
		}
		if (hasEvenNumberDigits) {
			String stringNr = String.format("%d", this.nr);
			String stone1 = stringNr.substring(0, stringNr.length() / 2);
			String stone2 = stringNr.substring(stringNr.length() / 2);
			// remove leading 0
			while (stone2.length()>1 && stone2.charAt(0) == '0') {
				stone2 = stone2.substring(1);
			}
			return List.of(new Stone(Long.parseLong(stone1)), new Stone(Long.parseLong(stone2)));
		} else {
			return List.of(new Stone(nr * 2024));
		}
	}

	@Override
	public String toString() {
		return "[" + nr + "] ";
	}

}
