package main;

import java.io.Serializable;

import main.Map.ElementyMapy;

@SuppressWarnings("serial")
public abstract class PhysicalObject implements Serializable, Attackable {
	boolean attackWith(final float points) {
		if (points < 0)
			throw new UnknownError("Robot zostal zaatakowany ujemna sila.");
		// odejmij hp jesli sie nie obroniles
		healthPoints -= (defence < points ? points - defence : 0);
		if (Config.DEBUG)
			System.out.println("Obrywa " + this.ID + " na " + x + "," + y + " pozostaje mu " + healthPoints + "HP");
		return healthPoints < 0;
	}

	boolean attackColission(final float colissionPower) {
		if (colissionPower < 0)
			throw new UnknownError("Robot zostal zaatakowany ujemna sila.");

		healthPoints -= colissionPower;
		if (Config.DEBUG)
			System.out.println("Zderza sie " + ID + " na " + x + "," + y + " pozostaje mu " + healthPoints + "HP");

		return healthPoints < 0;
	}

	/** Uwaga! Musi byc unikalny. */
	public final String ID;

	float x;
	float y;

	double rotate = 0;

	public double getRotate() {
		return rotate;
	}

	// kąt obrotu - wartości od 0 (0st) do 4 (360st)

	/** maksymalna szybkosc kratek/sekunde */
	final float maxSpeed = 1f;
	final float maxRotateSpeed = 1f;
	/**
	 * mnoznik mazymalnej szybkosci wartosci od -100 do 100.
	 */
	int actualSpeed = 0;
	int actualRotateSpeed = 0;

	final CollisionModel collisionModel;
	float attack;
	float defence;
	float healthPoints;
	int maxHealthPoints;

	PhysicalObject(final String id, final CollisionModel model, final int attack, final int defence, final int hp) {
		ID = id;
		collisionModel = model;
		this.attack = (float) attack / (float) Config.EMULATION_ACCURACY;
		this.defence = (float) defence / (float) Config.EMULATION_ACCURACY;
		maxHealthPoints = (int) (healthPoints = hp);
	}

	final boolean intersectsWith(final float jaX, final float jaY, final PhysicalObject inny) {
		if (equals(inny))
			return false;
		return collisionModel.intersectsWith(this, jaX, jaY, inny);
	}

	/**
	 * @return Czy jednostka zginela.
	 */
	abstract void step(); // String ktory powinien byc wyswietlony w
							// konsoli jednostki.

	float getMoveVectorX() {
		return (float) Math.sin(rotate * (Math.PI / 2));
	}

	float getMoveVectorY() {
		return (float) Math.cos(rotate * (Math.PI / 2));
	}

	float getStepLength() {
		return maxSpeed * actualSpeed / (100 * Config.EMULATION_ACCURACY);
	}

	float getRotateStepLength() {
		return maxRotateSpeed * actualRotateSpeed / (100 * Config.EMULATION_ACCURACY);
	}

	@Override
	public final boolean die() {
		return healthPoints < 0;
	}

	public abstract static class CollisionModel implements Serializable {
		protected final Float sizeA, sizeB;

		private CollisionModel(final Float sizeA, final Float sizeB) {
			this.sizeA = sizeA;
			this.sizeB = sizeB;
		}

		public abstract boolean intersectsWith(final PhysicalObject ja, float jaX, float jaY,
				final PhysicalObject inny);

		public abstract float getRadius();

	}

	// TODO to powinno byc wewnatrz klasy CollisionModel ale to jakos ladnie z
	// dziedziczeniem pokombinowac
	static class Cirkle extends CollisionModel {
		public Cirkle(final float promien) {
			super(promien, null);
		}

		@Override
		public boolean intersectsWith(final PhysicalObject ja, final float jaX, final float jaY,
				final PhysicalObject inny) {
			// TODO powoduje kolizje kiedy zachaczam wycinkiem kola po stronie w
			// ktora ide a nie calym kolem

			final float distance = (float) Math.sqrt(Math.pow(jaX - inny.x, 2) + Math.pow(jaY - inny.y, 2));
			if (distance < 2 * sizeA)
				return true;
			return false;
		}

		@Override
		public float getRadius() {
			return sizeA;
		}
	}

	static class Rectangle extends CollisionModel {
		public Rectangle(final float w, final float h) {
			super(w, h);
		}

		@Override
		public boolean intersectsWith(final PhysicalObject inny, final float jaX, final float jaY,
				final PhysicalObject ja) {
			if ((Math.abs(jaX - inny.x) < sizeA) && (Math.abs(jaY - inny.y) < sizeB))
				return true;
			return false;
		}

		@Override
		public float getRadius() {
			// TODO pol przekatnej
			return Math.max(sizeA, sizeB);
		}
	}

	// PRYWATNA KLASA ABY NIE BYLO DOSTEPU DO TEGO
	final static class Collisions {
		private static transient Map map;
		private static transient Players units;
		private static transient Stats stats;

		private static final float maxRangeCheck = Config.MAP_SIZE;
		private static final float maxSingleCheckRange = 0.01f;

