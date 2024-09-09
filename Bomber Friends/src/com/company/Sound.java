package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    Clip clip;
    File[] soundURL = new File[5];

    public Sound() {
        soundURL[0] = new File("assets_sound_music_ingame.wav");
        soundURL[1] = new File("powerup.wav");
        soundURL[2] = new File("placeingbomb.wav");
        soundURL[3] = new File("Explode.wav");
        soundURL[4] = new File("assets_sound_music_gameover.wav");


    }

    public void setFile(int i) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
        clip.close();
    }
}
