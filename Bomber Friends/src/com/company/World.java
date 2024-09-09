package com.company;


import entity.Bomb;
import entity.MovingThings;

import java.util.Random;
import java.util.Vector;

public class World {
    public Integer[][] world;
    public Vector<MovingThings> Monsters = new Vector<>();
    public Vector<Bomb> bombs = new Vector<Bomb>();
    int TileCol = Main.TileCol;
    int TileRow = Main.TileRow;
    int numOfWoodenBoxes = 50;
    int numOfMagicBoxes = 20;

    public World() {
        world = new Integer[TileRow][TileCol];
        for (int i = 0; i < TileRow; i++)
            for (int j = 0; j < TileCol; j++)
                world[i][j] = 0;
        for (int i = 0; i < TileRow; i++)
            for (int j = 0; j < TileCol; j++) {
                if (i == 0 || i == TileRow - 1 || j == 0 || j == TileCol - 1) world[i][j] = 1;//margin
                if (2 * i + 2 < TileRow && 2 * j + 2 < TileCol) world[2 * i + 2][2 * j + 2] = 2;//stones between
            }

        Random random = new Random();
        while (numOfWoodenBoxes > 0) {
            int x = random.nextInt(TileRow);
            int y = random.nextInt(TileCol);
            int type = random.nextInt(3);
            System.out.println(x + "   " + y);
            if (world[x][y] == 0 && (x > 3 || y < TileCol - 4)) {//Placing WoodenCrate with giving some space to player
                numOfWoodenBoxes--;
                world[x][y] = type + 3;
            }
        }
        while (numOfMagicBoxes > 0) {
            int x = random.nextInt(TileRow);
            int y = random.nextInt(TileCol);
            if (world[x][y] == 0 && (x > 3 || y < TileCol - 4)) {//Placing MagicCrate with giving some space to player
                numOfMagicBoxes--;
                world[x][y] = 9;
            }
        }
    }

}
