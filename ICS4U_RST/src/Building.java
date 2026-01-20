/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Building.java
 * This building class is responsible for handling the information for the various building style properties of monopoly.
 */
import java.util.ArrayList;

public class Building extends Property{
	public ArrayList<Integer> rents = new ArrayList<Integer>(); //To record prices number of hotels 
	int housePrice;
	int hotelPrice;
	int numberOfHouses;
	String colour;
	
	public Building(String n, String c, Player o, ArrayList<Integer> rs, int hP, int htP, int mV, int iV){
		// TODO Auto-generated constructor stub
		name = n;
		colour = c;
		owner = o;
		rents = rs;
		housePrice = hP;
		hotelPrice = htP;
		inherentValue = iV;
		mortgagedValue = mV;
		mortgaged = false;
	}
	public String toString() {
		return name + "," + colour + "," + owner.toString() + "," + Integer.toString(fee) + "," + Integer.toString(housePrice) + "," + Integer.toString(inherentValue) + "," + Integer.toString(mortgagedValue) + "," + Boolean.toString(purchased) + "," +Boolean.toString(mortgaged) +","+ Boolean.toString(monopolized);
	}

	@Override
	public int getFees() {
		return fee;
		// TODO Auto-generated method stub
		
	}

}
