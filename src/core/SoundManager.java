package core;

import collection.Settings;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private final Clip regularPickupSound;
    private final Clip speedPickupSound;
    private final Clip backgroundMusic;

    public SoundManager() {
        try {
            regularPickupSound = AudioSystem.getClip();
            speedPickupSound = AudioSystem.getClip();
            backgroundMusic = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/collection/sounds/music.wav"));
            backgroundMusic.open(audioInputStream);

            FloatControl control =(FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            float range = control.getMinimum();
            float result = range * ( 1 - Settings.MUSIC_VOLUME / 100.F);
            control.setValue(result);
            backgroundMusic.loop(100000);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playRegularSound() {
        try {
            if (regularPickupSound != null) {
                regularPickupSound.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/collection/sounds/regular_pickup.wav"));
            playSound(audioInputStream, regularPickupSound);
        } catch (UnsupportedAudioFileException | LineUnavailableException |
                 IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void playSound(AudioInputStream audioInputStream, Clip pickupSound) throws LineUnavailableException, IOException {
        pickupSound.open(audioInputStream);

        FloatControl control =(FloatControl) pickupSound.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMinimum();
        float result = range * ( 1 - Settings.SOUND_VOLUME / 100.F);
        control.setValue(result);

        pickupSound.start();
    }

    public void playSpeedSound() {
        try {
            if (speedPickupSound != null) {
                speedPickupSound.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/collection/sounds/speed_pickup.wav"));
            playSound(audioInputStream, speedPickupSound);
        } catch (UnsupportedAudioFileException | LineUnavailableException |
                 IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }
}
