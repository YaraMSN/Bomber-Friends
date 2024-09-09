package entity;

import com.company.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import static com.company.Main.*;

public class Skeleton extends MovingThings  {

    public Skeleton(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
    }

    @Override
    void setDefaultValues() {
        speed = 4;

        Random random = new Random();
        do {
            x = random.nextInt(TileCol / 2);
            y = random.nextInt(TileRow);
        } while (gp.world.world[y][x] != 0);
        x *= tileSize;
        y *= tileSize;
        health = 3;
        solidRectangle = new Rectangle();
        solidRectangle.x = x + 8;
        solidRectangle.y = y + 5;
        solidRectangle.width = tileSize - 15;
        solidRectangle.height = tileSize - 15;
        setImages();
    }

    @Override
    void setImages() {
        try {
            left = new BufferedImage[8];
            for (int i = 0; i < 8; i++)
                left[i] = ImageIO.read(new File("walk_left000" + i + ".png"));
            right = new BufferedImage[8];
            for (int i = 0; i < 8; i++)
                right[i] = ImageIO.read(new File("walk_right000" + i + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update() {
        if (health > 0) {//avg : 0.0092
            frameCounter++;
            if (frameCounter > 38) {
                Random random = new Random();
                int D = random.nextInt(120);
                collisionOn = false;
                if (D < 30) {
                    direction = 'U';
                } else if (D < 60) {
                    direction = 'D';
                } else if (D < 90) {
                    direction = 'L';
                } else {
                    direction = 'R';
                }
            }

            // System.out.println(direction);
            gp.checker.CheckTile(this);
            if (!collisionOn) {
                switch (direction) {
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
                }
            }
            imageMode = frameCounter / 5;
            if (frameCounter > 38) frameCounter = 0;

        }
    }


    @Override
    public void Draw(Graphics g) {
        BufferedImage image = null;
        switch (direction) {
            case 'U':
            case 'L':
                image = left[imageMode];
                break;
            case 'D':
            case 'R':
                image = right[imageMode];
                break;
            default:
                image = left[0];
                ;
        }
        double polo = ((double) health / 3);
        double green = 255 * polo;
        double red = 255 * (1 - polo);
        g.setColor(new Color((int) red, (int) green, 0));
        g.drawLine(x, y, x + (int) (tileSize * polo), y);
        g.drawString(String.valueOf(health), x, y);
        g.drawImage(image, x, y, tileSize, tileSize, null);
        g.setColor(Color.white);
        g.drawRect(solidRectangle.x, solidRectangle.y, solidRectangle.width, solidRectangle.height);
    }
}
