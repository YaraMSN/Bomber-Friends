package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

public class GameFrame extends JFrame {
    GameFrame(Socket socket, boolean isItOnline,boolean isServer) {
        setTitle("snake");
        GamePanel gp = new GamePanel(isItOnline);
        if (isItOnline)
            gp.setSocket(socket,isServer);
        add(gp);
        pack();
        setDefaultCloseOperation(GameFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                gp.stopMusic();
            }
        });
    }
}
