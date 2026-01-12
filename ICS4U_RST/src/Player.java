import java.util.ArrayList;

public class Player {
	
	private int cash;
	private int position;
	private boolean inPrison;
	private String userName;
	private boolean isAlive;
	private ArrayList<Property> properties = new ArrayList<Property>();
	
	public Player(String n, int c, int p, boolean a, boolean ip) {
		cash = c;
		position = p;
		inPrison = ip;
		userName = n;
		isAlive = a;
	}
	public void setCash(int amount) {
		cash = amount;
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
	//Need a mechanism for killing the player and selling all their assets.

}
