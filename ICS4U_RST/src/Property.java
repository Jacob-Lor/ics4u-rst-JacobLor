
public abstract class Property {
	
	private String name;
	private int fee;
	private boolean purchased;
	private Player owner; //Changed from String to Player Object
	private int inherentValue;
	private boolean mortgaged;
	private boolean monopolized;

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
	//Needed for all subclasses of the property, like Railroad, Building and Utility, yet implemented different. Hence, abstract class fits best.
	public abstract void setFees();
	public abstract void getFees();
	

}
