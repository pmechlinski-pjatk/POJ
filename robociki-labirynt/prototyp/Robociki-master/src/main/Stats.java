package main;

import java.util.HashMap;

public class Stats {
	private final HashMap<String, Integer> sum = new HashMap<>();

	void kill(final String killer, final String killed) {
		sum.merge(killer, 1, (oldValue, one) -> oldValue + one);
	}

	void kill(final String killedMyself) {
		sum.merge(killedMyself, -1, (oldValue, one) -> oldValue + one);
	}

	@Override
	public String toString() {
		return sum.toString();
	}
}
