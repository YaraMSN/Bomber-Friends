package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static int myPort = 4224;
    public static final int tileSize = 48;
    public static int TileCol = 31;
    public static int TileRow = 19;
    public static int SCREEN_WIDTH=TileCol*tileSize;
    public static int SCREEN_HEIGHT=TileRow*tileSize;
    public static int Plearhealth=3;
    public static int WoodenBox;
    public static int MagicBox;

    public static void main(String[] args) {
        // write your code here
        JButton Play = new JButton("Play");
        JButton Setting = new JButton("Setting");
        JButton Host = new JButton("Host");
        JButton Join = new JButton("Join");
        JFrame frame = new JFrame();
        Font font = new Font(null, Font.PLAIN, 25);
        Play.setBounds(83, 60, 224, 60);
        Play.setFont(font);
        Play.addActionListener(e -> {
            SCREEN_WIDTH=TileCol*tileSize;
            SCREEN_HEIGHT=TileRow*tileSize;
            new GameFrame(null,false,false);
        });
        Play.setFocusable(false);

        Setting.setBounds(83, 130, 224, 60);
        Setting.setFont(font);
        Setting.addActionListener(e -> {
            new SettingMenu();
        });
        Setting.setFocusable(false);

        Host.setBounds(83, 200, 224, 60);
        Host.setFont(font);
        Host.addActionListener(e -> {
            try {
                ServerSocket serverSocket = new ServerSocket(myPort);
                Socket socket = serverSocket.accept();
                SCREEN_WIDTH=TileCol*tileSize;
                SCREEN_HEIGHT=TileRow*tileSize;
                new GameFrame(socket,true,true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Host.setFocusable(false);

        Join.setBounds(83, 270, 224, 60);
        Join.setFont(font);
        Join.addActionListener(e -> {
            try {
                Socket socket=new Socket("localhost",myPort);
                new GameFrame(socket,true,false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        Join.setFocusable(false);

        frame.add(Play);
        frame.add(Setting);
        frame.add(Host);
        frame.add(Join);

        frame.setTitle("Menu");
        frame.setLayout(null);
        frame.setSize(390, 470);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
