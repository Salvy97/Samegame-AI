import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameGraphics extends JPanel implements MouseListener
{
    private static final long serialVersionUID = 1L;

    private GameCore gameCore;
    
    public GameGraphics(GameCore gameCore)
    {
    	this.gameCore = gameCore;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        Cell[][] grid = gameCore.getGrid();
        for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
        {
            for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
            {      	
            	if (grid[i][j] != null)
            	{
	                Cell cell = grid[i][j];
	                g.setColor(cell.getColor());
	                g.fillRect(cell.getX(), cell.getY(), GlobalConstants.CELL_WIDTH, GlobalConstants.CELL_HEIGHT);
	                
	                // DRAW BORDERS
	                g.setColor(Color.BLACK);
	                g.drawLine(cell.getX(), cell.getY(), cell.getX() + GlobalConstants.CELL_WIDTH, cell.getY());
	                g.drawLine(cell.getX(), cell.getY(), cell.getX(), cell.getY() + GlobalConstants.CELL_HEIGHT);
	                g.drawLine(cell.getX(), cell.getY() + GlobalConstants.CELL_HEIGHT, cell.getX() + GlobalConstants.CELL_WIDTH, cell.getY() + GlobalConstants.CELL_HEIGHT);
	                g.drawLine(cell.getX() + GlobalConstants.CELL_WIDTH, cell.getY(), cell.getX() + GlobalConstants.CELL_WIDTH, cell.getY() + GlobalConstants.CELL_HEIGHT);
            	}
           	}
        }
        
        repaint();
    }

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		Cell[][] grid = gameCore.getGrid();
        for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
            for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)    
            	if (grid[i][j] != null)
	            	if (mouseY < grid[i][j].getY() + GlobalConstants.CELL_HEIGHT && mouseY > grid[i][j].getY())
	            		if (mouseX < grid[i][j].getX() + GlobalConstants.CELL_WIDTH && mouseX > grid[i][j].getX())
	            			gameCore.makeMove(i, j);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}