import java.awt.Color;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell")
public class Cell 
{
	@Param(0)
    private int posI;
	@Param(1)
    private int posJ;
    private int x;
    private int y;
    @Param(2)
    private String colorStr;    
	private Color color;
    
    public Cell() {}

    public Cell(int posI, int posJ, Color color)
    {
        this.posI = posI;
        this.posJ = posJ;
        x = posJ * GlobalConstants.CELL_WIDTH;
        y = posI * GlobalConstants.CELL_HEIGHT;
        this.color = color;
        if (this.color.getRGB() == Color.RED.getRGB())
        	colorStr = "red";
        else if (this.color.getRGB() == Color.BLUE.getRGB())
        	colorStr = "blue";
        else if (this.color.getRGB() == Color.YELLOW.getRGB())
        	colorStr = "yellow";
        else if (this.color.getRGB() == Color.GREEN.getRGB())
        	colorStr = "green";
    }
    
    public Cell(Cell c)
    {
    	posI = c.posI;
    	posJ = c.posJ;
    	x = c.x;
    	y = c.y;
    	color = c.color;
    	colorStr = c.colorStr;
    }
    
    @Override
    public String toString()
    {
		return "CELL " + posI  + "-" + posJ + " " + colorStr;
    }

    public int getPosI() { return posI; }
    public void setPosI(int posI) { this.posI = posI; }

    public int getPosJ() { return posJ; }
    public void setPosJ(int posJ) { this.posJ = posJ; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    
    public String getColorStr() { return colorStr; }
	public void setColorStr(String colorStr) { this.colorStr = colorStr; }
}