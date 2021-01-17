import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class Main 
{
    public static void main(String[] args) 
    {
        JFrame window = new JFrame("Same Game");
        window.setSize(GlobalConstants.SCREEN_WIDTH, GlobalConstants.SCREEN_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        GameCore mainGame = new GameCore();
        
        GameGraphics gameGraphics = new GameGraphics(mainGame);
        gameGraphics.setPreferredSize(new Dimension(GlobalConstants.SCREEN_WIDTH, GlobalConstants.SCREEN_HEIGHT));
        window.getContentPane().add(gameGraphics);
        window.addMouseListener(gameGraphics);
        window.pack();

        window.setVisible(true);
        window.setEnabled(true);
        
        JDialog controlWindow = new JDialog(window, "Control Panel");
        ControlPanel controlPanel = new ControlPanel(mainGame, new DLVPlayer(mainGame));

        controlWindow.add(controlPanel);
        controlWindow.setSize(200, 100);
        controlWindow.setLocationByPlatform(true);
        controlWindow.setVisible(true);
    }
}