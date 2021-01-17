import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("color")
public class CellColor 
{
	@Param(0)
	private String colorStr;
	
	public CellColor(String colorStr)
	{
		this.colorStr = colorStr;
	}

	public String getColorStr() {
		return colorStr;
	}

	public void setColorStr(String colorStr) {
		this.colorStr = colorStr;
	}
}