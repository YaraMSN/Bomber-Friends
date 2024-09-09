package com.company;

import javax.swing.*;
import java.awt.*;

//* Dear code reviewer I am so ashamed of my code. i know its the worst code ever. im so ashamed of my code but it is what is ....
public class SettingMenu extends JFrame {
    SpinnerModel value1 = new SpinnerNumberModel(5, 0, 10, 1);
    SpinnerModel value2 = new SpinnerNumberModel(3, 0, 10, 1);
    SpinnerModel value3 = new SpinnerNumberModel(1, 0, 10, 1);
    SpinnerModel value4 = new SpinnerNumberModel(Main.TileRow, 10, 22, 1);
    SpinnerModel value5 = new SpinnerNumberModel(Main.TileCol, 15, 40, 1);
    SpinnerModel value6 = new SpinnerNumberModel(Main.Plearhealth, 1, 10, 1);
    SpinnerModel value7 = new SpinnerNumberModel(Main.WoodenBox, 0, 10, 1);
    SpinnerModel value8 = new SpinnerNumberModel(Main.MagicBox, 0, 10, 1);
    JSpinner spinner1 = new JSpinner(value1);
    JSpinner spinner2 = new JSpinner(value2);
    JSpinner spinner3 = new JSpinner(value3);
    JSpinner spinner4 = new JSpinner(value4);
    JSpinner spinner5 = new JSpinner(value5);
    JSpinner spinner6 = new JSpinner(value6);

    JLabel Label1 = new JLabel("Slime : ");
    JLabel Label2 = new JLabel("Skeleton : ");
    JLabel Label3 = new JLabel("Bomberman : ");
    JLabel Label4 = new JLabel("Height : ");
    JLabel Label5 = new JLabel("Width : ");
    JLabel Label6 = new JLabel("Health : ");
    Font font = new Font(null, Font.PLAIN, 25);

    SettingMenu() {

        Label1.setFont(font);
        Label1.setBounds(25, 50, 250, 30);
        spinner1.setFont(font);
        spinner1.setBounds(275, 50, 60, 30);
        spinner1.addChangeListener(e -> GamePanel.NumOfSlims = (int) ((JSpinner) e.getSource()).getValue());
        Label2.setFont(font);
        Label2.setBounds(25, 100, 250, 30);
        spinner2.setFont(font);
        spinner2.setBounds(275, 100, 60, 30);
        spinner2.addChangeListener(e -> GamePanel.NumOfSkeletons = (int) ((JSpinner) e.getSource()).getValue());
        Label3.setFont(font);
        Label3.setBounds(25, 150, 250, 30);
        spinner3.setFont(font);
        spinner3.setBounds(275, 150, 60, 30);
        spinner3.addChangeListener(e -> GamePanel.NumOfBomberman = (int) ((JSpinner) e.getSource()).getValue());
        Label4.setFont(font);
        Label4.setBounds(25, 200, 250, 30);
        spinner4.setFont(font);
        spinner4.setBounds(275, 200, 60, 30);
        spinner4.addChangeListener(e -> Main.TileRow = (int) ((JSpinner) e.getSource()).getValue());
        Label5.setFont(font);
        Label5.setBounds(25, 250, 250, 30);
        spinner5.setFont(font);
        spinner5.setBounds(275, 250, 60, 30);
        spinner5.addChangeListener(e -> Main.TileCol = (int) ((JSpinner) e.getSource()).getValue());
        Label6.setFont(font);
        Label6.setBounds(25, 300, 250, 30);
        spinner6.setFont(font);
        spinner6.setBounds(275, 300, 60, 30);
        spinner6.addChangeListener(e -> Main.TileCol = (int) ((JSpinner) e.getSource()).getValue());
        add(spinner1);
        add(Label1);
        add(spinner2);
        add(Label2);
        add(spinner3);
        add(spinner4);
        add(spinner5);
        add(Label3);
        add(Label4);
        add(Label5);
        add(Label6);
        add(spinner6);
        setLayout(null);
        setSize(390, 470);
        setLocationRelativeTo(null);
        setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

}
