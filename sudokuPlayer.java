package games;
import java.util.Scanner;
import java.util.Arrays;

public class sudokuPlayer extends sudokuGenerator{
    protected boolean checkVictory(int[][] board, int[][] solution){
        for(int i=0; i<9; i++){
            if(!(Arrays.equals(board[i], solution[i]))){
                return false;
            }
        }
        return true;
    }

    private boolean playTurn(int[][] board, int[][] invalidPositions, int x, int y, int num){
        if(x>8 || x<0 || y>8 || y<0){
            System.out.println("Invalid Location!");
            return false;
        }
        for (int[] pair : invalidPositions) {
            if (x == pair[0] && y == pair[1]) {
                System.out.println("Invalid Location!");
                return false;
            }
        }
        board[x][y] = num;
        return true;
    }

    private int[][] invalidPositions(int[][] board){
        int count = 0;
        for(int[] list : board){
            for(int num : list){
                if(num != 0)
                    count++;
            }
        }
        int[][] invalidPositions = new int[count][2];
        int c = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != 0){
                    invalidPositions[c][0] = i;
                    invalidPositions[c][1] = j;
                    c++;
                }
            }
        }
        return invalidPositions;
    }

    protected boolean anyZeroes(int[][] board){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] == 0){
                    return true;
                }
            }
        }
        return false;
    }

    private void printBoard(int[][] board, int[][] invalidPositions){
        System.out.println("    1  2  3  4  5  6  7  8  9\n   ┌──┬──┬──┬──┬──┬──┬──┬──┬──┐");
        for(int i=0; i<9; i++){
            System.out.print((i+1) + "  ");
            for(int j=0; j<9; j++){
                if(j==0)
                    System.out.print("│");
                if(board[i][j] == 0)
                    System.out.print("  │");
                else {
                    System.out.print(board[i][j]);
                    boolean isInvalid = false;
                    for (int k=0; k<invalidPositions.length; k++) {
                        if (invalidPositions[k][0] == i && invalidPositions[k][1] == j) {
                            isInvalid = true;
                            k = invalidPositions.length;
                        }
                    }
                    if (isInvalid) {
                        System.out.print("x│");
                    } else
                        System.out.print(" │");
                }
            }
            if(i==8)
                System.out.println("\n   └──┴──┴──┴──┴──┴──┴──┴──┴──┘");
            else
                System.out.println("\n   ├──┼──┼──┼──┼──┼──┼──┼──┼──┤");
        }
    }

    public static void main(String[] args) {
        sudokuGenerator gen = new sudokuGenerator();
        sudokuPlayer play1 = new sudokuPlayer();
        Scanner scan = new Scanner(System.in);
        int wins = 0;
        long totalTime = 0;
        String playerName;
        boolean replay = true;
        System.out.print("Enter your name: ");
        playerName = scan.next();
        System.out.println("Welcome, " + playerName + "!");
        while(replay){
            replay = false;
            System.out.println("Game start! Given numbers will be marked with an x");
            int[][] solution = gen.createSolvedBoard();
            System.out.print("Enter how many empty spaces you would like: ");
            int spaces = scan.nextInt();
            int[][] puzzle = gen.createSolvablePuzzle(solution, spaces);
            int[][] invalidPositions = play1.invalidPositions(puzzle);
            boolean isDone = false;
            boolean didQuit = false;
            while((!(isDone)) && !(didQuit)) {
                play1.printBoard(puzzle, invalidPositions);
                System.out.println("Enter the row, column, then number you'd like to place! Or enter 0 at anytime to quit!");
                long startTime = System.currentTimeMillis();
                int x = scan.nextInt() - 1;
                int y = scan.nextInt() - 1;
                int num = scan.nextInt();
                if((x+1) == 0 || (y+1) == 0 || num == 0){
                    didQuit = true;
                }
                else {
                    while (!(play1.playTurn(puzzle, invalidPositions, x, y, num))) {
                        System.out.println("Enter a valid position!");
                        x = scan.nextInt() - 1;
                        y = scan.nextInt() - 1;
                        num = scan.nextInt();
                    }
                    long endTime = System.currentTimeMillis();
                    if (!play1.anyZeroes(puzzle)) {
                        if (play1.checkVictory(puzzle, solution)) {
                            isDone = true;
                            wins++;
                        }
                        else
                            System.out.println("Puzzle contains errors!");
                    }
                    totalTime += (endTime - startTime);
                }
            }
            if(isDone) {
                int timeSec = (int) (totalTime / 1000.0);
                int timeMin = timeSec / 60;
                timeSec = timeSec % 60;
                System.out.println("You won in  " + timeMin + " minutes and " + timeSec + " seconds! Would you like to play a new puzzle? Enter Y for yes, or anything else for no.");
                String response = scan.next();
                if (response.equals("Y"))
                    replay = true;
            }
            else{
                System.out.println("You quit! Would you like to play a different puzzle? Enter Y for yes or anything else for no");
                String response = scan.next();
                if(response.equals("Y"))
                    replay = true;
            }
        }
        if(wins > 0)
            System.out.println(playerName + ", you won " + wins + " games!");
    }
}
