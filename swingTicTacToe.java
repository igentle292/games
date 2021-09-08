package games;

import java.awt.*;
import java.awt.event.*;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class swingTicTacToe extends JFrame{
    private final JLabel display;
    private final JLabel stats;
    private final JTextField[] tfBoard;
    private int p1Points = 0;
    private int p2Points = 0;
    private int games = 0;

    public swingTicTacToe(){
        Container cont = getContentPane();

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        display = new JLabel("Welcome to Tic Toe");
        p1.add(display);
        stats = new JLabel(p1Points + "-" + p2Points + " in " + games);
        p1.add(stats);
        JLabel rules = new JLabel("Play by setting O's and X's in squares and pressing enter!");
        p1.add(rules);
        p1.setSize(350, 75);
        p1.setVisible(true);
        cont.add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(3,3));
        tfBoard = new JTextField[9];
        for(int i=0; i<9; i++){
            tfBoard[i] = new JTextField("", 1);
            tfBoard[i].setEditable(true);
            tfBoard[i].addActionListener(new tfListener());
            p2.add(tfBoard[i]);
        }
        p2.setSize(350, 350);
        p2.setVisible(true);
        cont.add(p2);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new btnListener());
        p3.add(btnReset);
        p3.setSize(50, 50);
        p3.setVisible(true);
        cont.add(p3);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Swing Tic-Tac-Toe");
        setLayout(new GridLayout(3,1));
        setSize(350, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        new swingTicTacToe();
    }

    private static boolean checkVictoryP1(JTextField[] board){
        if(board[0].getText().equals("O") && board[0].getText().equals(board[1].getText()) && board[1].getText().equals(board[2].getText()))
            return true;
        if(board[3].getText().equals("O") && board[3].getText().equals(board[4].getText()) && board[4].getText().equals(board[5].getText()))
            return true;
        if(board[6].getText().equals("O") && board[6].getText().equals(board[7].getText()) && board[7].getText().equals(board[8].getText()))
            return true;
        if(board[0].getText().equals("O") && board[0].getText().equals(board[3].getText()) && board[3].getText().equals(board[6].getText()))
            return true;
        if(board[1].getText().equals("O") && board[1].getText().equals(board[4].getText()) && board[4].getText().equals(board[7].getText()))
            return true;
        if(board[2].getText().equals("O") && board[2].getText().equals(board[5].getText()) && board[5].getText().equals(board[8].getText()))
            return true;
        if(board[0].getText().equals("O") && board[0].getText().equals(board[4].getText()) && board[4].getText().equals(board[8].getText()))
            return true;
        return board[2].getText().equals("O") && board[2].getText().equals(board[4].getText()) && board[4].getText().equals(board[6].getText());
    }

    private static boolean checkVictoryP2(JTextField[] board){
        if(board[0].getText().equals("X") && board[0].getText().equals(board[1].getText()) && board[1].getText().equals(board[2].getText()))
            return true;
        if(board[3].getText().equals("X") && board[3].getText().equals(board[4].getText()) && board[4].getText().equals(board[5].getText()))
            return true;
        if(board[6].getText().equals("X") && board[6].getText().equals(board[7].getText()) && board[7].getText().equals(board[8].getText()))
            return true;
        if(board[0].getText().equals("X") && board[0].getText().equals(board[3].getText()) && board[3].getText().equals(board[6].getText()))
            return true;
        if(board[1].getText().equals("X") && board[1].getText().equals(board[4].getText()) && board[4].getText().equals(board[7].getText()))
            return true;
        if(board[2].getText().equals("X") && board[2].getText().equals(board[5].getText()) && board[5].getText().equals(board[8].getText()))
            return true;
        if(board[0].getText().equals("X") && board[0].getText().equals(board[4].getText()) && board[4].getText().equals(board[8].getText()))
            return true;
        return board[2].getText().equals("X") && board[2].getText().equals(board[4].getText()) && board[4].getText().equals(board[6].getText());
    }

    private static boolean checkIfFull(JTextField[] board){
        for(int i=0; i<9; i++){
            if(!(board[i].getText().equals("X") || board[i].getText().equals("O"))){
                return false;
            }
        }
        return true;
    }


    private class btnListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            for(int i=0; i<9; i++){
                tfBoard[i].setText("");
                tfBoard[i].setBackground(Color.WHITE);
                tfBoard[i].setEditable(true);
            }
            display.setText("Board Reset!");
        }
    }

    private class tfListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            JTextField source = (JTextField)evt.getSource();
            if(source.getText().equals("O")) {
                source.setEditable(false);
                source.setBackground(Color.RED);
                if (checkVictoryP1(tfBoard)) {
                    p1Points++;
                    games++;
                    for (int i = 0; i < 9; i++)
                        tfBoard[i].setEditable(false);
                    display.setText("Player 1 won!");
                    stats.setText(p1Points + "-" + p2Points + " in " + games);
                } else if (checkIfFull(tfBoard)) {
                    games++;
                    display.setText("Tie game!");
                    stats.setText(p1Points + "-" + p2Points + " in " + games);
                }
            }
            else if(source.getText().equals("X")){
                source.setEditable(false);
                source.setBackground(Color.BLUE);
                if (checkVictoryP2(tfBoard)) {
                    p2Points++;
                    games++;
                    for (int i = 0; i < 9; i++) {
                        tfBoard[i].setEditable(false);
                    }
                    display.setText("Player 2 won!");
                    stats.setText(p1Points + "-" + p2Points + " in " + games);
                } else if (checkIfFull(tfBoard)) {
                    games++;
                    display.setText("Tie game!");
                    stats.setText(p1Points + "-" + p2Points + " in " + games);
                }
            }
        }
    }
}
