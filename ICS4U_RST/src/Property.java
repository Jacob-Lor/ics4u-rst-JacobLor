/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Property.java
 * This property class lays out the typical datafields and methods associated with any property in monopoly.
 */

public abstract class Property {
	
	// Property identity and financial fields
	protected String name;
	protected int fee;
	protected boolean purchased;
	protected Player owner; 
	protected int inherentValue;
	protected int mortgagedValue;
	
	// Status flags
	protected boolean mortgaged;
	protected boolean monopolized;
	protected int monopolizedLevel;

	/**
	 * This method is the default constructor for the Property class.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return This method does not return anything.
	 */
	public Property() {
		// Default constructor
	}

	// Getters and Setters

	/**
	 * This method is responsible for retrieving the Player object who owns this property.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the Player object representing the owner.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * This method is responsible for retrieving the base purchase price of the property.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the integer inherent value.
	 */
	public int getInherentValue() {
		return inherentValue;
	}

	/**
	 * This method is responsible for setting the mortgage status of the property.
	 * @param prompt
	 * This method expects a <code>boolean<code> m representing the mortgage status.
	 * @return This method does not return anything.
	 */
	public void setMortgaged(boolean m) {
		mortgaged = m;
	}

	/**
	 * This method is responsible for setting whether the property is part of a monopoly.
	 * @param prompt
	 * This method expects a <code>boolean<code> m for the monopolized status.
	 * @return This method does not return anything.
	 */
	public void setMonopolized(boolean m) {
		monopolized = m;
	}

	/**
	 * This method is responsible for setting the display name of the property.
	 * @param prompt
	 * This method expects a <code>String<code> n for the property name.
	 * @return This method does not return anything.
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * This method is responsible for retrieving the name of the property.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the String name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is responsible for updating the development or monopoly level.
	 * @param prompt
	 * This method expects an <code>int<code> ml representing the new level.
	 * @return This method does not return anything.
	 */
	public void updateMonopolizedLevel(int ml) {
		monopolizedLevel = ml;
	}

	/**
	 * This abstract method is responsible for calculating rent fees, which varies by property type.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns the calculated integer fee.
	 */
	//Needed for all subclasses of the property, like Railroad, Building and Utility, yet implemented different. 
	public abstract int getFees();
	/**
	 * This method is for setting the owner of a property
	 * @param prompt
	 * This method expects an <code>Player<code> o representing the owner of the property.
	 * @return This method does not return anything.
	 */
	public void setOwner(Player o) {
		owner = o;
	}
	
	/**
	 * This method is for setting the state of purchase of a property
	 * @param prompt
	 * This method expects an <code>boolean<code> b representing if a property is purchased or not.
	 * @return This method does not return anything.
	 */
	public void setPurchased(boolean b) {
		purchased = b;
	}
}
