package main;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pair<K, V> implements Serializable {

	private final K element0;
	private final V element1;

	public static <K, V> Pair<K, V> createPair(final K element0,
			final V element1) {
		return new Pair<K, V>(element0, element1);
	}

	public Pair(final K element0, final V element1) {
		this.element0 = element0;
		this.element1 = element1;
	}

	public K getFirst() {
		return element0;
	}

	public V getSecond() {
		return element1;
	}

	@Override
	public String toString() {
		return element0.toString() + " && " + element1.toString();
	}

}