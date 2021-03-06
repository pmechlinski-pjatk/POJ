
interface Rysowalny {
	public void draw();

	@Override
	public String toString();

}

interface Skalowalny extends Rysowalny{
	public void draw(int i);
}

interface PrzedstawiaEmocje extends Skalowalny {
    public boolean czySieUsmiecha();
	public void przestanSieUsmiechac();
}

class Buzka implements PrzedstawiaEmocje {

	String emoji;

	public void draw() {
		System.out.println(emoji);
	}

	public Buzka() {
		this.emoji = ":-)";
	}

	Buzka (char wyraz) {
		this.emoji = ":-" + wyraz;
	}

	public boolean czySieUsmiecha(){
		if (this.emoji.contains(")") || this.emoji.contains("D") || this.emoji.contains(">")) {
			return true;
		} else return false;
	}

	public void przestanSieUsmiechac() {
		this.emoji = ":-(";
	}

	@Override
	public String toString() {
		return this.emoji;
	}

	public void draw(int i) {
		String verySmiling = this.emoji;
		while (i > 1) {
		verySmiling += ")";
		i--;
		}
		System.out.println(verySmiling);
	}


}

class Usmiech extends Buzka {

	String emoji;

	public Usmiech() {
		this.emoji = ":-)";
	}

	public Usmiech(char wyraz) {
		super(wyraz);
	}

	public void draw() {
		System.out.println(emoji);
	}

	public void draw(int i) {
		String verySmiling = this.emoji;
		while (i > 1) {
		verySmiling += ")";
		i--;
		}
		System.out.println(verySmiling);
	}

	public boolean czySieUsmiecha(){
		return true;
	}

	@Override
	public String toString() {
		return this.emoji;
	}

	public void przestanSieUsmiechac() {
	}
}


public class Kolo4 {



	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// DONE Nie zmieniając main spraw aby program działał
		// DONE Stwórz klasy Buzka, Usmiech
		// DONE Stwórz interfejsy Ryswalny, Skalowalny, PrzedstawiaEmocje

		// ? zadbaj o jakość kodu
		// +/-? nie dubluj niepotrzebnie kodu, nie pisz niepotrzebnego kodu
		// DONE staraj się ogólną funkcjonalność umieścić jak najwyżej w drzewie
		// dziedziczenia


		Usmiech u1 = new Usmiech();
		Buzka u2 = new Buzka('(');

		Buzka t1 = new Usmiech();
		PrzedstawiaEmocje t2 = new Buzka('(');
		Rysowalny t3 = new Buzka('(');
		Skalowalny t4 = new Usmiech();

		Rysowalny u3 = new Usmiech();
		Rysowalny u4 = (Rysowalny) new Skalowalny() {

			@Override
			public void draw() {
				System.out.println(toString());
			}

			@Override
			public void draw(int times) {
				System.out.print(toString());
				if (times > 1)
					System.out.print(")".repeat(times - 1));
				System.out.println();
			}

			@Override
			public String toString() {
				return ":-)";
			}
		};
		PrzedstawiaEmocje u5 = new Buzka(')');

		System.out.println("Wydrukuj :-)");
		u4.draw(); // drukuje :-)
		System.out.println("Wydrukuj :-)))");
		((Skalowalny) u4).draw(3); // drukuje :-)))
		System.out.println("Wydrukuj :-(");
		u2.draw(); // drukuje :-(
		System.out.println("Wydrukuj :-)))");
		u1.draw(3); // drukuje :-)))
		System.out.println("Wydrukuj :-(");
		((Rysowalny) ((PrzedstawiaEmocje) u2)).draw(); // drukuje :-(
		System.out.println("Wydrukuj :-)");
		((Rysowalny) ((PrzedstawiaEmocje) ((Buzka) u1))).draw(); // drukuje :-)

		if (("" + u1).equals(":-)"))
			System.out.println("OK 1");
		if (("" + u2).equals(":-("))
			System.out.println("OK 2");

		if (u1.toString().equals(u4.toString()))
			System.out.println("OK 3");
		if (u1.toString().equals(":-)"))
			System.out.println("OK 4");
		if (u5.toString().equals(":-)"))
			System.out.println("OK 5");

		if (u5.czySieUsmiecha())
			System.out.println("OK 6");
		if (u1.czySieUsmiecha())
			System.out.println("OK 7");
		if (!u2.czySieUsmiecha())
			System.out.println("OK 8");

		u5.przestanSieUsmiechac();
		if (!u5.czySieUsmiecha())
			System.out.println("OK 9");
		u1.przestanSieUsmiechac();
		if (u1.czySieUsmiecha())
			System.out.println("OK 10");
		
		if (u1.equals(u3))
			System.out.println("OK 11");
		if (u2.equals(u5))
			System.out.println("OK 12");
		if (!u1.equals(u5))
			System.out.println("OK 13");
		if (!u2.equals(u3))
			System.out.println("OK 14");
	}

}