import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("blockSize")
public class BlockSize
{
	@Param(0)
	private int i;
	@Param(1)
	private int j;
	@Param(2)
	private int size;
	
	public BlockSize( int i, int j, int size)
	{
		this.i = i;
		this.j = j;
		this.size = size;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}