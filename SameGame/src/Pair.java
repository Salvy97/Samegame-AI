public class Pair
{
	private int i;
	private int j;
	
	public Pair(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	
	public String toString()
	{
		return String.valueOf(i) + " " + String.valueOf(j);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
}