package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static com.company.Main.*;

public class UI  {
    Font font2 = new Font(null, Font.BOLD, 20);
    BufferedImage life, bomb;
    GamePanel gp;
    Font font;

    public UI(GamePanel gp) {
        this.gp = gp;
        font = new Font(null, Font.BOLD, 100);
        try {
            life = ImageIO.read(new File("life.png"));
            bomb = ImageIO.read(new File("bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowState(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(font);
        String Str;
        if (gp.GameState==gp.GamePause)
            Str="  Pause";
        else if (gp.GameState==gp.LostState)
            Str="Lost =(";
        else
            Str="$$Win$$";
        g.drawString(Str, SCREEN_WIDTH / 2 -(Str.length())/2*tileSize, SCREEN_HEIGHT / 2);
    }

    public void showPlayer(Graphics g) {
        for (int i = 0; i < gp.player.health; i++)
            g.drawImage(life, i * tileSize, 0, tileSize, tileSize, null);
        for (int i = 1; i <= gp.player.numOfBombs; i++)
            g.drawImage(bomb, SCREEN_WIDTH - i * tileSize, 0, tileSize, tileSize, null);
        g.setFont(font2);
        g.setColor(Color.red);
        g.drawString(String.valueOf(gp.player.bombStr), 0, SCREEN_HEIGHT - tileSize);
    }

}
