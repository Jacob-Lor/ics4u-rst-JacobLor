/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Player.java
 * This player class is responsible for handling the information of the player.
 */

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player {

	// Account and status fields
	private int cash;
	private int position;
	private boolean inPrison;
	private String userName;
	private boolean isAlive;

	// Property management
	private ArrayList<Property> properties = new ArrayList<Property>();

	// Identification
	private int id;
	private static int nextId = 1;

	// GUI Component
	private Circle token;

	/**
	 * This method is responsible for initializing a basic Player object primarily for identification.
	 * @param prompt
	 * This method expects a <code>String<code> n for the name and an <code>int<code> i for the ID.
	 * @return This method does not return anything.
	 */
	public Player(String n, int i) {
		userName = n;
		id = i;
	}

	/**
	 * This method is responsible for initializing a Player with full game stats and a visual token.
	 * @param prompt
	 * This method expects a name, starting cash amount, and a JavaFX Color for the token.
	 * @return This method does not return anything.
	 */
	public Player(String n, int startingCash, Color color) {
		userName = n;
		cash = startingCash;
		id = nextId++;
		position = 0;
		inPrison = false;
		isAlive = true;

		// Setup visual representation for the board
		token = new Circle(10);
		token.setFill(color);
		token.setStroke(Color.BLACK);
	}

	/**
	 * This method is responsible for returning a string representation of the player for debugging or lists.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns a string combining the username and unique ID.
	 */
	@Override
	public String toString() {
		return userName + "_" + id;
	}

	// Getters and Setters

	/**
	 * This method is responsible for retrieving the player's username.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the String userName.
	 */
	public String getName() {
		return userName;
	}

	/**
	 * This method is responsible for retrieving the unique player ID.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the integer id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method is responsible for retrieving the player's current cash balance.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the integer cash amount.
	 */
	public int getCash() {
		return cash;
	}

	/**
	 * This method is responsible for updating the player's cash balance.
	 * @param prompt
	 * This method expects an <code>int<code> amount to set as the new balance.
	 * @return This method does not return anything.
	 */
	public void setCash(int amount) {
		cash = amount;
	}

	/**
	 * This method is responsible for retrieving the player's current board position.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the integer position (0-39).
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * This method is responsible for updating the player's board position.
	 * @param prompt
	 * This method expects an <code>int<code> p representing the new tile index.
	 * @return This method does not return anything.
	 */
	public void setPosition(int p) {
		position = p;
	}

	/**
	 * This method is responsible for checking if the player is still active in the game.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns a boolean representing the life status.
	 */
	public boolean getIsAlive() {
		return isAlive;
	}

	/**
	 * This method is responsible for setting the player's life status.
	 * @param prompt
	 * This method expects a <code>boolean<code> alive status.
	 * @return This method does not return anything.
	 */
	public void setIsAlive(boolean alive) {
		isAlive = alive;
	}

	/**
	 * This method is responsible for adding a property to the player's portfolio.
	 * @param prompt
	 * This method expects a <code>Property<code> p to be added.
	 * @return Returns true if the property was added successfully.
	 */
	public boolean addProperty(Property p) {
		return properties.add(p);
	}

	/**
	 * This method is responsible for removing a property from the player's portfolio.
	 * @param prompt
	 * This method expects a <code>Property<code> p to be removed.
	 * @return Returns true if the property was removed successfully.
	 */
	public boolean removeProperty(Property p) {
		return properties.remove(p);
	}

	/**
	 * This method is responsible for retrieving the list of all properties owned by the player.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns an ArrayList of Property objects.
	 */
	public ArrayList<Property> getProperties() {
		return properties;
	}

	/**
	 * This method is responsible for retrieving the player's visual token for JavaFX rendering.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the Circle object used as the game piece.
	 */
	public Circle getToken() {
		return token;
	}
}
