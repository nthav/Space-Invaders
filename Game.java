/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run(){
        // NOTE : recall that the 'final' keyword notes inmutability
		  // even for local variables. 

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("SPACE INVADERS");
        frame.setLocation(500,200);

		  // Status panel
        final JPanel status_panel = new JPanel(new GridLayout());
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Score: ");
        final JLabel score = new JLabel("Lives: ");
        final JLabel level = new JLabel("Level: ");
        status_panel.add(score);
        status_panel.add(status);
        status_panel.add(level);
       
        
        // Main playing area
        //TAKEN FROM http://stackoverflow.com/questions/15258467/java-how-can-i-
        //popup-a-dialog-box-as-only-an-image
        JLabel lbl = new JLabel(new ImageIcon("intro.jpg"));
        JOptionPane.showMessageDialog(null, lbl, "ImageDialog", 
                                     JOptionPane.PLAIN_MESSAGE, null);
        
        final GameCourt court = new GameCourt(status, score, level);
        frame.add(court, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(frame, "Space Invaders Instructions\n"
        		+ "Use the arrow keys (<- and ->) to move the player "
        		+ "ship to the left or to the right of the screen\n"
        		+ "Press the space bar to shoot the enemy ships\n"
        		+ "Each player will have 3 lives in the game, and a life is lost "
        		+ "when the enemy ships crashes into the player ship\n"
        		+ "The player wins when all the enemy ships clear for "
        		+ "all 10 levels\n"
        		+ "\n\nSpecial Features:\n"
        		+ "Different ships are worth a different amount of points "
        		+ "depending on their position\n"
        		+ "Enemy ships move faster and UFO bullets hit you quicker "
        		+ "with each increase in level\n"
        		+ "Be careful of the UFOs! They shoot yellow bullets that "
        		+ "will kill you instantly!\nYou won't be able to kill them "
        		+ "but you will get 100 points for every shot\n"
        		+ "\n\nAre you ready?\n");

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is 
        // an instance of ActionListener with its actionPerformed() 
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    court.reset();
                }
            });
        control_panel.add(reset);
        
        final JButton start = new JButton("Start");
        reset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		court.playing = !court.playing;
        	}
        });
        control_panel.add(start);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /*
     * Main method run to start and run the game
     * Initializes the GUI elements specified in Game and runs it
     * NOTE: Do NOT delete! You MUST include this in the final submission of your game.
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Game());
    }
}
