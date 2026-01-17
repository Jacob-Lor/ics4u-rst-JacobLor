
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
