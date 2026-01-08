
public class Property {
	
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
	//As different properties have different fee structures, I left defining setting and getting fees to when I actually made them.
	

}
