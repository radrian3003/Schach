package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverallGui extends JFrame implements ActionListener {

    JButton bVsBot;

    public OverallGui(){
        super("Schachprogramm");
        setSize(500,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        bVsBot = new JButton("vs. Bot");
        bVsBot.setBounds(100,100,100,30);
        bVsBot.addActionListener(this);
        add(bVsBot);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Add your code here for vs Bot functionality
        System.out.println("vs Bot button clicked!");
    }

    public static void main(String[] args) {

    }
}
