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
 *
 * @author Kraiem
 */
public class JukeBoxControls extends JPanel {

	private JComboBox<String> musicCombo;
	private final JButton playButton;
	private final JButton stopButton;
	private final JButton rewindButton;
	private File[] musicFile;
	private File current;
	private AudioInputStream audioStream;
	private Clip audioClip;

	private static final String LOCAL_DIR = "/Users/yassinekraiem/Desktop/PA5Jukebox GUI/";

	/**
	 * Constructor for JukeBoxControls.
	 * Initializes the GUI controls and loads audio files.
	 */
	public JukeBoxControls() {
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

		ImageIcon playIcon = new ImageIcon(getClass().getResource("play25x25.png"));
		ImageIcon stopIcon = new ImageIcon(getClass().getResource("stop25x25.png"));

		playButton = new JButton("Play", playIcon);
		stopButton = new JButton("Stop", stopIcon);
		rewindButton = new JButton("Rewind");

		playButton.setBackground(Color.CYAN);
		stopButton.setBackground(Color.CYAN);
		rewindButton.setBackground(Color.CYAN);

		setPreferredSize(new Dimension(300, 150));
		setBackground(Color.CYAN);
		add(musicCombo);
		add(playButton);
		add(stopButton);
		add(rewindButton);

		musicCombo.addActionListener(event -> loadTrack());
		playButton.addActionListener(event -> playTrack());
		stopButton.addActionListener(event -> stopTrack());
		rewindButton.addActionListener(event -> rewindTrack());

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
		}
	}

	/**
	 * Displays an album cover visualizer.
	 * Links each track to an image displayed in a new window.
	 */
	private void displayAlbumCover() {
		JFrame albumFrame = new JFrame("Album Cover");
		JLabel albumLabel = new JLabel(new ImageIcon(getClass().getResource("cover_placeholder.jpg")));
		albumFrame.add(albumLabel);
		albumFrame.setSize(300, 300);
		albumFrame.setVisible(true);
	}

	/**
	 * Displays random geometric patterns when the music is playing.
	 */
	private void displayRandomVisualizer() {
		JFrame visualizerFrame = new JFrame("Visualizer");
		JPanel visualizerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < 10; i++) {
					g.setColor(new Color((int) (Math.random() * 0xFFFFFF)));
					g.fillOval((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()), 50, 50);
				}
			}
		};
		visualizerFrame.add(visualizerPanel);
		visualizerFrame.setSize(400, 400);
		visualizerFrame.setVisible(true);
	}
}
