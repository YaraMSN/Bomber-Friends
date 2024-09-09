package entity;

import com.company.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class MovingThings extends Entity  {
    transient public GamePanel gp;
    transient public BufferedImage[] up;
    transient public BufferedImage[] down;
    transient public BufferedImage[] left;
    transient public BufferedImage[] right;
    transient public BufferedImage idle;
    transient public int speed;
    transient public int frameCounter=0;
    public int imageMode=0;
    public char direction;
    transient public Rectangle solidRectangle;
    transient public boolean collisionOn=false;
    transient public int health;
    abstract void setDefaultValues();
    abstract void setImages();
    abstract public void update();
    abstract public void Draw(Graphics g);
}
