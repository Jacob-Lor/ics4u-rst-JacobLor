/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Building.java
 * This building class is responsible for handling the information for the various building style properties of monopoly.
 */
import java.util.ArrayList;

import java.util.ArrayList;

public class Building extends Property {

	public ArrayList<Integer> rents;
	protected int housePrice;
	protected int hotelPrice;
	protected int numberOfHouses;   // 0–4 houses, 5 = hotel
	protected String colour;

	/**
	 * This method is responsible for initializing a new Building object with specific game values.
	 * @param prompt
	 * This method expects strings for name/color, a Player owner, an ArrayList of rents, and integers for prices/values.
	 * @return This method does not return anything.
	 */
	public Building(String n, String c, Player o, ArrayList<Integer> rs,
	                int hP, int htP, int iV, int mV) {

		name = n;
		colour = c;
		owner = o;
		rents = rs;

		housePrice = hP;
		hotelPrice = htP;
		mortgagedValue = mV;
		inherentValue = iV;

		// Default states
		numberOfHouses = 0;
		mortgaged = false;
		purchased = false;
		monopolized = false;
	}

	// Getters

	/**
	 * This method is responsible for retrieving the current number of houses on the property.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the integer number of houses (5 indicates a hotel).
	 */
	public int getHouses() {
		return numberOfHouses;
	}

	/**
	 * This method is responsible for retrieving the colour group of the building.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the string representation of the colour.
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * This method is responsible for checking if a house can be built based on monopoly and mortgage status.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns true if conditions allow building, false otherwise.
	 */
	public boolean canBuildHouse() {
		return monopolized && !mortgaged && numberOfHouses < 5;
	}

	/**
	 * This method is responsible for incrementing the house count if building is permitted.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return This method does not return anything.
	 */
	public void addHouse() {
		if (canBuildHouse()) {
			numberOfHouses++;
		}
	}

	/**
	 * This method is responsible for decrementing the house count.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return This method does not return anything.
	 */
	public void removeHouse() {
		if (numberOfHouses > 0) {
			numberOfHouses--;
		}
	}


	/**
	 * This method is responsible for calculating the rent fee based on mortgage status, monopoly status, and house count.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the calculated integer rent value.
	 */
	@Override
	public int getFees() {
		// If mortgaged, no rent is collected
		if (mortgaged) {
			return 0;
		}

		// Monopoly + no houses → double base rent
		if (monopolized && numberOfHouses == 0) {
			return rents.get(0) * 2;
		}

		// Houses (0–4) or hotel (5)
		return rents.get(Math.min(numberOfHouses, rents.size() - 1));
	}

	/**
	 * This method is responsible for creating a string representation of the building's data for saving or display.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns a CSV-style String of the building's attributes.
	 */
	public String toString() {
		return name + "," + colour + "," + owner.getName() + "," +
		       numberOfHouses + "," + housePrice + "," +
		       inherentValue + "," + mortgagedValue + "," +
		       purchased + "," + mortgaged + "," + monopolized;
	}

	/**
	 * This method is responsible for retrieving the sell value of a house.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the full integer price of the house.
	 */
	//Allows houses to be sold
	public int getHouseSellValue() {
	    // Returns full house value (not half) as requested
	    return housePrice;
	}
	public void resetHouses() {
		numberOfHouses = 0;
	}
}

