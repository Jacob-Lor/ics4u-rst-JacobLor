import java.util.ArrayList;

public class Building extends Property{
	public ArrayList<Integer> paymentPlan = new ArrayList<Integer>(); //To record prices number of hotels 
	int housePrice;
	int hotelPrice;
	int numberOfHouses;
	String colour;
	
	public Building(String n, String c, ArrayList<Integer> pP, int hP, int htP, int mV, int iV){
		// TODO Auto-generated constructor stub
		name = n;
		colour = c;
		owner = null;
		paymentPlan = pP;
		housePrice = hP;
		hotelPrice = htP;
		inherentValue = iV;
		mortgagedValue = mV;
		mortgaged = false;
	}

	@Override
	public int getFees() {
		return fee;
		// TODO Auto-generated method stub
		
	}

}
