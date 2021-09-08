package games;
import java.util.Random;
import java.util.Arrays;

public class sudokuGenerator extends sudokuSolver{
    //Generates a playable puzzle

    private static final int[][] valuesToBeRemoved = new int[50][2];

    protected int[][] createSolvablePuzzle(int[][] solvedBoard, int emptySpaces){
        int i = 0;
        int[][] solvablePuzzle = new int[9][9];
        while((i+1)!=emptySpaces) {
            solvablePuzzle = full2DCopy(solvedBoard);
            for (int j = 0; j < 50; j++) {
                valuesToBeRemoved[j][0] = -1;
                valuesToBeRemoved[j][1] = -1;
            }
            valuesToBeRemoved[0] = removeRandomValue(solvablePuzzle, solvedBoard, 1);
            int[] pair = {-1, -1};
            i = 0;
            while ((!Arrays.equals(pair, valuesToBeRemoved[i]))) {
                solvablePuzzle[valuesToBeRemoved[i][0]][valuesToBeRemoved[i][1]] = 0;
                i++;
            }
        }
        return solvablePuzzle;
    }

    private static boolean checkForUniqueSolution(int[][] solvablePuzzle, int[][] solvedBoard){
        int[][] testPuzzle = full2DCopy(solvablePuzzle);
        for(int i=0; i<3; i++){
            solvePuzzle(testPuzzle);
            for(int j=0; j<9; j++){
                if(!(Arrays.equals(testPuzzle[j], solvedBoard[j])))
                    return false;
            }
        }
        return true;
    }

    private static int[] removeRandomValue(int[][] solvablePuzzle, int[][] solution, int count){
        int[] empty = {-1,-1};
        if(count>=50) {
            return empty;
        }
        else {
            Random rand = new Random();
            int i, j, temp;
            while (true) {
                i = rand.nextInt(9);
                j = rand.nextInt(9);
                if (solvablePuzzle[i][j] != 0) {
                    temp = solvablePuzzle[i][j];
                    solvablePuzzle[i][j] = 0;
                    int[] locations = {i, j};
                    if (checkForUniqueSolution(solvablePuzzle, solution)) {
                        int[] pair = removeRandomValue(solvablePuzzle, solution, count + 1);
                        if(pair[0] != -1){
                            valuesToBeRemoved[count-1][0] = pair[0];
                            valuesToBeRemoved[count-1][1] = pair[1];
                        }
                        return locations;
                    }
                    else {
                        solvablePuzzle[i][j] = temp;
                        return empty;
                    }
                }
            }
        }
    }


    protected int[][] createSolvedBoard(){
        int[][] board = new int[9][9];
        solvePuzzle(board);
        return board;
    }

    private static int[][] full2DCopy(int[][] board){
        int[][] copy = new int[board.length][board[0].length];
        for(int i=0; i<board.length; i++){
            copy[i] = Arrays.copyOf(board[i], 9);
        }
        return copy;
    }

    public static void main(String[] args) {
        sudokuGenerator gen = new sudokuGenerator();
        int[][] solution = gen.createSolvedBoard();
        int[][] puzzle = gen.createSolvablePuzzle(solution, 40);
        for(int[] list : solution){
            for(int num : list)
                System.out.print(num + " ");
            System.out.println();
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        for(int[] list : puzzle){
            for(int num : list)
                System.out.print(num + " ");
            System.out.println();
        }
    }
}
