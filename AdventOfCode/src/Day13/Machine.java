package Day13;

public class Machine {
	private Button buttonA, buttonB;
	private Location clawLocation;
	private Location priceLocation;
	private boolean isPricePossible, isPricePossible2, diff;
	private long aPushes, bPushes, aPushes2, bPushes2;

	public Machine(Button buttonA, Button buttonB, Location priceLocation) {
		clawLocation = new Location(0, 0);
		this.buttonA = buttonA;
		this.buttonB = buttonB;
		this.priceLocation = priceLocation;
		setIsPricePossible();
		setIsPricePossible2();
	}

	private void setIsPricePossible() {
		long a = 0;

		for (long i = 0; i < 100; i++) {
			long b = (priceLocation.getX() - a * buttonA.getX()) / buttonB.getX();
			for (long y = 0; y < 100; y++) {
				long posX = a * buttonA.getX() + b * buttonB.getX();
				long posY = a * buttonA.getY() + b * buttonB.getY();
				if (posX == priceLocation.getX() && posY == priceLocation.getY()) {
					isPricePossible = true;
					aPushes = a;
					bPushes = b;
					break;
				}
				b++;
				if (b * buttonB.getX() > priceLocation.getX() || b * buttonB.getY() > priceLocation.getY()) {
					break;
				}

			}
			a++;
			if (a * buttonA.getX() > priceLocation.getX() || a * buttonA.getY() > priceLocation.getY()) {
				break;
			}
		}

	}

	private void setIsPricePossible2() {
		long y = priceLocation.getY();
		long x = priceLocation.getX();
		long xa = buttonA.getX();
		long xb = buttonB.getX();
		long ya = buttonA.getY();
		long yb = buttonB.getY();
		long mod, mod2;
		if (xa * yb - xb * ya != 0 && xa != 0) {
			isPricePossible2 = true;
			bPushes2 = (xa * y - x * ya) / (xa * yb - xb * ya);
			mod = (xa * y - x * ya) % (xa * yb - xb * ya);
			aPushes2 = (x - bPushes2 * xb) / xa;
			mod2 = (x - bPushes2 * xb) % xa;

			if (mod != 0 || mod2 != 0) {
				isPricePossible2 = false;
				bPushes2 = 0;
				aPushes2 = 0;
			}
		}

	}

	public Location getPriceLocation() {
		return priceLocation;
	}

	public void setPriceLocation(Location priceLocation) {
		this.priceLocation = priceLocation;
	}

	public long getaPushes() {
		return aPushes;
	}

	public long getbPushes() {
		return bPushes;
	}

	public long getaPushes2() {
		return aPushes2;
	}

	public long getbPushes2() {
		return bPushes2;
	}

	public long getTokenCost() {
		return aPushes * 3 + bPushes;
	}

	public long getTokenCost2() {
		return aPushes2 * 3 + bPushes2;
	}

	public boolean isPricePossible() {
		return isPricePossible;
	}

	public boolean isPricePossible2() {
		return isPricePossible2;
	}

	public Location getClawLocation() {
		return clawLocation;
	}

	public void pushButtonA() {
		clawLocation.move(buttonA.getX(), buttonA.getY());
	}

	public void pushButtonB() {
		clawLocation.move(buttonB.getX(), buttonB.getY());
	}

	@Override
	public String toString() {
		return "Machine\nbuttonA=" + buttonA + ", buttonB=" + buttonB + "\npriceLocation=" + priceLocation
				+ "\nclawLocation=" + clawLocation + "\n";
	}
}
