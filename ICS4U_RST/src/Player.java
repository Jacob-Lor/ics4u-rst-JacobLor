import java.util.ArrayList;

public class Player {
	
	private int cash;
	private int position;
	private boolean inPrison;
	private String userName;
	private boolean isAlive;
	private ArrayList<Property> properties = new ArrayList<Property>();
	private int id;
	
	public Player(String n, int i) {
		userName = n;
		id = i;
	}
	
	public Player(String n, int i, int c, int p, boolean a, boolean ip) {
		cash = c;
		position = p;
		inPrison = ip;
		userName = n;
		isAlive = a;
		id = i;
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
	//Need a mechanism for killing the player and selling all their assets.

}
