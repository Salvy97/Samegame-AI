import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private final long WAITING_MILLIS = 1000; 
	
	private GameCore gameCore;
	
	private JButton resetButton;
	private JButton startButton;
	private JButton solveButton;
	private JButton undoButton;
	private DLVPlayer dlvPlayer;
	
	public ControlPanel(GameCore _gameCore, DLVPlayer _dlvPlayer)
	{
		gameCore = _gameCore;
		dlvPlayer = _dlvPlayer;
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				gameCore.restart();
		        dlvPlayer = new DLVPlayer(gameCore); 
			}
		});
		resetButton.setVisible(true);
		resetButton.setEnabled(true);
		add(resetButton);
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				startDLVPlayer();
			}
		});
		startButton.setVisible(true);
		startButton.setEnabled(true);
		add(startButton);
		
		solveButton = new JButton("Is Winnable");
		solveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				System.out.println(gameCore.isWinnable());
			}
		});
		solveButton.setVisible(true);
		solveButton.setEnabled(true);
		add(solveButton);
		
		undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				gameCore.undoMove();
			}
		});
		undoButton.setVisible(true);
		undoButton.setEnabled(true);
		add(undoButton);
	}
	
	private void startDLVPlayer()
	{
		if (dlvPlayer != null)
		{
			Thread dlvThread = new Thread()
			{
				@Override
				public void run()
				{
					while (dlvPlayer.makeNextMove())
			        {
			        	try 
			        	{
							Thread.sleep(WAITING_MILLIS);
						} 
			        	catch (InterruptedException e) 
			        	{
							e.printStackTrace();
						}
			        }
				}
			};
			dlvThread.start();
		}
	}
}