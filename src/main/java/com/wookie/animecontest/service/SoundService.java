package com.wookie.animecontest.service;

import com.google.common.io.Resources;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.springframework.stereotype.Component;

@Component
public class SoundService {
    private static String WIN_SOUND_NAME = "win.mp3";
    private static String LOSE_SOUND_NAME = "lose.mp3";


    public void runSoundWin() {
        runSound(WIN_SOUND_NAME);
    }

    public void runSoundLose() {
        runSound(LOSE_SOUND_NAME);
    }

    private void runSound(String filename) {
        String url = Resources.getResource(filename).toString();
        Media hit = new Media(url);
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
