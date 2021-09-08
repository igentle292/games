package games;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class visualSudokuPlayer extends JFrame{
    private JTextField[][] tfBoard;
    private int[][] solution;
    private int[][] puzzle;
    private final JLabel display;
    private final JButton btnReset;
    private final JButton btnCheckErrors;
    private final JButton btnHints;
    private final JPanel p2;
    private final Container cont;
    private final JComboBox<String> difficulty;
    private int emptySpaces = 35;

    private static JTextField[][] createTextFramePuzzle(int[][] puzzle){
        JTextField[][] textBoard = new JTextField[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(puzzle[i][j] != 0) {
                    textBoard[i][j] = new JTextField("", 1);
                    textBoard[i][j].setText(puzzle[i][j] + "");
                    textBoard[i][j].setEditable(false);
                    textBoard[i][j].setBackground(Color.YELLOW);
                }
                else {
                    textBoard[i][j] = new JTextField("", 1);
                    textBoard[i][j].setText("");
                    textBoard[i][j].setEditable(true);
                    textBoard[i][j].setBackground(Color.WHITE);
                }
            }
        }
        return textBoard;
    }

    private int[] findSource(JTextField text){
        int[] pair = new int[2];
        int[] empty = {-1, -1};
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(text == tfBoard[i][j]) {
                    pair[0] = i;
                    pair[1] = j;
                    return pair;
                }
            }
        }
        return empty;
    }

    public visualSudokuPlayer(){
        cont = getContentPane();
        setLayout(new GridLayout(3, 1));

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 1));
        display = new JLabel("Welcome to Sudoku!");
        p1.add(display);
        JLabel rules = new JLabel("Place each number 1-9 in each row, column, and 3x3 square without duplicates. Press ENTER each time you add a number!");
        p1.add(rules);
        p1.setSize(600, 75);
        p1.setVisible(true);
        cont.add(p1);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(9,9));
        sudokuGenerator gen = new sudokuGenerator();
        solution = gen.createSolvedBoard();
        puzzle = gen.createSolvablePuzzle(solution, emptySpaces);
        tfBoard = createTextFramePuzzle(puzzle);
        for(JTextField[] list : tfBoard){
            for(JTextField tf : list){
                p2.add(tf);
                tf.addActionListener(new tfListener());
            }
        }
        p2.setSize(600, 600);
        p2.setVisible(true);
        cont.add(p2);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        btnReset = new JButton("Reset");
        p3.add(btnReset);
        btnReset.addActionListener(new btnListener());
        btnCheckErrors = new JButton("Check for Errors");
        p3.add(btnCheckErrors);
        btnCheckErrors.addActionListener(new btnListener());
        btnHints = new JButton("Hint");
        p3.add(btnHints);
        btnHints.addActionListener(new btnListener());
        String[] difficulties = {"Easy", "Medium", "Hard", "Harder"};
        difficulty = new JComboBox<>(difficulties);
        difficulty.setSelectedIndex(0);
        difficulty.addActionListener(new cmbBxListener());
        p3.add(difficulty);
        p3.setSize(600, 75);
        p3.setVisible(true);
        cont.add(p3);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Swing Sudoku");
        setLayout(new GridLayout(3,1));
        setSize(600, 750);
        setVisible(true);
    }

    public static void main(String[] args) {
        new visualSudokuPlayer();
    }

    private class btnListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            JButton source = (JButton) evt.getSource();
            if(source == btnReset){
                sudokuGenerator gen = new sudokuGenerator();
                solution = gen.createSolvedBoard();
                puzzle = gen.createSolvablePuzzle(solution, emptySpaces);
                JTextField[][] newBoard = createTextFramePuzzle(puzzle);
                for(JTextField[] list : tfBoard){
                    for(JTextField text : list){
                        p2.remove(text);
                    }
                }
                for(JTextField[] list : newBoard){
                    for(JTextField text : list){
                        p2.add(text);
                        text.addActionListener(new tfListener());
                    }
                }
                tfBoard = newBoard;
                btnHints.setEnabled(true);
                display.setText("Board Reset!");
                p2.validate();
                cont.validate();
            }
            else if(source == btnCheckErrors){

                int counter = 0;
                for(int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        if((puzzle[i][j] != solution[i][j]) && (puzzle[i][j] != 0))
                            counter++;
                    }
                }
                if(counter != 0) {
                    int[][] locations = new int[counter][2];
                    counter = 0;
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if ((puzzle[i][j] != solution[i][j]) && (puzzle[i][j] != 0)) {
                                locations[counter][0] = i;
                                locations[counter][1] = j;
                                counter++;
                            }
                        }
                    }
                    for (int[] pair : locations) {
                        tfBoard[pair[0]][pair[1]].setBackground(Color.RED);
                    }
                    if(counter>1)
                        display.setText("There are " + counter + " errors!");
                    else if(counter == 1)
                        display.setText("There is 1 error!");
                } else
                    display.setText("There are no errors! Good Work!");
            }
            else{
                Random rand = new Random();
                boolean isValid = false;
                int i, j;
                while(!isValid){
                    i = rand.nextInt(9);
                    j = rand.nextInt(9);
                    if(puzzle[i][j] == 0 || puzzle[i][j] != solution[i][j]){
                        isValid = true;
                        puzzle[i][j] = solution[i][j];
                        tfBoard[i][j].setText("" + solution[i][j]);
                        tfBoard[i][j].setEditable(false);
                        tfBoard[i][j].setBackground(Color.BLUE);
                    }
                }
                display.setText("Hint granted!");
                sudokuPlayer play = new sudokuPlayer();
                if (!play.anyZeroes(puzzle)) {
                    if (play.checkVictory(puzzle, solution)) {
                        for (int x = 0; x < 9; x++) {
                            for (int y = 0; y < 9; y++) {
                                tfBoard[x][y].setEditable(false);
                                tfBoard[x][y].setBackground(Color.GREEN);
                                btnHints.setEnabled(false);
                                display.setText("You won! Great Job!");
                            }
                        }
                    }
                }
            }
        }
    }

    private class tfListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            JTextField source = (JTextField) evt.getSource();
            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                    if(tfBoard[i][j].isEditable() && (Color.RED == tfBoard[i][j].getBackground()))
                        tfBoard[i][j].setBackground(Color.WHITE);
                }
            }
