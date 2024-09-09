package entity;

import com.company.GamePanel;
import com.company.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Random;

import static com.company.Main.tileSize;
import static com.company.World.*;


public class Bomb extends Entity  {
    MovingThings owner;
    int radius;
    int damage;
    long PlantTime = 0;
    boolean isPlaced = false;
    transient BufferedImage[] explosion = new BufferedImage[4];
    Font font = new Font(null, Font.BOLD, 20);
    DecimalFormat format = new DecimalFormat("#0.00");
    ////
    World world;
    GamePanel gp;
    boolean isExploit = false;
    int fromCol, untilCol;
    int fromRow, untilRow;
    public long TimePast;

    public Bomb(int radius, int damage, int x, int y, MovingThings owner,GamePanel gp) {//X and y are in rectangle not in pixel
        this.gp=gp;
        this.radius = radius;
        this.damage = damage;
        this.x = x;
        this.y = y;
        this.owner = owner;
        world=gp.world;
        imageSetter();
    }

    public void imageSetter() {
        for (int i = 1; i <= 4; i++) {
            try {
                explosion[i - 1] = ImageIO.read(new File("Explosion" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bomb)) return false;
        Bomb bomb = (Bomb) o;
        return radius == bomb.radius && damage == bomb.damage && PlantTime == bomb.PlantTime  && isPlaced == bomb.isPlaced && owner.equals(bomb.owner) && font.equals(bomb.font) && format.equals(bomb.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, radius, damage, PlantTime, isPlaced, font, format);
    }
    public void updateTimer()
    {
        if (gp.GameState==gp.GameContinue)
            TimePast=System.nanoTime()-PlantTime;
        else
            PlantTime=System.nanoTime()-TimePast;
    }
    public boolean update(Graphics g) {
        if (!isExploit) {
            fromCol = y;
            untilCol = y;
            fromRow = x;
            untilRow = x;
            for (int i = 1; i < radius; i++)
                if (world.world[x - i][y] != 0 && world.world[x - i][y] != 6 && world.world[x - i][y] != 10) {
                    //decreaseHealth(x - i, y);
                    break;
                } else fromRow--;
            for (int i = 1; i < radius; i++)
                if (world.world[x + i][y] != 0 && world.world[x + i][y] != 6 && world.world[x + i][y] != 10) {
                    //  decreaseHealth(x + i, y);
                    break;
                } else untilRow++;
            for (int i = 1; i < radius; i++)
                if (world.world[x][y - i] != 0 && world.world[x][y - i] != 6 && world.world[x][y - i] != 10) {
                    //  decreaseHealth(x, y - i);
                    break;
                } else fromCol--;
            for (int i = 1; i < radius; i++)
                if (world.world[x][y + i] != 0 && world.world[x][y + i] != 6 && world.world[x][y + i] != 10) {
                    //   decreaseHealth(x, y + i);
                    break;
                } else untilCol++;
        }
        /////////////////////

        if (!owner.gp.checker.CheckColi(owner, y * tileSize, x * tileSize, tileSize, tileSize, 0, 0)) {
            if (PlantTime == 0) PlantTime = System.nanoTime();
            updateTimer();
            if (!isPlaced) {
                world.world[x][y] = 10;
                isPlaced = true;
            }

            if (!isExploit && TimePast > 3e9) {//TODO make this a variable
                explode();
                isExploit = true;
            }

            if (isExploit && TimePast < 3e9 + 5e8) {
                drawExplosion(g);
                gp.playSE(3);
            }
            if (isExploit && TimePast > 3e9 + 125e6) return true;
        }
        if (!isExploit && TimePast < 3e9) drawTimer(g);
        return false;
    }

    public void decreaseHealth(int x, int y) {
        if (world.world[x][y] > 2 && world.world[x][y] != 6 && world.world[x][y] != 10) {
            if (world.world[x][y] > 2 && world.world[x][y] < 6) world.world[x][y] = 0;
            else {
                world.world[x][y]--;
            }
        }
        ///////////////////

        /////
    }

    public void explode() {
        world.world[x][y] = 0;
        fromCol = y;
        untilCol = y;
        fromRow = x;
        untilRow = x;
        for (int i = 1; i < radius; i++)
            if (world.world[x - i][y] != 0 && world.world[x - i][y] != 6 && world.world[x - i][y] != 10) {
                decreaseHealth(x - i, y);
                break;
            } else fromRow--;
        for (int i = 1; i < radius; i++)
            if (world.world[x + i][y] != 0 && world.world[x + i][y] != 6 && world.world[x + i][y] != 10) {
                decreaseHealth(x + i, y);
                break;
            } else untilRow++;
        for (int i = 1; i < radius; i++)
            if (world.world[x][y - i] != 0 && world.world[x][y - i] != 6 && world.world[x][y - i] != 10) {
                decreaseHealth(x, y - i);
                break;
            } else fromCol--;
        for (int i = 1; i < radius; i++)
            if (world.world[x][y + i] != 0 && world.world[x][y + i] != 6 && world.world[x][y + i] != 10) {
                decreaseHealth(x, y + i);
                break;
            } else untilCol++;
        //CheckColi(MovingThings entity, int x, int y, int width, int height, int Ux, int Uy)
        checkForPuttingDamage(owner.gp.player);
        if (owner instanceof Player)
            for (MovingThings monster:world.Monsters)
                checkForPuttingDamage(monster);
    }

    public void checkForPuttingDamage(MovingThings thing) {
        if (owner.gp.checker.CheckColi(thing, y * tileSize, fromRow * tileSize, tileSize, (untilRow - fromRow + 1) * tileSize, 0, 0)) {//TODO:any entity
            thing.health -= damage;
        }
        if (owner.gp.checker.CheckColi(thing, fromCol * tileSize, x * tileSize, (untilCol - fromCol + 1) * tileSize, tileSize, 0, 0))
            thing.health -= damage;
    }

    public void drawTimer(Graphics g) {
        if (!owner.gp.checker.CheckColi(owner, y * tileSize, x * tileSize, tileSize, tileSize, 0, 0)) {
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(format.format((3e9 - TimePast)/ 1e9), y * tileSize, x * tileSize);
        }
        g.setColor(Color.red);
        g.drawRect(y * tileSize, fromRow * tileSize, tileSize, (untilRow - fromRow + 1) * tileSize);
        g.drawRect(fromCol * tileSize, x * tileSize, (untilCol - fromCol + 1) * tileSize, tileSize);
    }

    public void drawExplosion(Graphics g) {
        Random random = new Random();
        for (int i = x; i >= fromRow; i--)
            g.drawImage(explosion[random.nextInt(4)], y * tileSize, i * tileSize, tileSize, tileSize, null);
        for (int i = x; i <= untilRow; i++)
            g.drawImage(explosion[random.nextInt(4)], y * tileSize, i * tileSize, tileSize, tileSize, null);
        for (int i = y; i >= fromCol; i--)
            g.drawImage(explosion[random.nextInt(4)], i * tileSize, x * tileSize, tileSize, tileSize, null);
        for (int i = y; i <= untilCol; i++)
            g.drawImage(explosion[random.nextInt(4)], i * tileSize, x * tileSize, tileSize, tileSize, null);
        // for (int i = y; i >=fromCol;i--)


    }
}
