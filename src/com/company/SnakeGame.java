package com.company;

import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        board=new Board();
        add(board);
        pack();
        setVisible(false);
        setVisible(true);
    }

    public static void main(String[] args) {
	// write your code here
        SnakeGame snakeGame=new SnakeGame();
    }
}
