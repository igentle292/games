package games;

import java.util.Random;
import java.util.LinkedList;

public class sudokuSolver {
    private static boolean checkRowCol(int[][] board, int row, int column){
        /*
            Checks each value in the cell's row and column, returning False if a duplicate is found, and true if it is
            unique
         */
        for(int i=0; i<9; i++){
            if(i != column) {
                if (board[row][i] == board[row][column])
                    return false;
            }
        }
        for(int j=0; j<9; j++){
            if(j != row){
                if(board[j][column] == board[row][column])
                    return false;
            }
        }
        return true;
    }

    private static boolean checkSquare(int[][] board, int row, int col){
        /*
            Checks each value in the cell's square, returning false if a duplicate is found, and true if it is unique
         */
        for(int i=(row/3)*3; i<(row/3)*3+3; i++){
            for(int j=(col/3)*3; j<(col/3)*3+3; j++){
                if(!(i == row && j == col)) {
                    if (board[i][j] == board[row][col])
                        return false;
                }
            }
        }
        return true;

    }

    private static boolean guessAndCheck(int[][] board, int row, int col, LinkedList<Integer> lList){
        /*
            Randomly guesses for a cell before checking if it is valid, and recursively calling itself for the next cell
            in the row. Recurs at the end of each row, and ends once the row to be checked is out of range
        */
        if(row>=board.length)
            return true;
        if(col>=board[row].length) {
            LinkedList<Integer> rList = generateRandomList();
            return guessAndCheck(board, row + 1, 0, rList);
        }
        if(board[row][col] == 0){
            for(int k=0; k<lList.size(); k++){      //Iterates through each value in the linked list, guessing and checking
                board[row][col] = lList.get(k);     //before guessing and checking in the next cell
                if(checkRowCol(board, row, col) && checkSquare(board, row, col)){
                    if(guessAndCheck(board, row, col+1, lList))
                        return true;
                }
                board[row][col] = 0;                //Resets the cell if a prior guess was invalid, probably unnecessary
            }
            return false;
        }
        else{
            for(int k=0; k<lList.size(); k++){          //Removes a value from the linked list if it already existed in
                if(lList.get(k) == board[row][col]){    //the row, before guessing and checking the next cell
                    lList.remove(k);
                }
            }
            return guessAndCheck(board, row, col+1, lList);
        }
    }

    private static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private static LinkedList<Integer> generateRandomList(){
        Random rand = new Random();
        int[] list = {1,2,3,4,5,6,7,8,9};
        LinkedList<Integer> lList = new LinkedList<>();
        for(int i=0; i<8; i++){
            swap(list, i, rand.nextInt(9-i)+i);
        }
        for (int num : list) {
            lList.add(num);
        }
        return lList;
    }

    protected static void solvePuzzle(int[][] board){
        LinkedList<Integer> lList = generateRandomList();
        if(!guessAndCheck(board, 0, 0, lList)){}
    }

    public static void main(String[] args) {
        int[] row1 = {0,0,0,0,0,0,3,0,7};
        int[] row2 = {0,2,0,9,7,0,0,5,0};
        int[] row3 = {0,0,0,0,6,0,0,0,2};
        int[] row4 = {3,0,0,2,0,0,5,0,0};
        int[] row5 = {0,6,0,0,0,0,0,8,0};
        int[] row6 = {0,0,7,0,0,5,0,0,9};
        int[] row7 = {5,0,0,0,1,0,0,0,0};
        int[] row8 = {0,9,0,0,4,7,0,2,0};
        int[] row9 = {6,0,8,0,0,0,0,0,0};

        int[][] board = {row1, row2, row3, row4, row5, row6, row7, row8, row9};
//        int[][] board = new int[9][9];
        solvePuzzle(board);
        for(int[] list : board){
            for(int num : list){
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
