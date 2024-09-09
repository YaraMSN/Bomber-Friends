package com.company;

import Tile.TileManager;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import static com.company.Main.*;

public class GamePanel extends JPanel  {

    //Enemy for beginning of the game
    public static int NumOfSlims = 5;
    public static int NumOfSkeletons = 3;
    public static int NumOfBomberman = 1;
    //
    //States of game
    public final int GameContinue = 1;
    public final int GamePause = 2;
    public final int WinState = 3;
    public final int LostState = 4;
    public int GameState = 1;
    ///
    long drawInterval = 1000000000 / 60;
    //Tools
    UI ui = new UI(this);
    Sound sound = new Sound();
    Sound ThemeMusic=new Sound();
    TileManager tileManager;
    KeyHandler handler = new KeyHandler(this);
    // Game things
    public CollisionChecker checker;
    public World world;
    public Player player = new Player(this, handler);
    //Internet things
    boolean isItOnline = false;
    Socket socket;
    boolean isServer;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    public GamePanel(boolean isItOnline) {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(handler);
        if (!isItOnline) StartGame();
        this.isItOnline = isItOnline;
    }

    public void setSocket(Socket socket, boolean isServer) {
        this.isServer = isServer;
        this.socket = socket;
        try {
            if (isServer) {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
            } else {
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                drawInterval=1000000000/90;
            }
            if (isServer) StartGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StartGame() {
        world = new World();
        tileManager = new TileManager(world.world);
        checker = new CollisionChecker(this);
        for (int i = 0; i < NumOfSlims; i++)
            world.Monsters.add(new Slime(this));
        for (int i = 0; i < NumOfSkeletons; i++)
            world.Monsters.add(new Skeleton(this));
        for (int i = 0; i < NumOfBomberman; i++)
            world.Monsters.add(new BomberMan(this));
        playMusic(0);
    }

    public void update() {
        Vector<MovingThings> delete = new Vector<>();
        if (GameState == GameContinue) {
            player.update();
            for (MovingThings monster : world.Monsters) {
                if (checker.CheckEntity(player, monster)) {
                    delete.add(monster);
                    player.health--;
                } else {
                    monster.update();
                    if (monster.health < 1) delete.add(monster);
                }
            }

            world.Monsters.removeAll(delete);
            if (world.Monsters.size() == 0)
            {
                GameState = WinState;
                stopMusic();
                playMusic(4);
            }
            else if (player.health < 1) {
                GameState = LostState;
                stopMusic();
                playMusic(4);
            }
        } else if (GameState == GamePause) {
            for (Bomb bomb : world.bombs)
                bomb.updateTimer();
        }
    }

    public void UpdateFromInternet() {
        try {
            tileManager=new TileManager((Integer[][]) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    int FrameCounter=0;
    public void paintComponent(Graphics g) {

        long YaraStart = System.nanoTime();
        if (!isServer && isItOnline) UpdateFromInternet();
        else update();
        FrameCounter++;

        if (GameState == GameContinue) {
            super.paintComponent(g);
            if (!isServer && isItOnline)
                tileManager.draw(g);
            else {
                tileManager.draw(g);
                ui.showPlayer(g);

                for (MovingThings thing : world.Monsters)
                    thing.Draw(g);
                player.Draw(g);
                world.bombs.removeIf(x -> x.update(g));
            }
        } else{
            ui.ShowState(g);
        }

        while (drawInterval-System.nanoTime() + YaraStart>0)
        {
            if (isServer && FrameCounter==30) {
                try {
                    long startOutput=System.nanoTime();
                    outputStream.reset();
                    outputStream.writeUnshared(world.world);
                    outputStream.flush();
                    System.out.println((System.nanoTime() - startOutput)/1e9);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FrameCounter=0;
            }
        }

//        if (drawInterval - System.nanoTime() + YaraStart > 0) {
//            try {
//                Thread.sleep((long) ((drawInterval - System.nanoTime() + YaraStart) / 1e6));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        ///////////////////

        repaint();
    }

    public void playMusic(int i) {
        ThemeMusic.setFile(i);
        ThemeMusic.loop();
        ThemeMusic.play();

    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void stopMusic() {
        ThemeMusic.stop();
    }
}
