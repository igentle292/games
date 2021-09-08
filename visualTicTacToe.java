package games;

import java.awt.Frame;
import java.awt.Button;
import java.awt.Panel;
import java.awt.Label;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.*;

public class visualTicTacToe extends Frame{
    private final Label display;
    private final Label stats;
    private final TextField[] tfBoard;
    private final Button btnReset;
    private int p1Points = 0;
    private int p2Points = 0;
    private int games = 0;

    public visualTicTacToe(){
        setLayout(new GridLayout(3,1));

        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());
        display = new Label("Welcome to Tic Toe");
        p1.add(display);
        stats = new Label(p1Points + "-" + p2Points + " in " + games);
        p1.add(stats);
        Label rules = new Label("Play by setting O's and X's in squares and pressing enter!");
        p1.add(rules);
        p1.setSize(350, 75);
        p1.setVisible(true);
        add(p1);

        Panel p2 = new Panel();
        p2.setLayout(new GridLayout(3,3));
        tfBoard = new TextField[9];
        for(int i=0; i<9; i++){
            tfBoard[i] = new TextField("", 1);
            tfBoard[i].setEditable(true);
            tfBoard[i].addActionListener(new tfListener());
            p2.add(tfBoard[i]);
        }
        p2.setSize(100, 100);
        p2.setVisible(true);
        add(p2);

        Panel p3 = new Panel();
        p3.setLayout(new FlowLayout());
        btnReset = new Button("Reset");
        btnReset.addActionListener(new btnListener());
        p3.add(btnReset);
        p3.setSize(50, 50);
        add(p3);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                System.exit(0);
            }
        });

        setTitle("Tic-Tac-Toe");
        setSize(350, 300);
        setVisible(true);


    }

    private static boolean checkVictoryP1(TextField[] board){
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

    private static boolean checkVictoryP2(TextField[] board){
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

    private static boolean checkIfFull(TextField[] board){
        for(int i=0; i<9; i++){
            if(!(board[i].getText().equals("X") || board[i].getText().equals("O"))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new visualTicTacToe();
    }

    private class btnListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            Button source = (Button)evt.getSource();
            if(source == btnReset){
                for(int i=0; i<9; i++){
                    tfBoard[i].setText("");
                    tfBoard[i].setEditable(true);
                }
                display.setText("Board Reset!");
            }
        }
    }

    private class tfListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            TextField source = (TextField)evt.getSource();
            if(source.getText().equals("X") || source.getText().equals("O")) {
                source.setEditable(false);
                if (checkVictoryP1(tfBoard)) {
                    p1Points++;
                    games++;
                    for (int i = 0; i < 9; i++) {
                        tfBoard[i].setEditable(false);
                    }
                    display.setText("Player 1 won!");
                    stats.setText(p1Points + "-" + p2Points + " in " + games);
                } else if (checkVictoryP2(tfBoard)) {
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
