import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.awt.Color;

public class GameCore
{
    private Cell[][] grid;
    private Cell[][] undoGrid;
    private Color[] gameColors = { Color.RED, Color.BLUE, Color.YELLOW };
    private int score;
    
    //private static GameCore instance;

    /*public static GameCore getInstance()
    {
        if (instance == null)
            instance = new GameCore();
        return instance;
    }*/

    public GameCore()
    {
    	initializeGame();
    }
    
    public GameCore(GameCore gameCore)
    {
    	grid = new Cell[GlobalConstants.GRID_ROWS][GlobalConstants.GRID_COLUMNS];
    	for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
            for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
            	grid[i][j] = gameCore.grid[i][j];
    	score = gameCore.score;
    }
    
    private void initializeGame()
    {
    	grid = new Cell[GlobalConstants.GRID_ROWS][GlobalConstants.GRID_COLUMNS];
        score = 0;
        
        // FILL THE GRID
        Random random = new Random();
        for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
        {
            for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
            {
            	 if (grid[i][j] == null)
                 {
                     ArrayList<Integer> blockChoices = new ArrayList<Integer>();
                     for (int k = 0; k < 3; k++)
                    	 blockChoices.add(k);
                     int blockChoice = 0;
                     if (!isSingleBlock())
                    	 blockChoice = blockChoices.get(random.nextInt(blockChoices.size()));
                     Color colorChosen = gameColors[random.nextInt(gameColors.length)];
                     boolean filled = false;
                     while (!filled)
                     {
                         filled = fillBlock(i, j, blockChoice, colorChosen);
                         if (!filled)
                         {
	                         blockChoices.remove((Integer) blockChoice);
	                         blockChoice = blockChoices.get(random.nextInt(blockChoices.size()));
                         }
                     }
                 }
            }
        }
        
        //readMapFromFile("grid.txt");
    }
    
    private boolean isSingleBlock()
    {
    	Random random = new Random();
    	int c = random.nextInt(10);
    	if (c <= 7)
    		return true;
    	return false;
    }

