package sudoku.consoleprogram;

import sudoku.board.Sudoku;

/**
 * A small console program that lets the player solve
 * a Sudoku1 puzzle using the Sudoku1 class.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public class PlaySudoku
{
    private static Sudoku board;

    /**
     * Entry point for the Sudoku1 console program.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        printWelcome();
        prepareMatch();
        solveBoard();
    }

    private static void printWelcome()
    {
        System.out.println("Welcome to the Sudoku1 Console Game!");
        System.out.println("***********************************");
        System.out.println();
    }

    private static void prepareMatch()
    {
        System.out.println("Generating a new Sudoku1 board...");
        board = new Sudoku();
        System.out.println("How many cells should I reveal [0,81]? ");
        board.populateBoard(Console.getInt());
    }

    private static void solveBoard()
    {
        while (!board.isSolved())
        {
            System.out.println(board.toString());
            System.out.println("Board incomplete");

            int row = Console.getInt("Row?");
            int column = Console.getInt("Column?");
            int choice = Console.getInt("Number?");

            board.makeChoice(choice, row, column);
        }

        System.out.println(convertBoardToString());
        System.out.println("Congratulations, you win!");
    }

    private static String convertBoardToString()
    {
        int[][] cells = board.getBoard();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < Sudoku.BOARD_SIZE; i++)
        {
            if (i % Sudoku.REGION_SIZE == 0)
            {
                builder.append("+---------+---------+---------+\n");
            }

            for (int j = 0; j < Sudoku.BOARD_SIZE; j++)
            {
                if (j % Sudoku.REGION_SIZE == 0)
                {
                    builder.append("|");
                }

                int spot = cells[i][j];
                builder.append(spot == 0 ? " - " : " " + spot + " ");
            }
            builder.append("|\n");
        }
        builder.append("+---------+---------+---------+");

        return builder.toString();
    }
}
