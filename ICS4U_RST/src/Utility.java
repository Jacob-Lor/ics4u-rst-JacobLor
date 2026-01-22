/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Utility.java
 * This utility class lays out the fee structure of a railroad card.
 */

public class Utility extends Property {

	/**
	 * This method is responsible for initializing a Utility property (Electric Company or Water Works).
	 * @param prompt
	 * This method expects a <code>String<code> n for the name of the utility.
	 * @return This method does not return anything.
	 */
	public Utility(String n) {
		name = n;
		purchased = false;
		monopolized = false;
		inherentValue = 150;
		mortgagedValue = 75;
	}

	/**
	 * This method is responsible for calculating the rent fee based on the dice roll and monopoly status.
	 * @param prompt
	 * This method does not expect a prompt, but references the static roll value from the Bank class.
	 * @return Returns an integer representing the rent (4x roll for one utility, 10x roll for both).
	 */
	@Override
	public int getFees() {
		// If the utility is mortgaged, no rent is collected
		if (mortgaged) {
			return 0;
		}
		
		// If the owner owns both utilities, rent is 10 times the dice roll
		if (monopolized) {
			return Bank.roll * 10;
		}
		
		// Otherwise, rent is 4 times the dice roll
		return Bank.roll * 4;
	}
}