package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class KeyHandler extends KeyAdapter  {
    GamePanel gp;
    private char direction = 'I';

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                direction = 'R';
                break;
            case KeyEvent.VK_UP:
                direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                direction = 'D';
                break;
            case KeyEvent.VK_SPACE: {
                direction = 'S';

                break;
            }
            case KeyEvent.VK_P: {
                if (gp.GameState == gp.GamePause) {
                    gp.GameState = gp.GameContinue;
                    gp.stopMusic();
                    gp.playMusic(0);
                }
                else if (gp.GameState == gp.GameContinue) {
                    gp.GameState = gp.GamePause;
                    gp.stopMusic();
                    gp.playMusic(4);
                }
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
            direction = 'I';
    }

    public char getDirection() {
        return direction;
    }
}
