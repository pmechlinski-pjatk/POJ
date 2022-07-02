package main;

@SuppressWarnings("serial")
public abstract class Unit extends PhysicalObject implements Runnable {

	@Override
	public String toString() {
		return "Unit [ID=" + ID + ", x=" + x + ", y=" + y + ", rotate=" + rotate + ", actualSpeed=" + actualSpeed + "]";
	}

	private boolean attackNextMove = false;

	protected Unit(final String id, final Pair<Double, Double> position, final double rotate,
			final CollisionModel model) {
		super(id, model, 50, 10, 10);
		this.x = position.getFirst().floatValue();
		this.y = position.getSecond().floatValue();
		this.rotate = rotate;
		if (Config.DEBUG)
			System.out.println(this.toString());
	}

	@Override
	public String getPlayerID() {
		return ID;
	}

	@Override
	void step() {
		if (Config.DEBUG)
			System.out.println(ID + ". x: " + x + " y: " + y + " rotate: " + rotate + " rotateTo: " + rotateTo
					+ " goTo: " + goTo + " move_vec: " + (getMoveVectorX() + "").substring(0, 3) + ","
					+ (getMoveVectorY() + "").substring(0, 3) + " hp: " + healthPoints);
		if (attackNextMove) {
			Collisions.attackTargetInRange(this, attack);
			attackNextMove = false;
		} else if (isEnabled) {
			if (tachoMode) {
				if (Math.abs(goTachoCounter - goTo) < 0.000001) {
					actualSpeed = 0;
					goStep = 0;
					tachoMode = false;
				} else if (goTachoCounter < goTo) {
					actualSpeed = speed;
					goStep = getStepLength();
					if ((goTachoCounter + goStep) > goTo)
						goStep = goTo - goTachoCounter;
				} else {
					actualSpeed = -speed;
					goStep = getStepLength();
					if ((goTachoCounter + goStep) < goTo)
						goStep = goTo - goTachoCounter;
				}
			} else
				goStep = getStepLength();

			if (tachoRotateMode) {
				// System.out.println("aaa");
				if (Math.abs(rotateTachoCounter - rotateTo) < 0.000001) {
					actualRotateSpeed = 0;
					rotateStep = 0;
					tachoRotateMode = false;
				} else if (rotateTachoCounter < rotateTo) {
					actualRotateSpeed = rotateSpeed;
					rotateStep = getRotateStepLength();
					if ((rotateTachoCounter + rotateStep) > rotateTo)
						rotateStep = rotateTo - rotateTachoCounter;
				} else {
					actualRotateSpeed = -rotateSpeed;
					rotateStep = getRotateStepLength();
					if ((rotateTachoCounter + rotateStep) < rotateTo)
						rotateStep = rotateTo - rotateTachoCounter;
				}

			} else
				rotateStep = getRotateStepLength();

			if (!stopOnWall || !Collisions.checkNextMoveCollision(this, 0) || Collisions.whatIsInRange(this) == 3) {
				if (!Collisions.checkNextMoveCollision(this, Math.abs(goStep))) {

					x += getMoveVectorX() * goStep;
					y += getMoveVectorY() * goStep;
					goTachoCounter += goStep;
				} else
					Collisions.attackTargetInRange(this, goStep);
			}
			rotate += rotateStep;
			rotate = rotate % 4;
			rotateTachoCounter += rotateStep;
		}
	}

	private boolean tachoMode;
	private boolean tachoRotateMode;
	private boolean isEnabled;
	private boolean stopOnWall;

	private int speed = 100; // w procentach w sensie 0-100
	private float goTo;
	private float goTachoCounter;

	private int rotateSpeed = 100; // w procentach w sensie 0-100
	private float rotateTo;
	private float rotateTachoCounter;

	private float goStep; // dlugosc kroku ruchu liniowego
	private float rotateStep; // dlugosc kroku obrotu

	// |y ^ 0 stopni będzie w kierunku y
	// | |
	// | --> +90 stopni będzie w kierunku x
	// |__________x
	// poniższe napisalem jako funkcje zwracajace wektor ruchu(patrzenia)
	// robota
	// w kierunkach x i y w fukcji obrotu, wartosci miedzy -1 a 1.

	/**
	 * Jeżeli napotka ścianę zatrzyma się
	 */
	public void enableStopOnWall() {
		if (!Config.WALLS_CRASH)
			stopOnWall = true;
	}

	/**
	 * Jeżeli napotka ścianę nie zatrzyma się
	 */
	public void disableStopOnWall() {
		stopOnWall = false;
	}

	/**
	 * Enables motor to run
	 */
	public void enableMovement() {
		isEnabled = true;
	}

	/**
	 * Disables motor running
	 */
	public void disableMovement() {
		isEnabled = false;
	}

	/**
	 * Causes robot move backwards until stop() is called.
	 */
	public void backward() {
		actualSpeed = -speed;
	}

	/**
	 * Causes robot move forward until stop() is called.
	 */
	public void forward() {
		actualSpeed = speed;
	}

	/**
	 * Causes robot rotate left until stopRotate() is called.
	 */
	public void rotateLeft() {
		actualRotateSpeed = -rotateSpeed;
	}

	/**
	 * Causes robot rotate right until stopRotate() is called.
	 */
	public void rotateRight() {
		actualRotateSpeed = rotateSpeed;
	}

	/**
	 * @return acceleration in degrees/second/second
	 */
	public int getActualSpeed() {
		return actualSpeed;
	}

