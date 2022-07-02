package main;

public interface Attackable {

	/**
	 * @return true jesli zmarla
	 */
	abstract boolean die();

	/**
	 * @return ID wlasciciela obiektu
	 */
	public abstract String getPlayerID();

}