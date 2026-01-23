/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Railroad.java
 * This railroad class lays out the fee structure of a railroad card.
 */

public class Railroad extends Property {

	/**
	 * This method is responsible for initializing a Railroad property with default Monopoly values.
	 * @param prompt
	 * This method expects a <code>String<code> n representing the name of the railroad.
	 * @return This method does not return anything.
	 */
	public Railroad(String n, Player o) {
		name = n;
		owner = o;
		fee = 25; // Base rent for one railroad
		purchased = false;
		inherentValue = 200;
		mortgagedValue = 100;
		monopolizedLevel = 0; // Represents how many railroads the owner has (0-3 additional)
	}

	/**
	 * This method is responsible for calculating the rent fee based on how many railroads are owned.
	 * @param prompt
	 * This method does not expect a prompt.
	 * @return Returns an integer representing the rent, doubling for each additional railroad owned.
	 */
	public int getFees() {
		// If the railroad is mortgaged, no rent is collected
		if (mortgaged) {
			return 0;
		}
		
		// Rent calculation logic: $25 for 1, $50 for 2, $100 for 3, $200 for 4
		// Uses monopolizedLevel as the exponent to double the base fee
		return 25 * (int)Math.pow(2, monopolizedLevel);
	}
}