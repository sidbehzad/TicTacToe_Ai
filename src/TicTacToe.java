import java.util.Scanner;

public class TicTacToe {
    public static final char PLAYER = 'X';     // Human player
    public static final char COMPUTER = 'O';   // AI player
    public static final char EMPTY = '_';      // Empty cell

    private char[][] board = {
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY },
            { EMPTY, EMPTY, EMPTY }
    };

    // Prints the board
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Evaluate the board to see if someone has won
    public int evaluate() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == PLAYER) return 10;
                else if (board[i][0] == COMPUTER) return -10;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == PLAYER) return 10;
                else if (board[0][i] == COMPUTER) return -10;
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == PLAYER) return 10;
            else if (board[0][0] == COMPUTER) return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == PLAYER) return 10;
            else if (board[0][2] == COMPUTER) return -10;
        }

        // No winner
        return 0;
    }

    // Find the best move for the computer
    public int[] findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = { -1, -1 };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if the cell is empty
                if (board[i][j] == EMPTY) {
                    // Make the move
                    board[i][j] = COMPUTER;

                    // Compute the minimax value for this move
                    int moveVal = minimax(0, false);

                    // Undo the move
                    board[i][j] = EMPTY;

                    // If the value of the current move is better than the best, update best
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    // Minimax algorithm
    public int minimax(int depth, boolean isMaximizing) {
        int score = evaluate();

        // If maximizer has won
        if (score == 10) return score - depth;

        // If minimizer has won
        if (score == -10) return score + depth;

        // If no more moves and no winner, it's a draw
        if (!isMoveLeft()) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER;
                        best = Math.max(best, minimax(depth + 1, false));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == EMPTY) {
                        board[i][j] = COMPUTER;
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        }
    }

    // Check if any moves are left
    public boolean isMoveLeft() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) return true;
            }
        }
        return false;
    }

    // Check if the game is over (either win, loss, or draw)
    public boolean isGameOver() {
        int score = evaluate();
        return score == 10 || score == -10 || !isMoveLeft();
    }

    // Start the game
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean isPlayerTurn = true;

        while (!isGameOver()) {
            printBoard();
            if (isPlayerTurn) {
                System.out.println("Your turn! Enter row and column (0, 1, 2): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (board[row][col] == EMPTY) {
                    board[row][col] = PLAYER;
                    isPlayerTurn = false;
                } else {
                    System.out.println("Invalid move! Try again.");
                }
            } else {
                System.out.println("Computer's turn");
                int[] bestMove = findBestMove();
                board[bestMove[0]][bestMove[1]] = COMPUTER;
                isPlayerTurn = true;
            }
        }

        printBoard();
        int score = evaluate();
        if (score == 10) {
            System.out.println("Congratulations, you win!");
        } else if (score == -10) {
            System.out.println("Computer wins!");
        } else {
            System.out.println("It's a draw!");
        }

        scanner.close();
    }


}
