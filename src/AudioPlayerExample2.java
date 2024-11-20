import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * This class demonstrates audio playback using the SourceDataLine in the Java Sound API.
 * It has been refactored to match JukeBox project requirements.
 *
 * @author Yassine Kraiem
 * CSC 230 FA 24
 * November 16th, 2024
 */
public class AudioPlayerExample2 {

    private static final int BUFFER_SIZE = 4096;

    /**
     * Plays a given audio file.
     *
     * @param audioFilePath the path to the audio file
     */
    public void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

            audioLine.open(format);
            audioLine.start();
            System.out.println("Playback started.");

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = audioStream.read(buffer)) != -1) {
                audioLine.write(buffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            System.out.println("Playback completed.");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
