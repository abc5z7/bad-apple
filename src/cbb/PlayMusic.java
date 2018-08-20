package cbb;

import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

class PlayMusic {
    PlayMusic() {
        String fileName = "./music/badapple.mp3";
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(fileName));
            Player player = new Player(buffer);
            Thread.sleep(5000);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