    private boolean fillBlock(int i, int j, int blockChoice, Color colorChosen)
    {
        switch (blockChoice)
        {
            // #
            case 0: {
                        grid[i][j] = new Cell(i, j, colorChosen);
                        return true;
                    }

            // ##   
            case 1: {
                        if (i > 0 && grid[i - 1][j] == null)
                        {
                            grid[i - 1][j] = new Cell(i - 1, j, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        else if (i < GlobalConstants.GRID_ROWS - 1 && grid[i + 1][j] == null)
                        {
                            grid[i + 1][j] = new Cell(i + 1, j, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }

                        if (j > 0 && grid[i][j - 1] == null)
                        {
                            grid[i][j - 1] = new Cell(i, j - 1, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        else if (j < GlobalConstants.GRID_COLUMNS - 1 && grid[i][j + 1] == null)
                        {
                            grid[i][j + 1] = new Cell(i, j + 1, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        break;
                    }
            
            // ###
            case 2: {
                        if (i > 1 && grid[i - 1][j] == null && grid[i - 2][j] == null)
                        {
                            grid[i - 2][j] = new Cell(i - 2, j, colorChosen);
                            grid[i - 1][j] = new Cell(i - 1, j, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        else if (i < GlobalConstants.GRID_ROWS - 2 && grid[i + 1][j] == null && grid[i + 2][j] == null)
                        {
                            grid[i + 2][j] = new Cell(i + 2, j, colorChosen);
                            grid[i + 1][j] = new Cell(i + 1, j, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }

                        if (j > 1 && grid[i][j - 1] == null && grid[i][j - 2] == null)
                        {
                            grid[i][j - 2] = new Cell(i, j - 2, colorChosen);
                            grid[i][j - 1] = new Cell(i, j - 1, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        else if (j < GlobalConstants.GRID_COLUMNS - 2 && grid[i][j + 1] == null)
                        {
                            grid[i][j + 2] = new Cell(i, j + 2, colorChosen);
                            grid[i][j + 1] = new Cell(i, j + 1, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        break;
                    }

            // ####
            /*case 3: {
                        if (i > 2 && grid[i - 1][j] == null && grid[i - 2][j] == null && grid[i - 3][j] == null)
                        {
                            grid[i - 3][j] = new Cell(i - 3, j, colorChosen);
                            grid[i - 2][j] = new Cell(i - 2, j, colorChosen);
                            grid[i - 1][j] = new Cell(i - 1, j, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        else if (i < GlobalConstants.GRID_SIZE - 3 && grid[i + 1][j] == null && grid[i + 2][j] == null && grid[i + 3][j] == null)
                        {
                            grid[i + 3][j] = new Cell(i + 3, j, colorChosen);
                            grid[i + 2][j] = new Cell(i + 2, j, colorChosen);
                            grid[i + 1][j] = new Cell(i + 1, j, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }

                        if (j > 2 && grid[i][j - 1] == null && grid[i][j - 2] == null && grid[i][j - 3] == null)
                        {
                            grid[i][j - 3] = new Cell(i, j - 3, colorChosen);
                            grid[i][j - 2] = new Cell(i, j - 2, colorChosen);
                            grid[i][j - 1] = new Cell(i, j - 1, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        else if (j < GlobalConstants.GRID_SIZE - 3 && grid[i][j + 1] == null && grid[i][j + 2] == null && grid[i][j + 3] == null)
                        {
                            grid[i][j + 3] = new Cell(i, j + 3, colorChosen);
                            grid[i][j + 2] = new Cell(i, j + 2, colorChosen);
                            grid[i][j + 1] = new Cell(i, j + 1, colorChosen);
                            grid[i][j] = new Cell(i, j, colorChosen);
                            return true;
                        }
                        break;
                    }*/
        }
        return false;
    }

    public Cell[][] getGrid() { return grid; }
    
    public void makeMove(int posI, int posJ)
    {
    	undoGrid = new Cell[GlobalConstants.GRID_ROWS][GlobalConstants.GRID_COLUMNS];
    	for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
    		for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
    			if (grid[i][j] != null)
    				undoGrid[i][j] = new Cell(grid[i][j]);
    	
    	int tempScore = (int) Math.pow(getBlockSize(posI, posJ) - 2, 2);
    	LinkedList<Cell> toCheck = new LinkedList<Cell>();
    	toCheck.add(grid[posI][posJ]);
    	Color colorToCheck = grid[posI][posJ].getColor();
    	boolean wasSingle = true;
    	while (!toCheck.isEmpty())
    	{
    		Cell curr = toCheck.getFirst();
    		if (curr.getPosI() > 0 && grid[curr.getPosI() - 1][curr.getPosJ()] != null && grid[curr.getPosI() - 1][curr.getPosJ()].getColor() == colorToCheck)
    			toCheck.add(grid[curr.getPosI() - 1][curr.getPosJ()]);
    		if (curr.getPosI() < GlobalConstants.GRID_ROWS - 1 && grid[curr.getPosI() + 1][curr.getPosJ()] != null && grid[curr.getPosI() + 1][curr.getPosJ()].getColor() == colorToCheck)
    			toCheck.add(grid[curr.getPosI() + 1][curr.getPosJ()]);
    		if (curr.getPosJ() > 0 && grid[curr.getPosI()][curr.getPosJ() - 1] != null && grid[curr.getPosI()][curr.getPosJ() - 1].getColor() == colorToCheck)
    			toCheck.add(grid[curr.getPosI()][curr.getPosJ() - 1]);
    		if (curr.getPosJ() < GlobalConstants.GRID_COLUMNS - 1 && grid[curr.getPosI()][curr.getPosJ() + 1] != null && grid[curr.getPosI()][curr.getPosJ() + 1].getColor() == colorToCheck)
    			toCheck.add(grid[curr.getPosI()][curr.getPosJ() + 1]);
    		if (toCheck.size() > 1)
    			wasSingle = false;
    		if (!wasSingle)
    			grid[curr.getPosI()][curr.getPosJ()] = null;
    		toCheck.removeFirst();
    	}
    	if (!wasSingle)
    	{
    		score += tempScore;
    		updateGrid();
    		if (isGameWon())
    			score += 1000;
    	}
    }
    
    private void updateGrid()
    {
    	// SPOSTARE TUTTO SOTTO
    	while (stillHaveToShiftDown())
    	{
	    	for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
	        {
	            for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
	            {
	            	if (grid[i][j] != null)
	            	{
	            		boolean done = false;
	            		int t_i = i;
	            		while (!done)
	            		{
		            		if (t_i < GlobalConstants.GRID_ROWS - 1 && grid[t_i + 1][j] == null)
		            		{
		            			grid[t_i + 1][j] = new Cell(t_i + 1, j, grid[t_i][j].getColor());
		            			grid[t_i][j] = null;
		            			for (int k = t_i - 1; k >= 0; k--)
		            			{
		            				if (grid[k][j] != null)
		            				{
			            				grid[k + 1][j] = new Cell(k + 1, j, grid[k][j].getColor());
				            			grid[k][j] = null;
		            				}
		            			}
		            			t_i++;
		            		}
		            		if (t_i == GlobalConstants.GRID_ROWS - 1 || (t_i < GlobalConstants.GRID_ROWS - 1 && grid[t_i][j] != null))
		            			done = true;
	            		}
	            	}
	            }
	        }
    	}
    	
    	// SPOSTARE TUTTO A SINISTRA
    	while (true)
    	{
    		boolean foundNull = false, finished = true, canShift = false;
    		int startingPos = -1;
            for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
            {
    			if (grid[GlobalConstants.GRID_ROWS - 1][j] == null)
    			{
    				foundNull = true;
    			}
    			else
    			{
    				if (foundNull)
    				{
    					canShift = true;
    					finished = false;
    					startingPos = j;
    					break;
    				}
    			}
            }
            if (finished)
            	break;
            if (canShift)
            {
				for (int l = 0; l < GlobalConstants.GRID_ROWS; l++)
				{
					for (int k = GlobalConstants.GRID_COLUMNS - 1; k >= startingPos; k--)
					{
						if (grid[l][k] != null)
						{
							boolean done = false;
		            		int t_k = k;
		            		while (!done)
		            		{
			            		if (t_k > 0 && grid[l][t_k - 1] == null)
			            		{
			            			grid[l][t_k - 1] = new Cell(l, t_k - 1, grid[l][t_k].getColor());
			            			grid[l][t_k] = null;
			            			for (int n = t_k + 1; n < GlobalConstants.GRID_COLUMNS; n++)
			            			{
			            				if (grid[l][n] != null)
			            				{
				            				grid[l][n - 1] = new Cell(l, n - 1, grid[l][n].getColor());
					            			grid[l][n] = null;
			            				}
			            			}
			            			t_k--;
			            		}
			            		if (t_k == 0 || (t_k > 0 && grid[l][k - 1] != null))
			            			done = true;
		            		}
						}
					}
				}
	        }
        }
    }
    
    private boolean stillHaveToShiftDown()
    {
    	boolean foundNull = false;
        for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
        {
        	foundNull = false;
        	for (int i = GlobalConstants.GRID_ROWS - 1; i >= 0; i--)
            {
        		
				if (grid[i][j] == null)
				{
					foundNull = true;
				}
				else
				{
					if (foundNull)
					{
						return true;
					}
				}
            }
        }
        return false;
    }
    
    public int getBlockSize(int i, int j)
    {
    	LinkedList<Cell> toCheck = new LinkedList<Cell>();
    	ArrayList<Cell> alreadyChecked = new ArrayList<Cell>();
    	toCheck.add(grid[i][j]);
    	Color colorToCheck = grid[i][j].getColor();
    	while (!toCheck.isEmpty())
    	{
    		Cell curr = toCheck.getFirst();
    		if (curr.getPosI() > 0 && grid[curr.getPosI() - 1][curr.getPosJ()] != null && grid[curr.getPosI() - 1][curr.getPosJ()].getColor() == colorToCheck && !alreadyChecked.contains(grid[curr.getPosI() - 1][curr.getPosJ()]))
    		{	
    			toCheck.add(grid[curr.getPosI() - 1][curr.getPosJ()]);
    			alreadyChecked.add(grid[curr.getPosI() - 1][curr.getPosJ()]);
    		}
    		if (curr.getPosI() < GlobalConstants.GRID_ROWS - 1 && grid[curr.getPosI() + 1][curr.getPosJ()] != null && grid[curr.getPosI() + 1][curr.getPosJ()].getColor() == colorToCheck && !alreadyChecked.contains(grid[curr.getPosI() + 1][curr.getPosJ()]))
    		{
    			toCheck.add(grid[curr.getPosI() + 1][curr.getPosJ()]);
    			alreadyChecked.add(grid[curr.getPosI() + 1][curr.getPosJ()]);
    		}
    		if (curr.getPosJ() > 0 && grid[curr.getPosI()][curr.getPosJ() - 1] != null && grid[curr.getPosI()][curr.getPosJ() - 1].getColor() == colorToCheck && !alreadyChecked.contains(grid[curr.getPosI()][curr.getPosJ() - 1]))
    		{
    			toCheck.add(grid[curr.getPosI()][curr.getPosJ() - 1]);
    			alreadyChecked.add(grid[curr.getPosI()][curr.getPosJ() - 1]);
    		}
    		if (curr.getPosJ() < GlobalConstants.GRID_COLUMNS - 1 && grid[curr.getPosI()][curr.getPosJ() + 1] != null && grid[curr.getPosI()][curr.getPosJ() + 1].getColor() == colorToCheck && !alreadyChecked.contains(grid[curr.getPosI()][curr.getPosJ() + 1]))
    		{	
    			toCheck.add(grid[curr.getPosI()][curr.getPosJ() + 1]);
    			alreadyChecked.add(grid[curr.getPosI()][curr.getPosJ() + 1]);
    		}
    		toCheck.removeFirst();
    	}
    	if (alreadyChecked.size() == 0)
    		return 1;
    	return alreadyChecked.size();
    }

	public int getScore() { return score; }
	
	public void readMapFromFile(String path)
	{
		ArrayList<String> file = FileManager.content(path);
		int i = 0;
		int j = 0;
		for (String line : file)
		{
			for (int k = 0; k < line.length(); k++)
			{
				grid[i][j] = new Cell(i, j, getColorByNumber(Integer.parseInt(line.charAt(k) + "")));
				j++;
			}
			j = 0;
			i++;
		}
	}
	
	private Color getColorByNumber(int n)
	{
		switch (n)
		{
			case 1: return Color.RED;
			case 2: return Color.BLUE;
			case 3: return Color.YELLOW;
		}
		return Color.GREEN;
	}
	
	public void printGrid()
	{
		 for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
		 {
			 for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
			 {
				 if (grid[i][j] != null)
					 System.out.print(grid[i][j].getColorStr().charAt(0) + "  ");
				 else
					 System.out.print("+  ");
			 }
			 System.out.println();
		 }
		 System.out.println();
		 System.out.println();
	}
	
	public void restart()
	{
		initializeGame();
	}
	
	public boolean isGameLost()
	{
		boolean allNull = true;
		for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
		{
			for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
			{
				if (grid[i][j] != null)
				{
					if (i > 0 && grid[i - 1][j] != null && grid[i - 1][j].getColor() == grid[i][j].getColor())
						return false;
					if (i < GlobalConstants.GRID_ROWS - 1 && grid[i + 1][j] != null && grid[i + 1][j].getColor() == grid[i][j].getColor())
						return false;
					if (j > 0 && grid[i][j - 1] != null && grid[i][j - 1].getColor() == grid[i][j].getColor())
						return false;
					if (j < GlobalConstants.GRID_COLUMNS - 1 && grid[i][j + 1] != null &&  grid[i][j + 1].getColor() == grid[i][j].getColor())
						return false;
					allNull = false;
				}
			}
		}
		return !allNull;
	}
	
	public boolean isCellSingle(int i, int j)
	{
		if (grid[i][j] == null)
			return true;
		if (i > 0 && grid[i - 1][j] != null && grid[i - 1][j].getColor() == grid[i][j].getColor())
			return false;
		if (i < GlobalConstants.GRID_ROWS - 1 && grid[i + 1][j] != null && grid[i + 1][j].getColor() == grid[i][j].getColor())
			return false;
		if (j > 0 && grid[i][j - 1] != null && grid[i][j - 1].getColor() == grid[i][j].getColor())
			return false;
		if (j < GlobalConstants.GRID_COLUMNS - 1 && grid[i][j + 1] != null &&  grid[i][j + 1].getColor() == grid[i][j].getColor())
			return false;
		return true;
	}
	
	public boolean isWinnable()
	{
		int filledCellsNumber = 0;
		for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
			for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
				if (grid[i][j] != null)
					filledCellsNumber++;
		if (filledCellsNumber == 0)
			return true;
		else if (isGameLost())
			return false;
		
		boolean gameWon = false;
		for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
		{
			for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
			{
				if (!isCellSingle(i, j) && grid[i][j] != null)
				{
					GameCore newGameCoreInstance = new GameCore(this);
					newGameCoreInstance.makeMove(i, j);
					//newGameCoreInstance.printGrid();
					gameWon = newGameCoreInstance.isWinnable();
					if (gameWon)
						return true;
				}
			}
		}
		return gameWon;
	}
	
	public void undoMove()
	{
		grid = undoGrid;
	}
	
	public int getNumberOfBlocks(Color color)
	{
		int n = 0;
		ArrayList<Cell> blockDoneAlready = new ArrayList<Cell>();
		
		for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
		{
			for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
			{
				if (grid[i][j] != null && !blockDoneAlready.contains(grid[i][j]) && grid[i][j].getColor() == color)
				{
					LinkedList<Cell> toCheck = new LinkedList<Cell>();
			    	ArrayList<Cell> alreadyChecked = new ArrayList<Cell>();
			    	toCheck.add(grid[i][j]);
			    	while (!toCheck.isEmpty())
			    	{
			    		Cell curr = toCheck.getFirst();
			    		if (curr.getPosI() > 0 && grid[curr.getPosI() - 1][curr.getPosJ()] != null && grid[curr.getPosI() - 1][curr.getPosJ()].getColor() == color && !alreadyChecked.contains(grid[curr.getPosI() - 1][curr.getPosJ()]))
			    		{	
			    			toCheck.add(grid[curr.getPosI() - 1][curr.getPosJ()]);
			    			alreadyChecked.add(grid[curr.getPosI() - 1][curr.getPosJ()]);
			    			blockDoneAlready.add(grid[curr.getPosI() - 1][curr.getPosJ()]);
			    		}
			    		if (curr.getPosI() < GlobalConstants.GRID_ROWS - 1 && grid[curr.getPosI() + 1][curr.getPosJ()] != null && grid[curr.getPosI() + 1][curr.getPosJ()].getColor() == color && !alreadyChecked.contains(grid[curr.getPosI() + 1][curr.getPosJ()]))
			    		{
			    			toCheck.add(grid[curr.getPosI() + 1][curr.getPosJ()]);
			    			alreadyChecked.add(grid[curr.getPosI() + 1][curr.getPosJ()]);
			    			blockDoneAlready.add(grid[curr.getPosI() + 1][curr.getPosJ()]);
			    		}
			    		if (curr.getPosJ() > 0 && grid[curr.getPosI()][curr.getPosJ() - 1] != null && grid[curr.getPosI()][curr.getPosJ() - 1].getColor() == color && !alreadyChecked.contains(grid[curr.getPosI()][curr.getPosJ() - 1]))
			    		{
			    			toCheck.add(grid[curr.getPosI()][curr.getPosJ() - 1]);
			    			alreadyChecked.add(grid[curr.getPosI()][curr.getPosJ() - 1]);
			    			blockDoneAlready.add(grid[curr.getPosI()][curr.getPosJ() - 1]);
			    		}
			    		if (curr.getPosJ() < GlobalConstants.GRID_COLUMNS - 1 && grid[curr.getPosI()][curr.getPosJ() + 1] != null && grid[curr.getPosI()][curr.getPosJ() + 1].getColor() == color && !alreadyChecked.contains(grid[curr.getPosI()][curr.getPosJ() + 1]))
			    		{	
			    			toCheck.add(grid[curr.getPosI()][curr.getPosJ() + 1]);
			    			alreadyChecked.add(grid[curr.getPosI()][curr.getPosJ() + 1]);
			    			blockDoneAlready.add(grid[curr.getPosI()][curr.getPosJ() + 1]);
			    		}
			    		toCheck.removeFirst();
			    	}
			    	n++;
				}
			}
		}
		
		return n;
	}

	public Color[] getGameColors() { return gameColors; }
	
	public String convertColorInString(Color color)
	{
		if (color.getRGB() == Color.RED.getRGB())
			return "red";
        else if (color.getRGB() == Color.BLUE.getRGB())
        	return "blue";
        else if (color.getRGB() == Color.YELLOW.getRGB())
        	return "yellow";
        return "green";
	}
	
	public boolean isGameWon()
	{
		for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
			for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
				if (grid[i][j] != null)
					return false;
		return true;
	}
	
	public boolean isGameOver()
	{
		if (isGameLost() || isGameWon())
			return true;
		return false;
	}
}