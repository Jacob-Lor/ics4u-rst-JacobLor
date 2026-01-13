import java.util.ArrayList;

public class Building extends Property{
	public ArrayList<Integer> paymentPlan = new ArrayList<Integer>(); //To record prices number of hotels 
	int housePrice;
	int hotelPrice;
	int numberOfHouses;
	
	public Building(String n, ArrayList<Integer> pP, int hP, int htP, int iV){
		// TODO Auto-generated constructor stub
		name = n;
		owner = null;
		paymentPlan = pP;
		housePrice = hP;
		hotelPrice = htP;
		inherentValue = iV;
		mortgaged = false;
	}

	@Override
	public int getFees() {
		return fee;
		// TODO Auto-generated method stub
		
	}

}
