import java.util.ArrayList;

public class Player {
	
	private int cash;
	private int position;
	private boolean inPrison;
	private String userName;
	private boolean isAlive;
	private ArrayList<Property> properties = new ArrayList<Property>();
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	public void setCash(int amount) {
		cash -= amount;
	}
	public void setName(String name) {
		userName = name;
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

}
