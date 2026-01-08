
public abstract class Property {
	
	protected String name;
	protected int fee;
	protected boolean purchased;
	protected Player owner; //Changed from String to Player Object
	protected int inherentValue;
	protected boolean mortgaged;
	protected boolean monopolized;

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
	public abstract int getFees();
	

}
