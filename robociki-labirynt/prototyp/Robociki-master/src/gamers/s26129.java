
package gamers;

import main.Pair;
import main.Unit;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class s26129 extends Unit {
	public s26129(String id, Pair<Double, Double> position, double r, CollisionModel model) {
		super(id, position, r, model);
	}
/* Gracz zwany jest "Wyjścioszukaczem".
Wykorzystuje implementację standardowego algorytmu typu Wall Follower,
 z (prawdopodobnie) działającą poprawką Pledge'a - robocik nie grzęźnie w G-kształtnych pułapkach, tylko podąża
 za ścianą.
 */

	// Zmienne globalne są trzymane w pamięci w celu poprawy sterowania robocikiem.
public int angle = 0;
// Zmienna kątu zapewnia zmianę kierunku obrotu co 360 stopni.
public int direction = 1;
// Zmienna kierunku obrotu o wartościach {-1,1}
public double frustration = 1.0;
// Zmienna frustracji jest wykorzystywana do poprawy wychodzenia robocika z klinczu przy ścianie.

	public void run() {
// Blok startowy
		this.enableStopOnWall();
		this.enableMovement();
		this.forward();
		while(true) {
			// Pętle konsoli do testowania parametrów
//				System.out.println(this.nearestCollision());
//				System.out.println("Angle: " + angle);
//				System.out.println("Direction: " + direction);

			// Warunek zmienia kierunek obrotu co 360 stopni w celu uniknięcia kręcenia się w kółko.
			if (angle >= 36) {
				direction *= -1;
				angle = 0;
			}

			// Jeżeli robocik jest zbyt blisko ściany, spróbuje znaleźć drogę w bezpiecznej odległości.
			if (this.nearestCollision() > 0.5 && this.nearestCollision() < 0.6) {
				try {
					int dirStart = direction;
					this.changeDirection(direction);
					direction = dirStart;
					System.out.println("Change direction!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// Jeżeli robocik jest bardzo blisko ściany, spróbuje wycofać i skorygować kurs.
			if (this.nearestCollision() <= 0.5) {
				try {
					this.goBackRotate(direction);
					System.out.println("Go back n' rotate!");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			// Jeżeli robocik będzie zbyt daleko ściany, spróbuje znaleźć się z powrotem blisko.
			if (this.nearestCollision() >= 2 && this.nearestCollision() <= 2.2) {
				System.out.println("I must go back near wall!");
				this.rotateBy(60*(-1*direction));
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				angle -= 6;
			}

		}
	}
		// Metoda na cofanie.
		public void goBackRotate(int d) throws InterruptedException {
		this.backward();
		TimeUnit.MILLISECONDS.sleep((long) (200*frustration));
		// Pseudolosowy współczynnik frustracji pomaga wybrnąć szczególnie z mocnego klinczu.
		this.rotateBy(30*direction);
		angle += 3;
		frustration += 0.1;
		if (frustration > 2) frustration = 1;
		this.forward();
		}

		public void changeDirection(int d) throws InterruptedException {
		while(true) {
			this.rotateBy(30*d);
			angle += 3;
			double startVal = this.nearestCollision();
			this.forward();
			TimeUnit.MILLISECONDS.sleep(100);
			double endVal = this.nearestCollision();
			if (endVal == startVal) break;
			// Dzięki temu ^ warunkowi robocik powinien częściej łapać pozycje równoległe do ściany
			if (endVal >= startVal) direction *= -1; break;
			// Zmienny współczynnik kierunku pomaga w efektywnym cofaniu, ale działa tylko w kontekście tej metody.
		}
		}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
	}