//             && (Color.RED == tfBoard[i][j].getBackground())
            display.setText("Welcome to Sudoku!");
            int input;
            try{
                input = Integer.parseInt(source.getText());
            } catch(NumberFormatException e){
                source.setText("");
                input = 0;
            }
            if(0<=input && input<10){
                int[] location = findSource(source);
                puzzle[location[0]][location[1]] = input;
                if(input == 0){
                    source.setText("");
                } else {
                    source.setText(input + "");
                    sudokuPlayer play = new sudokuPlayer();
                    if (!play.anyZeroes(puzzle)) {
                        if (play.checkVictory(puzzle, solution)) {
                            for (int i = 0; i < 9; i++) {
                                for (int j = 0; j < 9; j++) {
                                    tfBoard[i][j].setEditable(false);
                                    tfBoard[i][j].setBackground(Color.GREEN);
                                    btnHints.setEnabled(false);
                                    display.setText("You won! Great Job!");
                                }
                            }
                        }
                    }
                }
            }
            else{
                source.setText("");
            }
        }
    }

    private class cmbBxListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            String newDiff = (String) difficulty.getSelectedItem();
            if(newDiff.equals("Easy")){
                emptySpaces = 35;
            } else if(newDiff.equals("Medium")){
                emptySpaces = 40;
            } else if(newDiff.equals("Hard")){
                emptySpaces = 45;
            } else if (newDiff.equals("Harder")){
                emptySpaces = 49;
            } else{
                emptySpaces = 35;
            }
        }
    }
}