		private static <WHAT_WE_WANT> WHAT_WE_WANT superUniversalCollisionChecker(final Unit entity,
				final Options option, final Float power) {
			float x, y;
			float singleStep;
			int checks;

			singleStep = entity.getStepLength() > 0 && entity.getStepLength() < maxSingleCheckRange
					? entity.getStepLength() : maxSingleCheckRange;

			x = entity.x;
			y = entity.y;
			if (option == Options.ATTACK_TARGET_IN_RANGE

					|| option == Options.WHAT_IS_IN_RANGE) {
				singleStep = 2 * entity.collisionModel.getRadius();
				checks = 1;
			} else {
				singleStep = entity.getStepLength() > 0 && entity.getStepLength() < maxSingleCheckRange
						? entity.getStepLength() : maxSingleCheckRange;
				if (option == Options.NEAREST_COLLISION)
					checks = singleStep == 0 ? 0 : (int) Math.ceil(maxRangeCheck / singleStep);
				else
					checks = 1;
			}

			// if (option == Options.NEAREST_COLLISION) {
			// checks = 160;
			// singleStep = 0.1f;
			// }

			for (int i = 0; i < checks; ++i) {

				x += entity.getMoveVectorX() * singleStep;
				y += entity.getMoveVectorY() * singleStep;

				if (map.isOccupied(x, y))
					return returnWhatWeWant(entity, -1, option, power, x, y, singleStep, i,
							ReturnOptions.TILE_OCCUPIED);

				for (int a = 0; a < units.size(); ++a)
					if (units.get(a) != null && entity.intersectsWith(x, y, units.get(a)))
						return returnWhatWeWant(entity, a, option, power, x, y, singleStep, i,
								ReturnOptions.UNIT_COLLISION);

			}
			return returnWhatWeWant(null, -1, option, -1f, -1, -1, -1f, // null,
					-1, ReturnOptions.NOTHING);
		}

		@SuppressWarnings("unchecked")
		private static <WHAT_WE_WANT> WHAT_WE_WANT returnWhatWeWant(final Unit entity, final int tempUnitId,
				final Options option, final Float power, final float x, final float y, final float singleStep,
				final int i, final ReturnOptions what) {
			switch (option) {
			case WHAT_IS_IN_RANGE:
				if (what.equals(ReturnOptions.NOTHING))
					return (WHAT_WE_WANT) new Integer(0);
				if (what.equals(ReturnOptions.TILE_OCCUPIED)) {
					if (map.getStructure(x, y) == ElementyMapy.PRZESZKODA)
						return (WHAT_WE_WANT) new Integer(2);
					return (WHAT_WE_WANT) new Integer(1);
				}

				return (WHAT_WE_WANT) new Integer(3);

			case ATTACK_TARGET_IN_RANGE:

				if (what.equals(ReturnOptions.UNIT_COLLISION) && units.get(tempUnitId).attackWith(power)) {
					stats.kill(entity.ID, units.get(tempUnitId).ID);
					if (Config.DEBUG)
						System.out.println("Ginie od ran ta oto osobistosc ;(\n" + units.get(tempUnitId));
					// TODO TRUE JESLI TRAFIL NA COKOLWIEK!!!!!!!!!!
					return (WHAT_WE_WANT) new Boolean(true);
				}
				return (WHAT_WE_WANT) new Boolean(false);
			case CHECK_NEXT_MOVE_COLLISION:
				if (what.equals(ReturnOptions.NOTHING))
					return (WHAT_WE_WANT) new Boolean(false);
				if (power > 0 && entity.attackColission(power)) {
					stats.kill(entity.ID);
					if (Config.DEBUG)
						System.out.println("Ginie przez zderzenie ta oto osobistosc ;(\n" + entity);
				}
				return (WHAT_WE_WANT) new Boolean(true);
			case NEAREST_COLLISION:
				if (what.equals(ReturnOptions.NOTHING))
					return (WHAT_WE_WANT) new Float(-1);
				return (WHAT_WE_WANT) new Float((i + 1) * singleStep);
			default:
				throw new UnsupportedOperationException("Jesteś inną opcją niż się da");
			}
		}

		/**
		 * Funkcja sprawdza czy następny ruch spowoduje kolizję. Jeżeli
		 * spowoduje to jednostka w argumencie obrywa
		 * 
		 * @param entity
		 * @param power
		 *            z ktora jest niszczony
		 * @return true jesli bedzie kolizja z czymkolwiek, w przeciwnym wypadku
		 *         false.
		 */
		public static boolean checkNextMoveCollision(final Unit entity, final float power) {
			return superUniversalCollisionChecker(entity, Options.CHECK_NEXT_MOVE_COLLISION, power);
		}

		/**
		 * @param entity
		 * @return Ile kratek do najblizszej jakiejkolwiek rzeczy
		 * @return -1 jesli nic nie ma lub jest poza zasiegiem
		 */
		public static float nearestCollision(final Unit entity) {
			// TODO jakos tak za duzo wchodzi w sciane
			return superUniversalCollisionChecker(entity, Options.NEAREST_COLLISION, null);
		}

		/**
		 * Atakuje jednostke z dana sila i usuwa ja jesli zostala pokonana
		 * 
		 * @param entity
		 * @param power
		 * @return true jesli zaatakuje cokolwiek
		 */
		public static boolean attackTargetInRange(final Unit entity, final float power) {
			return superUniversalCollisionChecker(entity, Options.ATTACK_TARGET_IN_RANGE, power);
		}

		/**
		 * @param entity
		 * @return 0 nic nie ma; 1 koniec mapy; 2 przeszkoda; 3 jednostka;
		 */
		public static int whatIsInRange(final Unit entity) {
			return superUniversalCollisionChecker(entity, Options.WHAT_IS_IN_RANGE, null);
		}

		private enum ReturnOptions {
			NOTHING, TILE_OCCUPIED, UNIT_COLLISION
		}

		private enum Options {
			CHECK_NEXT_MOVE_COLLISION, NEAREST_COLLISION, ATTACK_TARGET_IN_RANGE, WHAT_IS_IN_RANGE
		}

	}

	@Override
	public int hashCode() {
		return ID.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PhysicalObject other = (PhysicalObject) obj;
		if (ID != other.ID)
			return false;

		return true;
	}
}
