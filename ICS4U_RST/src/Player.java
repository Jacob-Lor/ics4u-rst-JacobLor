/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Player.java
 * This player class is responsible for handling the information of the player.
 */

import java.util.ArrayList;

public class Player {
	
	private int cash;
	private int position;
	private boolean inPrison;
	private String userName;
	private boolean isAlive;
	private ArrayList<Property> properties = new ArrayList<Property>();
	private int id;
	private static int nextId = 1; // ensures id is never 0 -- the banker!
	
	public Player(String n, int startingCash) {
	    userName = n;
	    cash = startingCash;
	    id = nextId++;
	    position = 0;
	    inPrison = false;
	    isAlive = true;
	}

	public String toString() {
		return userName + "_" + Integer.toString(id);
	}
	public void setCash(int amount) {
		cash = amount;
	}
	public String getName() {
		return userName;
	}
	public boolean removeProperty(Property p) {
		return properties.remove(p);
	}
	public boolean addProperty(Property p) {
		return properties.add(p);
	}
	public int getId() {
	    return id;
	}
	public int getCash() {
	    return cash;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int p) {
		position = p;
	}

	//Need a mechanism for killing the player and selling all their assets.

}
