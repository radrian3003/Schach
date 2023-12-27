package com.company;

import java.awt.*;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardGui extends JFrame {
    //wie viele felder pro zeile&spalte
    int numberOfSquares = 8;
    int squareSize = 60;

    //braucht man das?
    private final JLabel[][] squares = new JLabel[numberOfSquares][numberOfSquares];
    private final String[][] pieces = {
            {"r", "n", "b", "q", "k", "b", "n", "r"},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"R", "N", "B", "Q", "K", "B", "N", "R"}
    };
    JPanel boardPanel = createBoardPanel();

    private int selectedRow = -1;
    private int selectedCol = -1;

    public BoardGui(){
    setTitle("Board GUI");
    setSize(numberOfSquares * squareSize, numberOfSquares * squareSize);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    add(boardPanel);
    setVisible(true);
    }

    //Methode abgeschrieben das glaubt keiner dass wir das selber gemacht haben
    private JPanel createBoardPanel() {
        JPanel pbp = new JPanel(new GridLayout(numberOfSquares, numberOfSquares));

        for (int row = 0; row < numberOfSquares; row++) {
            for (int column = 0; column < numberOfSquares; column++) {
                JLabel squareLabel = new JLabel(pieces[row][column], SwingConstants.CENTER);
                squareLabel.setOpaque(true);
                squareLabel.setBackground((row + column) % 2 == 0 ? Color.pink : Color.darkGray);
                squareLabel.setForeground((row + column) % 2 == 0 ? Color.darkGray : Color.pink);
                squareLabel.setPreferredSize(new Dimension(squareSize, squareSize));
                squareLabel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

                // Add mouse listener for piece movement
                int goalRow = row;
                int goalColumn = column;
                squareLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        goalSquareClickHandling(goalRow, goalColumn);
                    }
                });

                squares[row][column] = squareLabel;
                pbp.add(squareLabel);
            }
        }

        return pbp;
    }

    //Methode kopiert
    private void goalSquareClickHandling(int row, int column) {
        if (selectedRow == -1 && !pieces[row][column].isEmpty()) {
            // Select the piece
            selectedRow = row;
            selectedCol = column;
        } else if (selectedRow != -1) {
            // Move the selected piece to the new position
            pieces[row][column] = pieces[selectedRow][selectedCol];
            pieces[selectedRow][selectedCol] = "";
            squares[row][column].setText(pieces[row][column]);
            squares[selectedRow][selectedCol].setText("");

            // Clear selection
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    public static void main(String[] args) {
        BoardGui bg = new BoardGui();
    }

}
