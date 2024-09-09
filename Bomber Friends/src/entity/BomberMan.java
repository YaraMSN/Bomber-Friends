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

public class BomberMan extends MovingThings  {
    long lastDrop = 0;

    public BomberMan(GamePanel gp) {
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
        speed = 3;
        health = 1;
        solidRectangle = new Rectangle();
        solidRectangle.x = x;
        solidRectangle.y = y;
        solidRectangle.width = tileSize - 15;
        solidRectangle.height = tileSize - 15;
        setImages();
    }

    @Override
    void setImages() {
        left = new BufferedImage[2];
        right = new BufferedImage[2];
        up = new BufferedImage[2];
        down = new BufferedImage[2];
        try {
            left[0] = ImageIO.read(new File("oldman_left_1.png"));
            left[1] = ImageIO.read(new File("oldman_left_2.png"));

            right[0] = ImageIO.read(new File("oldman_right_1.png"));
            right[1] = ImageIO.read(new File("oldman_right_2.png"));

            down[0] = ImageIO.read(new File("oldman_down_1.png"));
            down[1] = ImageIO.read(new File("oldman_down_2.png"));

            up[0] = ImageIO.read(new File("oldman_up_1.png"));
            up[1] = ImageIO.read(new File("oldman_up_2.png"));
            idle = up[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (health > 0) {

            frameCounter++;
            if (frameCounter > 38) {
                Random random = new Random();
                int D = random.nextInt(200);
                collisionOn = false;
                if (D < 45) {
                    direction = 'U';
                } else if (D < 90) {
                    direction = 'D';
                } else if (D < 135) {
                    direction = 'L';
                } else if (D < 180) {
                    direction = 'R';
                } else direction = 'S';
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
                    case 'S': {
                        frameCounter = 38;
                        if (System.nanoTime() - lastDrop > 5e9) {
                            Bomb bomb = new Bomb(5, 4, solidRectangle.y / tileSize, solidRectangle.x / tileSize, this, gp);
                            if (!gp.world.bombs.contains(bomb)) {
                                gp.playSE(2);
                                gp.world.bombs.add(bomb);
                            }
                            lastDrop = System.nanoTime();
                        }
                        break;
                    }
                }
            }
            imageMode = frameCounter / 20;
            if (frameCounter > 38) frameCounter = 0;

        }
    }

    @Override
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
        g.drawImage(image, x, y, tileSize - 12, tileSize - 12, null);
        g.setColor(Color.white);
        g.drawRect(solidRectangle.x, solidRectangle.y, solidRectangle.width, solidRectangle.height);

    }
}
