/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Property.java
 * This property class lays out the typical datafields and methods associated with any property in monopoly.
 */
public abstract class Property {
	
	protected String name;
	protected int fee;
	protected boolean purchased;
	protected Player owner; //Changed from String to Player Object
	protected int inherentValue;
	protected int mortgagedValue;
	protected boolean mortgaged;
	protected boolean monopolized;
	protected int monopolizedLevel;

	public Property() {
		// TODO Auto-generated constructor stub
	}
	public Player getOwner() {
		return owner;
	}
	public int getInherentValue() {
		return inherentValue;
	}
	public void setMortgaged(boolean m) {
		mortgaged = m;
	}
	public void setMonopolized(boolean m) {
		monopolized = m;
	}
	public void setName(String n) {
		name = n;
	}
	public String getName() {
		return name;
	}
	public void updateMonopolizedLevel(int ml) {
		monopolizedLevel = ml;
	}
	//Needed for all subclasses of the property, like Railroad, Building and Utility, yet implemented different. Hence, abstract class fits best.
	public abstract int getFees();
	

}
