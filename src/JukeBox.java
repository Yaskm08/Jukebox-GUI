/**
 * JukeBox.java -- example from Listing 6.13 of Lewis et al., 4th Ed.
 * Yassine Kraiem
 * CSC 230 FA 24
 * November 16th, 2024
 * Example using AudioClips with local files and JComboBox objects.
 * <p>
 * This is the main class for the Jukebox application.
 * It initializes the GUI and starts the application.
 */

import javax.swing.*;

/**
 * Main class for the Jukebox application.
 * <p>
 * This class sets up the main JFrame window and adds the JukeBoxControls panel to it.
 *
 * @author Kraiem
 */
public class JukeBox {

	/**
	 * The main method for launching the Jukebox application.
	 *
	 * @param args command-line arguments (not used in this application)
	 */
	public static void main(String[] args) {
		// Create the main application frame
		JFrame frame = new JFrame("Java Juke Box");

		// Set the default close operation
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add the control panel to the frame
		frame.getContentPane().add(new JukeBoxControls());

		// Adjust the frame to fit the components
		frame.pack();

		// Make the frame visible
		frame.setVisible(true);
	}
}
