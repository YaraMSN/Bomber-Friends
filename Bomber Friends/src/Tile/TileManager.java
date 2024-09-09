package Tile;

import com.company.GamePanel;
import com.company.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

import static com.company.Main.*;

public class TileManager  {
    public Tile[] tile;
    int numOfTiles = 11;
    Integer[][] ground;
    public TileManager( Integer[][] playground) {
        tile = new Tile[numOfTiles];
        for (int i = 0; i < numOfTiles; i++)
            tile[i] = new Tile();
        getTileImage();
        ground = playground;

    }

    public void getTileImage() {
        try {
            tile[0].image = ImageIO.read(new File("grass.png"));
            tile[1].image = ImageIO.read(new File("wall-1.png"));
            tile[2].image = ImageIO.read(new File("wall-2.png"));
            //==

            tile[3].image = ImageIO.read(new File("CrateWithMetal-1.png"));
            tile[4].image = ImageIO.read(new File("CrateWithMetal-2.png"));
            tile[5].image = ImageIO.read(new File("CrateWithMetal-3.png"));
            //==
            tile[6].image = ImageIO.read(new File("MagicBox.png"));
            tile[7].image = ImageIO.read(new File("BronzeCrate.png"));
            tile[8].image = ImageIO.read(new File("SilverCrate.png"));
            tile[9].image = ImageIO.read(new File("GoldCrate.png"));
            //==
            tile[10].image = ImageIO.read(new File("Bomb (1).png"));

            tile[0].collision = false;
            tile[6].collision = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < TileRow; i++)
            for (int j = 0; j < TileCol; j++) {
                g.drawImage(tile[0].image, j * tileSize, i * tileSize, tileSize, tileSize, null);
                g.drawImage(tile[ground[i][j]].image, j * tileSize, i * tileSize, tileSize, tileSize, null);
            }
    }
}
