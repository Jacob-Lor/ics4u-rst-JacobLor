/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Railroad.java
 * This railroad class lays out the fee structure of a railroad card.
 */
public class Railroad extends Property{

	public Railroad(String n) {
		// TODO Auto-generated constructor stub
		name = n;
	}

	@Override
	public int getFees() {
		// TODO Auto-generated method stub
		return (25 * 2^monopolizedLevel);
	}

}
