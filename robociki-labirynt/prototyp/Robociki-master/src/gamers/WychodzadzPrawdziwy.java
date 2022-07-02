package gamers;

import java.util.Random;

import main.Pair;
import main.Unit;

@SuppressWarnings("serial")
public class WychodzadzPrawdziwy extends Unit {

	public WychodzadzPrawdziwy(final String id, final Pair<Double, Double> position, final double r,
			final CollisionModel model) {
		super(id, position, r, model);
	}

	@Override
	public void run() {
		final Random rand = new Random();

		this.enableMovement();
		// this.enableStopOnWall();

		if (!rotateToNearrest())
			jedzPoSpiralachLosowo();
		this.resetRotateTachoCounter();
		goToHalf();
		this.rotateByAndWait(90);
		goToHalf();

		centrowaniePrzudPrawo();

		this.resetTachoCounter();
		while (true) {
			// System.out.println(((System.currentTimeMillis() / 1000) + "
			// ").substring(7, 12) + ": dupa");
			if (kolizjaKratek() > 0) {

				// System.out.println(
				// ((System.currentTimeMillis() / 1000) + " ").substring(7, 12)
				// + ": true");
				goByAndWait(1);

			} else {
				// System.out.println(
				// ((System.currentTimeMillis() / 1000) + " ").substring(7, 12)
				// + ": false");
				this.rotateByAndWait(90);
				// System.out.print("a");
				if (kolizjaKratek() <= 0) {
					// System.out.print("b");
					this.rotateByAndWait(90);
					// System.out.print("c");
					if (kolizjaKratek() <= 0) {
						// System.out.print("d");
						centrowaniePrzudPrawo();
						// System.out.println("e");
					}
				}
			}
			if (rand.nextBoolean() && rand.nextBoolean())
				if (rand.nextBoolean())
					this.rotateByAndWait(90);
				else
					this.rotateByAndWait(-90);
		}

	}

	private void centrowaniePrzudPrawo() {
		rotateToNearrest();
		// this.resetRotateTachoCounter();
		goToHalf();
		this.rotateByAndWait(90);
		goToHalf();
	}

	private void jedzPoSpiralachLosowo() {
		final Random rand = new Random();
		while (true) {
			int obrocik = 180;
			double dzielnik;
			while (this.whatIsInRange() == 0) {
				if (rand.nextBoolean())
					obrocik = -obrocik;
				if (rand.nextBoolean()) {
					dzielnik = 2;
					this.setRotateSpeed(rand.nextInt(50) + 1);
				} else {
					this.setRotateSpeed(rand.nextInt(50) + 51);
					dzielnik = 0.5;
				}

				for (int i = 0; i < 6; ++i) {

					this.rotateBy(obrocik);
					while (!this.isRotating() && this.whatIsInRange() == 0)
						;

					this.forward();

					while (this.isRotating() && this.whatIsInRange() == 0)
						;
					this.stop();
					if (this.getRotationSpeed() == 100)
						break;
					this.setRotateSpeed((int) (this.getRotationSpeed() * dzielnik));
				}

				this.setRotateSpeed(100);

			}
			atakujJesliMozesz();

			if (rand.nextBoolean())
				this.rotateLeft();
			else
				this.rotateRight();
			while (this.whatIsInRange() != 0) {
				atakujJesliMozesz();
				// try {
				// Thread.sleep(10);
				// } catch (InterruptedException e) {
				// }
			}
		}
	}

	private int kolizjaKratek() {
		final float aaa = this.nearestCollision();
		if (aaa < 0)
			return 999;
		return (int) (aaa - .25);
	}

	private void goByAndWait(final float a) {
		final long time = System.currentTimeMillis();
		this.goBy(przybliz(a));
		while (!this.isMoving() && System.currentTimeMillis() - time < a * 1000) {
			sleep(1);
		}
		while (this.isMoving() && System.currentTimeMillis() - time < a * 1000) {
			sleep(1);
		}
		stopGo();
	}

	private void goToHalf() {
		float dfsd = this.nearestCollision();
		if (dfsd < 0)
			return;
		final int aaaa = this.getSpeed();
		this.setSpeed(10);
		if (dfsd - (int) dfsd >= .5) {
			this.forward();
			while ((dfsd = this.nearestCollision()) - (int) dfsd > .5) {
				// System.out.println(dfsd);
				sleep(1);
			}
		} else {
			this.backward();
			while ((dfsd = this.nearestCollision()) - (int) dfsd < .5) {
				// System.out.println(dfsd);
				sleep(1);
			}
		}
		stop();
		// System.out.println("final " + this.nearestCollision());
		this.setSpeed(aaaa);
	}

	private boolean rotateToNearrest() {
		this.resetRotateTachoCounter();

		final int tmp = this.getRotationSpeed();
		this.setRotateSpeed(10);
		// int tacho = this.getRotateTachoCount();
		float aktualnaNajmniejsza = 99999999;
		float aktualnaNajmniejsza_wartosc = -1;
		float aaa;
		do {
			rotateByAndWait(1);
			// System.out.println(this.nearestCollision());
			if ((aaa = this.nearestCollision()) >= 0 && aaa < aktualnaNajmniejsza) {
				aktualnaNajmniejsza_wartosc = this.getRotateTachoCount();
				aktualnaNajmniejsza = aaa;
				// System.out.println(aktualnaNajmniejsza_wartosc +
				// " odleglosc "
				// + aaa);
			}
			// System.out.println(this.getRotateTachoCount() + " " + aaa);
		} while (this.getRotateTachoCount() < 360);
		stop();
		// System.out.println(aktualnaNajmniejsza_wartosc + "="
		// + przybliz(aktualnaNajmniejsza_wartosc));

		if (aktualnaNajmniejsza_wartosc >= 0) {
			rotateToAndWait(aktualnaNajmniejsza_wartosc);
			this.setRotateSpeed(tmp);
			return true;
		}
		this.setRotateSpeed(tmp);
		return false;
	}

	private void rotateToAndWait(final float a) {
		final long time = System.currentTimeMillis();
		this.rotateTo(przybliz(a));
		while (!this.isRotating() && System.currentTimeMillis() - time < a * 1000) {
			sleep(1);
		}
		while (this.isRotating() && System.currentTimeMillis() - time < a * 1000) {
			sleep(1);
		}
		stopRotate();
	}

	private void rotateByAndWait(final int deg) {
		final long time = System.currentTimeMillis();
		this.rotateBy(deg);
		while (!this.isRotating() && System.currentTimeMillis() - time < deg * 100) {
			sleep(1);
		}
		while (this.isRotating() && System.currentTimeMillis() - time < deg * 100) {
			sleep(1);
		}
		stop();
	}

	private int przybliz(final float f) {
		return (int) (f + .5);
	}

	void atakujJesliMozesz() {
		while (this.whatIsInRange() == 3)
			this.attackInNextMove();
	}

	private void sleep(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (final InterruptedException e) {
		}
	}

	// void sleepWakedUped(final long ms) {
	// this.stop();
	// final long time = System.currentTimeMillis() + ms;
	// while (this.whatIsInRange() != 3 && System.currentTimeMillis() < time)
	// ;
	// }
}
