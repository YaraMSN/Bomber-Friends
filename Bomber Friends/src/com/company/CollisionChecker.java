package com.company;

import Tile.TileManager;
import entity.MovingThings;
import entity.Player;

import java.io.Serializable;


public class CollisionChecker  {
    GamePanel gp;
    int tileSize=Main.tileSize;
    Integer[][] world;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
        world=gp.world.world;
    }

    public void CheckTile(MovingThings entity) {
        int entityLeftCol = entity.solidRectangle.x / tileSize;
        int entityRightCol = (entity.solidRectangle.x + entity.solidRectangle.width) / tileSize;
        int entityTopRow = entity.solidRectangle.y / tileSize;
        int entityBottomRow = (entity.solidRectangle.y + entity.solidRectangle.height) / tileSize;
        int prediction = 0;
        if (entity.direction == 'U' || entity.direction == 'D') {
            if (entity.direction == 'U') prediction = (entity.solidRectangle.y - entity.speed) / tileSize;
            else prediction = (entity.solidRectangle.y + entity.solidRectangle.height + entity.speed) / tileSize;
            int tileR = world[prediction][entityRightCol];
            int tileL = world[prediction][entityLeftCol];
            if (gp.tileManager.tile[tileL].collision || gp.tileManager.tile[tileR].collision) entity.collisionOn = true;
            else if (tileL == 6) {

                if (entity instanceof Player) {
                    ((Player) entity).MagicBox();
                    world[prediction][entityLeftCol] = 0;
                }
            } else if (tileR == 6) {

                if (entity instanceof Player) {
                    world[prediction][entityRightCol] = 0;
                    ((Player) entity).MagicBox();
                }
            }
        }

        if (entity.direction == 'L' || entity.direction == 'R') {
            if (entity.direction == 'L') prediction = (entity.solidRectangle.x - entity.speed) / tileSize;
            else prediction = (entity.solidRectangle.x + entity.solidRectangle.width + entity.speed) / tileSize;
            int tileT = world[entityTopRow][prediction];
            int tileB = world[entityBottomRow][prediction];
            if (gp.tileManager.tile[tileT].collision || gp.tileManager.tile[tileB].collision) entity.collisionOn = true;
            else if (tileT == 6) {
                if (entity instanceof Player) {
                    world[entityTopRow][prediction] = 0;
                    ((Player) entity).MagicBox();
                }
            } else if (tileB == 6) {

                if (entity instanceof Player) {
                    world[entityBottomRow][prediction] = 0;
                    ((Player) entity).MagicBox();
                }
            }
        }
    }

    public boolean CheckColi(MovingThings entity, int x, int y, int width, int height, int Ux, int Uy)//x and y as pixel AABB
    {
        int entityLeftCol = entity.solidRectangle.x + Ux;
        int entityRightCol = entity.solidRectangle.x + Ux + entity.solidRectangle.width;
        int entityTopRow = entity.solidRectangle.y + Uy;
        int entityBottomRow = entity.solidRectangle.y + Uy + entity.solidRectangle.height;
        if (x + width > entityLeftCol && x < entityLeftCol) {
            if (y + height > entityTopRow && y < entityTopRow) return true;
            if (y + height > entityBottomRow && y < entityBottomRow) return true;
        }
        if (x < entityRightCol && x + width > entityRightCol) {
            if (y + height > entityTopRow && y < entityTopRow) return true;
            if (y + height > entityBottomRow && y < entityBottomRow) return true;
        }
        return false;
    }

    public boolean CheckEntity(MovingThings entity1, MovingThings entity2) {
        return CheckColi(entity1, entity2.solidRectangle.x, entity2.solidRectangle.y, entity2.solidRectangle.width, entity2.solidRectangle.height, 0, 0);
    }

}
