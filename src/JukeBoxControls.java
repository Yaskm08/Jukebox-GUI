/**
 * JukeBoxControls.java -- example from Listing 6.13 of Lewis et al., 4th Ed.
 * Yassine Kraiem
 * CSC 230 FA 24
 * November 16th, 2024
 * Example using AudioClips with local files and JComboBox objects.
 * <p>
 * This class defines the GUI controls for the Jukebox application.
 * Enhancements include Javadoc documentation, a Rewind button,
 * album cover visualizer, random geometric patterns, and lambda-based ActionListeners.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * The JukeBoxControls class represents the control panel for the Jukebox application.
 * It allows users to play, stop, and rewind audio tracks, as well as display visualizations.
 * Includes radio buttons for shape selection in the random visualizer.
 * <p>
 * - Lambda-based ActionListeners
 * - Rewind button
 * - Album cover visualizer
 * - Random geometric visualizer
 * - Radio buttons for shape selection for the EXTRA CREDITS.
 * - File selector for additional .wav files
 *
 * @author Yassine Kraiem
 */
public class JukeBoxControls extends JPanel {

	private JComboBox<String> musicCombo;
	private JButton playButton;
	private JButton stopButton;
	private JButton rewindButton;
	private JButton selectFileButton;
	private File[] musicFile;
	private File current;
	private AudioInputStream audioStream;
	private Clip audioClip;

	private JRadioButton circleButton; // Radio button for Circles
	private JRadioButton rectangleButton; // Radio button for Rectangles

	private static final String LOCAL_DIR = "/Users/yassinekraiem/Desktop/PA5Jukebox GUI/";

	/**
	 * Constructor for JukeBoxControls.
	 * Initializes the GUI controls and loads audio files.
	 */
	public JukeBoxControls() {
		// Initialize buttons
		playButton = new JButton("Play", new ImageIcon(getClass().getResource("play25x25.png")));
		stopButton = new JButton("Stop", new ImageIcon(getClass().getResource("stop25x25.png")));
		rewindButton = new JButton("Rewind");
		selectFileButton = new JButton("Select File");

		playButton.setBackground(Color.CYAN);
		stopButton.setBackground(Color.CYAN);
		rewindButton.setBackground(Color.CYAN);
		selectFileButton.setBackground(Color.LIGHT_GRAY);

		File f1, f2, f3, f4, f5;
		f1 = f2 = f3 = f4 = f5 = null;

		try {
			f1 = new File(LOCAL_DIR + "Cinematic.wav");
			f2 = new File(LOCAL_DIR + "Country&Folk.wav");
			f3 = new File(LOCAL_DIR + "pop.wav");
			f4 = new File(LOCAL_DIR + "R&B & Soul.wav");
			f5 = new File(LOCAL_DIR + "reggae.wav");
		} catch (Exception e) {
			System.err.println("Error loading audio files.");
		}

		musicFile = new File[]{null, f1, f2, f3, f4, f5};

		String[] musicNames = {"Pick some jams!", "Cinematic", "Country & Folk", "pop",
				"R&B & Soul", "reggae"};

		musicCombo = new JComboBox<>(musicNames);
		musicCombo.setBackground(Color.CYAN);

		setPreferredSize(new Dimension(350, 200));
		setBackground(Color.CYAN);
		add(musicCombo);
		add(playButton);
		add(stopButton);
		add(rewindButton);
		add(selectFileButton);

		musicCombo.addActionListener(event -> loadTrack());
		playButton.addActionListener(event -> {
			playTrack();
			displayRandomVisualizer();
		});
		stopButton.addActionListener(event -> stopTrack());
		rewindButton.addActionListener(event -> rewindTrack());
		selectFileButton.addActionListener(event -> selectFile());

		current = null;
	}

	/**
	 * Loads the selected track from the JComboBox.
	 */
	private void loadTrack() {
		try {
			if (current != null) {
				audioClip.close();
				audioStream.close();
			}
			current = musicFile[musicCombo.getSelectedIndex()];
			if (current != null) {
				audioStream = AudioSystem.getAudioInputStream(current);
				AudioFormat format = audioStream.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.open(audioStream);
			}
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException exception) {
			System.err.println("Error loading track: " + exception.getMessage());
		}
	}

	/**
	 * Plays the loaded audio track.
	 */
	private void playTrack() {
		if (current != null) {
			audioClip.start();
		}
	}

	/**
	 * Stops the currently playing audio track.
	 */
	private void stopTrack() {
		if (current != null) {
			audioClip.stop();
		}
	}

	/**
	 * Rewinds the current track to the beginning.
	 */
	private void rewindTrack() {
		if (current != null) {
			audioClip.setFramePosition(0);
			System.out.println("Track rewound to the beginning.");
		} else {
			System.err.println("No track loaded to rewind.");
		}
	}

	/**
	 * Displays an album cover visualizer.
	 */
	private void displayAlbumCover() {
		JFrame albumFrame = new JFrame("Album Cover");
		JLabel albumLabel = new JLabel(new ImageIcon(getClass().getResource("cover_placeholder.jpg")));
		albumFrame.add(albumLabel);
		albumFrame.setSize(300, 300);
		albumFrame.setVisible(true);
	}

	/**
	 * Displays random geometric patterns with radio buttons for shape selection.
	 */
	private void displayRandomVisualizer() {
		JFrame visualizerFrame = new JFrame("Visualizer");

		// Panel for the geometric patterns
		JPanel visualizerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (circleButton.isSelected()) {
					for (int i = 0; i < 10; i++) {
						g.setColor(new Color((int) (Math.random() * 0xFFFFFF)));
						g.fillOval((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()), 50, 50);
					}
				} else if (rectangleButton.isSelected()) {
					for (int i = 0; i < 10; i++) {
						g.setColor(new Color((int) (Math.random() * 0xFFFFFF)));
						g.fillRect((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()), 50, 50);
					}
				}
			}
		};

		// Radio buttons for shape selection
		circleButton = new JRadioButton("Circles");
		rectangleButton = new JRadioButton("Rectangles");

		ButtonGroup shapeGroup = new ButtonGroup();
		shapeGroup.add(circleButton);
		shapeGroup.add(rectangleButton);

		// Default selection
		circleButton.setSelected(true);

		// Control panel for the radio buttons
		JPanel controlPanel = new JPanel();
		controlPanel.add(circleButton);
		controlPanel.add(rectangleButton);

		// Add components to the frame
		visualizerFrame.add(controlPanel, BorderLayout.NORTH);
		visualizerFrame.add(visualizerPanel, BorderLayout.CENTER);

		// Set frame properties
		visualizerFrame.setSize(400, 400);
		visualizerFrame.setVisible(true);

		// Repaint the visualizer when a radio button is selected
		ActionListener repaintAction = event -> visualizerPanel.repaint();
		circleButton.addActionListener(repaintAction);
		rectangleButton.addActionListener(repaintAction);
	}

	/**
	 * Opens a file chooser to select additional .wav files and plays the selected file.
	 */
	private void selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());

			try {
				// Close the previous audio clip if it's playing
				if (audioClip != null) {
					audioClip.close();
					audioStream.close();
				}

				// Load the selected file
				current = selectedFile;
				audioStream = AudioSystem.getAudioInputStream(current);
				AudioFormat format = audioStream.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.open(audioStream);

				// Play the audio
				audioClip.start();
				System.out.println("Playing selected file: " + current.getName());

			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				System.err.println("Error playing the selected file: " + e.getMessage());
			}
		}
	}
}
