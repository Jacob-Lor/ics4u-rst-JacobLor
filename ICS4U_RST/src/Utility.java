/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Utility.java
 * This utility class lays out the fee structure of a railroad card.
 */
public class Utility extends Property{

	public Utility(String n) {
		// TODO Auto-generated constructor stub
		name = n;
	}

	@Override
	public int getFees() {
		// TODO Auto-generated method stub
		if (monopolized) {
			return Bank.roll*10;
		}
		return  Bank.roll * 4;
	}

}
