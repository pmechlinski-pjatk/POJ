package gamers;

import main.Config;
import main.Pair;
import main.Unit;

@SuppressWarnings("serial")
public class ChlebKrojony extends Unit {

	public ChlebKrojony(final String id, final Pair<Double, Double> position, final double r,
			final CollisionModel model) {
		super(id, position, r, model);

	}

	@Override
	public void run() {
		this.enableStopOnWall();
		this.forward();
		this.enableMovement();

		for (;;) {

			try {
				Thread.sleep((long) (100d / Config.EMULATION_SPEED));
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			final int randAngle = -180 + (int) (Math.random() * 360);
			if (this.nearestCollision() <= 2 && nearestCollision() >= 0) {
				if (this.whatIsInRange() == 3) {
					for (int i = 0; i < 1500; i++) {
						try {
							Thread.sleep(1);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
						this.attackInNextMove();
					}
				} else {
					rotateBy(180);
				}
			} else {
				rotateBy(randAngle);
			}
			// System.out.println(this.nearestCollision());
		}
	}
}
