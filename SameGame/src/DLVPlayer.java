import java.util.ArrayList;
import java.util.Random;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class DLVPlayer 
{
	private static Handler handler;
	private GameCore gameCore;
	private InputProgram facts;
	
	private int numOfGames;
	private int numOfWins;
	private int numOfLosses;
	
	// FILE DI INPUT DLV2
	private static String encodingResource = "solver/samegame";
	private static String dlv2Resource = "solver/dlv2";
	
	public DLVPlayer(GameCore gameCore)
	{
		this.gameCore = gameCore;
		
		numOfGames = 0;
		numOfWins = 0;
		numOfLosses = 0;
		
		updateFacts();
		
		//play();
	}
	
	public void play()
	{
		while (makeNextMove())
			continue;
		
		if (gameCore.isGameLost())
			numOfLosses++;
		else if (gameCore.isGameWon())
			numOfWins++;

		numOfGames++;
		
		printStats();
		
		gameCore.restart();
		play();
	}
	
	public void updateFacts()
	{
		facts = new ASPInputProgram();

		Cell[][] grid = gameCore.getGrid();
		for (int i = 0; i < GlobalConstants.GRID_ROWS; i++)
		{	
			for (int j = 0; j < GlobalConstants.GRID_COLUMNS; j++)
			{
				if (grid[i][j] != null)
				{
					try 
					{
						facts.addObjectInput(new Cell(i, j, grid[i][j].getColor()));
						facts.addObjectInput(new BlockSize(i, j, gameCore.getBlockSize(i, j)));
						facts.addObjectInput(new CellColor(grid[i][j].getColorStr()));
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}	
			}
		}
	}
	
	public boolean makeNextMove()
	{
		handler = new DesktopHandler(new DLV2DesktopService(dlv2Resource));
		updateFacts();
		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);
		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		
		if (answers.getAnswersets().size() <= 0)
		{
			System.out.println(answers.getErrors());
			System.out.println(gameCore.getScore());
			return false;
		}
		
		AnswerSet optimum = answers.getAnswersets().get(answers.getAnswersets().size() - 1);

		ArrayList<Pair> possibleMoves = new ArrayList<Pair>();
		for (int j = 0; j < optimum.getAnswerSet().size(); j++)
		{		
			String subs = "";
			for (int i = 0; i < optimum.getAnswerSet().get(j).length(); i++)
			{
				if (optimum.getAnswerSet().get(j).charAt(i) == '(')
				{
					if (subs.equals("pick"))
					{
						i++;
						String iS = "";
						while (optimum.getAnswerSet().get(j).charAt(i) != ',')
						{
							iS += optimum.getAnswerSet().get(j).charAt(i);
							i++;
						}
						i++;
						String jS = "";
						while (optimum.getAnswerSet().get(j).charAt(i) != ')')
						{
							jS += optimum.getAnswerSet().get(j).charAt(i);
							i++;
						}
						possibleMoves.add(new Pair(Integer.parseInt(iS), Integer.parseInt(jS)));
					}
				}
				subs += optimum.getAnswerSet().get(j).charAt(i);
			}
		}
		if (possibleMoves.size() == 0)
			return false;
		Pair randomMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
		gameCore.makeMove(randomMove.getI(), randomMove.getJ());
		return true;
	}
	
	public void printStats()
	{
		System.out.println("CURRENT STATS");
		System.out.println("-----------------------");
		System.out.println("Number of games played: " + numOfGames);
		System.out.println("Number of games lost: " + numOfLosses);
		System.out.println("Number of games won: " + numOfWins);
		System.out.println("Win percentage: " + (int)(((float)numOfWins / numOfGames) * 100));
		System.out.println();
	}
}