package gamers;

import java.util.Random;

import main.Pair;
import main.Unit;

@SuppressWarnings("serial")
public class WhatIamDoingHere extends Unit {

	public WhatIamDoingHere(final String id, final Pair<Double, Double> position, final double r,
			final CollisionModel model) {
		super(id, position, r, model);

	}

	Random rnd = new Random();

	@Override
	public void run() {
		this.enableStopOnWall();

		this.enableMovement();
		// System.out.println("what a");
		while (true) {
			// System.out.println("what b");
			while (this.nearestCollision() > 0.4 || this.nearestCollision() == -1) {
				// System.out.println("what c");
				this.forward();

				if (this.whatIsInRange() == 3 && this.nearestCollision() < 1) {
					this.attackInNextMove();
				}
			}
			this.stop();
			// System.out.println("what d");
			cam();
		}
	}

	private void cam() {
		// System.out.println("what aa");
		while (this.whatIsInRange() == 1 || this.whatIsInRange() == 2) {
			this.rotateBy(2);
		}
		// System.out.println("what bb");
		final double r1 = this.getRotate();
		double r2 = 0;
		rotateBy(2);
		// System.out.println("what bb2");
		if (this.whatIsInRange() == 0) {
			// System.out.println("what cc");
			while (this.whatIsInRange() == 0 || this.whatIsInRange() == 3) {
				this.rotateBy(2);
				if (this.whatIsInRange() == 3 && this.nearestCollision() < 4) {
					this.attackInNextMove();
				}
			}
			r2 = this.getRotate();
		}
		// System.out.println("what dd");
		cam();
	}

	private void scan(final double r1, final double r2) {
		// TODO Auto-generated method stub

	}

}
