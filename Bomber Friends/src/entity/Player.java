package entity;

import com.company.GamePanel;
import com.company.KeyHandler;
import com.company.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import static com.company.Main.TileCol;
import static com.company.Main.tileSize;

public class Player extends MovingThings  {
    public int numOfBombs = 4;
    public int CoolDown = 2000000000;
    public int bombStr = 1;
    KeyHandler keyH;
    long[] lastDrop = new long[4];
    int bombRad = 3;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        setImages();
    }

    public void setDefaultValues() {
        x = (TileCol - 2) * tileSize;
        y = tileSize;
        speed = 3;
        health = Main.Plearhealth;
        solidRectangle = new Rectangle();
        solidRectangle.x = (TileCol - 2) * tileSize + 6;
        solidRectangle.y = 2 * tileSize - 22;
        solidRectangle.width = 32 - 10;
        solidRectangle.height = 32 - 10;
    }

    void setImages() {
        try {
            up = new BufferedImage[4];
            for (int i = 1; i <= 4; i++)
                up[i - 1] = ImageIO.read(new File(("up (" + i + ").png")));
            down = new BufferedImage[4];
            for (int i = 1; i <= 4; i++)
                down[i - 1] = ImageIO.read(new File(("down (" + i + ").png")));
            left = new BufferedImage[4];
            for (int i = 1; i <= 4; i++)
                left[i - 1] = ImageIO.read(new File(("left (" + i + ").png")));
            right = new BufferedImage[4];
            for (int i = 1; i <= 4; i++)
                right[i - 1] = ImageIO.read(new File(("right (" + i + ").png")));
            idle = ImageIO.read(new File(("down (1).png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        numOfBombs = 0;
        for (int i = 0; i < 4; i++)
            if (System.nanoTime() - lastDrop[i] > CoolDown)
                numOfBombs++;
        direction = keyH.getDirection();
        collisionOn = false;
        gp.checker.CheckTile(this);
        if (!collisionOn) {
            switch (keyH.getDirection()) {
                case 'U': {
                    y -= speed;
                    solidRectangle.y -= speed;
                    break;
                }
                case 'L': {
                    x -= speed;
                    solidRectangle.x -= speed;
                    break;
                }
                case 'R': {
                    x += speed;
                    solidRectangle.x += speed;
                    break;
                }
                case 'D': {
                    y += speed;
                    solidRectangle.y += speed;
                    break;
                }
                case 'S': {
                    placeBomb();
                    break;
                }
            }
        }
        frameCounter++;
        if (frameCounter > 5) {
            imageMode++;
            if (imageMode > 3) imageMode = 0;
            frameCounter = 0;
        }
    }

    private void placeBomb() {
        Bomb bomb = new Bomb(bombRad, bombStr, solidRectangle.y / tileSize, solidRectangle.x / tileSize, this, gp);
        if (!gp.world.bombs.contains(bomb) && numOfBombs > 0) {
            for (int i = 0; i < 4; i++)
                if (System.nanoTime() - lastDrop[i] > CoolDown) {
                    lastDrop[i] = System.nanoTime();
                    break;
                }
            gp.playSE(2);
            gp.world.bombs.add(bomb);
            // gp.playSE(2);
        }
    }

    public void Draw(Graphics g) {
        BufferedImage image = null;
        switch (direction) {
            case 'U':
                image = up[imageMode];
                break;
            case 'D':
                image = down[imageMode];
                break;
            case 'L':
                image = left[imageMode];
                break;
            case 'R':
                image = right[imageMode];
                break;
            default:
                image = idle;
        }
        g.drawImage(image, x, y, tileSize - 12, tileSize, null);
        g.setColor(Color.white);
        g.drawRect(solidRectangle.x, solidRectangle.y, solidRectangle.width, solidRectangle.height);
    }

    public void MagicBox() {
        gp.playSE(1);
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                health++;
                break;
            case 1:
                bombRad++;
                break;
            case 2:
                bombStr++;
                break;
        }
    }
}
