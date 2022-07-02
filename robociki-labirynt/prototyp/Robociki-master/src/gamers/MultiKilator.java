package gamers;

import java.util.Random;

import main.Pair;
import main.Unit;

@SuppressWarnings("serial")
public class MultiKilator extends Unit {

	public MultiKilator(final String id, final Pair<Double, Double> position, final double r,
			final CollisionModel model) {
		super(id, position, r, model);

	}

	@Override
	public void run() {
		final Random rand = new Random();
		this.enableMovement();
		this.enableStopOnWall();
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
				//
				// }
			}
		}
	}

	void atakujJesliMozesz() {
		while (this.whatIsInRange() == 3)
			this.attackInNextMove();
	}

	void sleepWakedUped(final long ms) {
		this.stop();
		final long time = System.currentTimeMillis() + ms;
		while (this.whatIsInRange() != 3 && System.currentTimeMillis() < time)
			;
	}
}
