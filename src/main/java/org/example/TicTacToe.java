package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener {

    private final Random random = new Random();
    private final JFrame frame = new JFrame();
    private final JPanel titlePanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JLabel textfield = new JLabel();
    private final JButton[] buttonsArray = new JButton[9];
    private boolean player1Turn;

    public TicTacToe() {
        setupFrame();
        setupTitlePanel();
        setupButtonPanel();
        firstTurn();
    }

    private void setupFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }

    private void setupTitlePanel() {
        titlePanel.setBackground(new Color(192, 192, 192));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        textfield.setBackground(new Color(192, 192, 192));
        textfield.setForeground(Color.BLACK);
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        titlePanel.add(textfield);
        frame.add(titlePanel, BorderLayout.NORTH);
    }

    private void setupButtonPanel() {
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(Color.BLACK);

        for (int i = 0; i < 9; i++) {
            buttonsArray[i] = new JButton();
            buttonPanel.add(buttonsArray[i]);
            buttonsArray[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttonsArray[i].setFocusable(false);
            buttonsArray[i].addActionListener(this);
            buttonsArray[i].setBackground(Color.BLACK);
            buttonsArray[i].setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192)));
        }

        frame.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttonsArray[i]) {
                handleButtonClick(i);
                check();
                break;
            }
        }
    }

    private void handleButtonClick(int i) {
        if (buttonsArray[i].getText().equals("")) {
            if (player1Turn) {
                buttonsArray[i].setForeground(new Color(118, 215, 234));
                buttonsArray[i].setText("X");
                textfield.setText("O");
            } else {
                buttonsArray[i].setForeground(new Color(255, 0, 204));
                buttonsArray[i].setText("O");
                textfield.setText("X");
            }
            player1Turn = !player1Turn;
        }
    }

    public void firstTurn() {
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (random.nextInt(2) == 0) {
                    player1Turn = true;
                    textfield.setText("X");
                    textfield.setForeground(Color.RED);
                } else {
                    player1Turn = false;
                    textfield.setText("O");
                    textfield.setForeground(Color.BLUE);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void check() {
        String[][] patterns = {
                {"0", "1", "2"}, {"3", "4", "5"}, {"6", "7", "8"},
                {"0", "3", "6"}, {"1", "4", "7"}, {"2", "5", "8"},
                {"0", "4", "8"}, {"2", "4", "6"} // diagonal
        };

        for (String[] pattern : patterns) {
            int a = Integer.parseInt(pattern[0]);
            int b = Integer.parseInt(pattern[1]);
            int c = Integer.parseInt(pattern[2]);

            if (buttonsArray[a].getText().equals("X") && buttonsArray[b].getText().equals("X") && buttonsArray[c].getText().equals("X")) {
                xWins(a, b, c);
                return;
            }
            if (buttonsArray[a].getText().equals("O") && buttonsArray[b].getText().equals("O") && buttonsArray[c].getText().equals("O")) {
                oWins(a, b, c);
                return;
            }
        }
    }

    public void xWins(int a, int b, int c) {
        setWinningColors(a, b, c);
        textfield.setText("Hurra, X wins!");
    }

    public void oWins(int a, int b, int c) {
        setWinningColors(a, b, c);
        textfield.setText("Hurra, O wins!");
    }

    private void setWinningColors(int a, int b, int c) {
        Color winningColor = new Color(135, 255, 42);
        buttonsArray[a].setBackground(winningColor);
        buttonsArray[b].setBackground(winningColor);
        buttonsArray[c].setBackground(winningColor);

        buttonsArray[a].setOpaque(true);
        buttonsArray[b].setOpaque(true);
        buttonsArray[c].setOpaque(true);

        buttonsArray[a].repaint();
        buttonsArray[b].repaint();
        buttonsArray[c].repaint();

        for (JButton button : buttonsArray) {
            button.setEnabled(false);
        }
    }
}