	/**
	 * @return acceleration in degrees/second/second
	 */
	public int getActualRotateSpeed() {
		return actualRotateSpeed;
	}

	/**
	 * @return the angle that this Motor is rotating to.
	 */
	public int getGoLimit() {
		return (int) goTo;
	}

	/**
	 * @return Return the angle that this Motor is rotating to.
	 */
	public int getRotateLimit() {
		return (int) ((360 / 4) * rotateTo);
	}

	/**
	 * @return the current rotation speed.
	 */
	public int getRotationSpeed() {
		return rotateSpeed;
	}

	/**
	 * @return the current target speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @return the tachometer count.
	 */
	public int getTachoCount() {
		return (int) goTachoCounter;
	}

	/**
	 * @return the rotation tachometer count.
	 */
	public int getRotateTachoCount() {
		return (int) ((360 / 4) * rotateTachoCounter);
	}

	/**
	 * @return This method returns true if the motor is attempting to move.
	 */
	public boolean isMoving() {
		return actualSpeed != 0;
	}

	/**
	 * @return This method returns true if the motor is attempting to rotate.
	 */
	public boolean isRotating() {
		return actualRotateSpeed != 0;
	}

	/**
	 * Move by the requested number of meters.
	 * 
	 * @param units
	 */
	public void goBy(final int units) {
		goTo = goTachoCounter + units;
		tachoMode = true;
	}

	/**
	 * Rotate by the requested number of degrees.
	 * 
	 * @param deg
	 */
	public void rotateBy(final int deg) {
		rotateTo = rotateTachoCounter + (((float) (4 * deg) / 360));
		tachoRotateMode = true;
	}

	/**
	 * go to the specified target distance.
	 * 
	 * @param units
	 */
	public void goTo(final int units) {
		goTo = units;
		tachoMode = true;
	}

	/**
	 * Rotate to the target angle.
	 * 
	 * @param deg
	 */
	public void rotateTo(final int deg) {
		rotateTo = (((float) (4 * deg) / 360));
		tachoRotateMode = true;
	}

	/**
	 * sets the acceleration rate of this motor in degrees/sec/sec The default
	 * value is 6000; Smaller values will make speeding up.
	 * 
	 * @param accelerationPercentage
	 */
	public void setActualSpeed(final int accelerationPercentage) {
		if (accelerationPercentage >= 0 && accelerationPercentage <= 100)
			actualSpeed = accelerationPercentage;
	}

	/**
	 * sets the rotate acceleration rate of this motor in degrees/sec/sec The
	 * default value is 6000; Smaller values will make speeding up.
	 * 
	 * @param accelerationPercentage
	 */
	public void setActualRotateSpeed(final int accelerationPercentage) {
		if (accelerationPercentage >= 0 && accelerationPercentage <= 100)
			actualRotateSpeed = accelerationPercentage;

	}

	/**
	 * Sets desired motor speed , in degrees per second; The maximum reliably
	 * sustainable velocity is 100 x battery voltage under moderate load, such
	 * as a direct drive robot on the level.
	 * 
	 * @param speedPercentage
	 */
	public void setSpeed(final int speedPercentage) {
		if (speedPercentage >= 0 && speedPercentage <= 100)
			speed = speedPercentage;
	}

	/**
	 * Sets desired motor rotate speed , in degrees per second; The maximum
	 * reliably sustainable velocity is 100 x battery voltage under moderate
	 * load, such as a direct drive robot on the level.
	 * 
	 * @param speedPercentage
	 */
	public void setRotateSpeed(final int speedPercentage) {
		if (speedPercentage >= 0 && speedPercentage <= 100)
			rotateSpeed = speedPercentage;
	}

	/**
	 * Causes motor to stop, pretty much instantaneously.
	 */
	public void stop() {
		actualSpeed = 0;
		actualRotateSpeed = 0;
		tachoMode = false;
		tachoRotateMode = false;
	}

	/**
	 * Causes motor to stop movement, pretty much instantaneously.
	 */
	public void stopGo() {
		actualSpeed = 0;
		tachoMode = false;
	}

	/**
	 * Causes motor to stop rotation, pretty much instantaneously.
	 */
	public void stopRotate() {
		actualRotateSpeed = 0;
		tachoRotateMode = false;
	}

	/**
	 * NN
	 */
	public void resetTachoCounter() {
		goTachoCounter = 0;
		goTo = 0;
		tachoMode = false;
	}

	/**
	 * NN
	 */
	public void resetRotateTachoCounter() {
		rotateTachoCounter = 0;
		rotateTo = 0;
		tachoRotateMode = false;
	}

	/**
	 * wykorzystaj nastepny ruch na atak (zatrzymuje robota w trakcie trwania
	 * ataku)
	 */
	public void attackInNextMove() {
		attackNextMove = true;
	}

	/**
	 * @return czy w nastepnym ruchu jest zlecone atakowanie
	 */
	public boolean isAttacking() {
		return attackNextMove;
	}

	/**
	 * @param entity
	 * @return Ile kratek do najblizszej jakiejkolwiek rzeczy
	 * @return -1 jesli nic nie ma lub jest poza zasiegiem
	 */
	public float nearestCollision() {
		return PhysicalObject.Collisions.nearestCollision(this);
	}

	/**
	 * @param entity
	 * @return 0 nic nie ma; 1 koniec mapy; 2 przeszkoda; 3 jednostka;
	 */
	public int whatIsInRange() {
		return PhysicalObject.Collisions.whatIsInRange(this);
	}
}
