package games;

import java.util.Scanner;

public class ticTacToe {
    private static char[][] createBoard(){
        return new char[3][3];
    }

    private static boolean checkVictoryP1(char[][] board){
        if(board[0][0] == 'O' && board[0][0] == board[0][1] && board[0][1] == board[0][2])
            return true;
        if(board[1][0] == 'O' && board[1][0] == board[1][1] && board[1][1] == board[1][2])
            return true;
        if(board[2][0] == 'O' && board[2][0] == board[2][1] && board[2][1] == board[2][2])
            return true;
        if(board[0][0] == 'O' && board[0][0] == board[1][0] && board[1][0] == board[2][0])
            return true;
        if(board[0][1] == 'O' && board[0][1] == board[1][1] && board[1][1] == board[2][1])
            return true;
        if(board[0][2] == 'O' && board[0][2] == board[1][2] && board[1][2] == board[2][2])
            return true;
        if(board[0][0] == 'O' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return true;
        return board[0][2] == 'O' && board[0][2] == board[1][1] && board[1][1] == board[2][0];
    }

    private static boolean checkVictoryP2(char[][] board){
        if(board[0][0] == 'X' && board[0][0] == board[0][1] && board[0][1] == board[0][2])
            return true;
        if(board[1][0] == 'X' && board[1][0] == board[1][1] && board[1][1] == board[1][2])
            return true;
        if(board[2][0] == 'X' && board[2][0] == board[2][1] && board[2][1] == board[2][2])
            return true;
        if(board[0][0] == 'X' && board[0][0] == board[1][0] && board[1][0] == board[2][0])
            return true;
        if(board[0][1] == 'X' && board[0][1] == board[1][1] && board[1][1] == board[2][1])
            return true;
        if(board[0][2] == 'X' && board[0][2] == board[1][2] && board[1][2] == board[2][2])
            return true;
        if(board[0][0] == 'X' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return true;
        return board[0][2] == 'X' && board[0][2] == board[1][1] && board[1][1] == board[2][0];
    }

    private static boolean playTurn(char[][] board, int player, int x, int y){
        if(x>2 || x<0 || y>2 || y<0 || board[x][y] == 'O' || board[x][y] == 'X') {
            System.out.println("Invalid move");
            return false;
        }
        else {
            if (player == 1) {
                board[x][y] = 'O';
            }
            else {
                board[x][y] = 'X';
            }
            return true;
        }
    }

    private static void printBoard(char[][] board){
        System.out.println("  0 1 2");
        for(int i=0; i<3; i++){
            System.out.print(i + " ");
            for(int j=0; j<3; j++){
                if(board[i][j] != 'X' && board[i][j] != 'O')
                    System.out.print(" ");
                else
                    System.out.print(board[i][j]);
                if(j!=2)
                    System.out.print("|");
            }
            if(i!=2)
                System.out.println("\n  -----");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int p1Wins =0, p2Wins=0, ties=0, games=0;
        String p1Name, p2Name;
        boolean replay = true;
        System.out.println("Player 1, enter your name: ");
        p1Name = scan.next();
        System.out.println("Player 2, enter your name: ");
        p2Name = scan.next();
        System.out.println("Welcome, " + p1Name + " and " + p2Name + "!");
        while(replay){
            replay = false;
            System.out.println("Game Start!");
            char[][] board = createBoard();
            printBoard(board);

            for(int i=0; i<9; i++){
                int x, y;
                if(i%2==0) {
                    System.out.println(p1Name + ", it's your turn! Enter the row, press ENTER, then the column!");
                    x = scan.nextInt();
                    y = scan.nextInt();
                    while(!playTurn(board, 1, x, y)){
                        System.out.println(p1Name + ", Enter a valid position! Enter the row, press ENTER, then the column!");
                        x = scan.nextInt();
                        y = scan.nextInt();
                    }
                }
                else{
                    System.out.println(p2Name + ", it's your turn! Enter the row, press ENTER, then the column!");
                    x = scan.nextInt();
                    y = scan.nextInt();
                    while(!playTurn(board, 2, x, y)){
                        System.out.println(p2Name + ", Enter a valid position! Enter the row, press ENTER, then the column!");
                        x = scan.nextInt();
                        y = scan.nextInt();
                    }
                }
                printBoard(board);
                if(i>3){
                    if(checkVictoryP1(board)){
                        i=9;
                        System.out.println(p1Name + " won! Player 1 has won " + ++p1Wins + " times!");
                    }
                    else if(checkVictoryP2(board)){
                        i=9;
                        System.out.println(p2Name + " won! Player 2 has won " + ++p2Wins + " times!");
                    }
                    else if(i==8)
                        System.out.println("Tie! There has been " + ++ties + " ties!");
                }

            }
            games++;
            System.out.println("Stats\nGames played: " + games + "\n" + p1Name + " wins: " + p1Wins + "\n" + p2Name + " wins: " + p2Wins + "\nTies: " + ties);
            System.out.print("Would you like to play again? Enter Y for yes, or press ENTER for no. ");
            if(scan.next().equals("Y"))
                replay = true;
        }
    }
}
