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

public class Slime extends MovingThings  {

    public Slime(GamePanel gp) {
        this.gp = gp;
        setDefaultValues();
    }

    @Override
    void setDefaultValues() {
        Random random = new Random();
        do {
            x = random.nextInt(TileCol / 2);
            y = random.nextInt(TileRow);
        } while (gp.world.world[y][x] != 0);
        x *= tileSize;
        y *= tileSize;
        speed = 7;
        health = 1;
        solidRectangle = new Rectangle();
        solidRectangle.x = x;
        solidRectangle.y = y;
        solidRectangle.width = tileSize - 15;
        solidRectangle.height = tileSize - 15;
        //System.out.println("X : "+solidRectangle.x/tileSize + " Y : "+solidRectangle.y/tileSize+ "  "+ world[solidRectangle.x/tileSize][solidRectangle.y/tileSize]);
        setImages();
    }

    @Override
    void setImages() {
        up = new BufferedImage[2];
        try {
            up[0] = ImageIO.read(new File("Slime (1).png"));
            up[1] = ImageIO.read(new File("Slime (2).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (health > 0) {
            frameCounter++;
            if (frameCounter > 28) {

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
            imageMode = frameCounter / 15;
            if (frameCounter > 28) frameCounter = 0;
        }
    }


    @Override
    public void Draw(Graphics g) {
        BufferedImage image = up[imageMode];
        g.drawImage(image, (int) (x), (int) (y), (int) (tileSize * 0.8), (int) (tileSize * 0.8), null);
        g.setColor(Color.green);
        g.drawString(String.valueOf(direction), x, y);
        g.setColor(Color.red);
        g.drawRect(solidRectangle.x, solidRectangle.y, solidRectangle.width, solidRectangle.height);
    }
}
