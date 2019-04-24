package flawedsolutions;

import sudoku.board.SudokuFileReader;
import sudoku.exceptions.InvalidBoardPositionException;

import java.util.*;

/**
 * Represents a 9x9 sudoku board. The elements in the
 * board are stored in a 9x9 array of integers where
 * 0 represents an empty location and 1-9 represent
 * a user choice.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class Sudoku2
{
    public static final Random RANDOM = new Random();
    public static final int BOARD_SIZE = 9;
    public static final int TOTAL_CELLS = BOARD_SIZE * BOARD_SIZE;
    public static final int REGION_SIZE = 3;

    private SudokuFileReader randomBoards;
    private int[][] board;

    /**
     * Creates a new 9x9 sudoku board.
     */
    public Sudoku2()
    {
        randomBoards = new SudokuFileReader();

        resetBoard();
    }

    private void resetBoard()
    {
        board = new int[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * Resets the board to an empty board and then fills the given number
     * of spots in the sudoku board with pre-existing elements for a player
     * to begin the match with. These present restrictions which can
     * increase the difficulty of the game.
     *
     * Each time this method is called the underlying solution for the board
     * is changed.
     *
     * @param spots the number of spots to fill
     * @throws IllegalArgumentException thrown if the number of spots
     * is not in the range [0,81].
     */
    public void populateBoard(int spots)
    {
        if (spots < 0 || spots > TOTAL_CELLS)
        {
            throw new IllegalArgumentException("Number of spots must be in the range [0,81]");
        }

        resetBoard();

        //pick a random board, then reveal the number of spots given
        int[] randomBoard = randomBoards.getRandomBoard();

        //copy over the solution
        for (int i = 0; i < randomBoard.length; i++)
        {
            assignCell(i, randomBoard[i]);
        }

        //obscure random cells on the board until only the number
        //of spots requested are showing
        int spotsToObscure = TOTAL_CELLS - spots;
        Set<Integer> obscured = new HashSet<>();
        while (obscured.size() < spotsToObscure - 1) //OOPS!
        {
            int spot = RANDOM.nextInt(TOTAL_CELLS);
            obscured.add(spot);
            assignCell(spot, 0);
        }
    }

    private void assignCell(int index, int value)
    {
        int row = index / BOARD_SIZE;
        int column = index % BOARD_SIZE;

        board[row][column] = value;
    }

    /**
     * Changes the value on the Sudoku1 board at the given
     * row & column.
     *
     * @param choice a number in the range [1,9]
     * @param row a row index in the range [0,8]
     * @param column a column index in the range [0,8]
     * @throws IllegalArgumentException thrown if the choice is not in
     * the range [1,9]
     * @throws InvalidBoardPositionException thrown if the row or column is not
     * in the range [0,8]
     */
    public void makeChoice(int choice, int row, int column)
    {
        checkIndex(row, IndexType.ROW);
        checkIndex(column, IndexType.COLUMN);

        if (choice < 1 || choice > BOARD_SIZE)
        {
            throw new IllegalArgumentException(choice + " must be in the range [1,9]");
        }

        board[row][column] = choice;
    }

    private void checkIndex(int value, IndexType index)
    {
        if (value < 0 || value >= BOARD_SIZE)
        {
            throw new InvalidBoardPositionException(index + " must be in the range [0,8].");
        }
    }

    /**
     * Returns a copy of the internal sudoku.board.Sudoku1 board.
     *
     * @return a 9x9 grid with cells that have player
     * choices from 1-9 or are empty, which is represented
     * by 0.
     */
    public int[][] getBoard()
    {
        int[][] result = new int[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int j = 0; j < BOARD_SIZE; j++)
            {
                result[i][j] = board[i][j];
            }
        }

        return result;
    }

    /**
     * Reports whether the given row contains a contradiction or not.
     * This does not check whether the row has values in all locations,
     * but instead whether there are duplicate elements or not.
     *
     * @param row a number in the range [0,8]
     * @throws InvalidBoardPositionException thrown if the row is not
     * in the range [0,8]
     * @return returns false if there is a contradiction with the numbers
     * in the row, otherwise returns true
     */
    public boolean isRowValid(int row)
    {
        checkIndex(row, IndexType.ROW);

        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++)
        {
            countDuplicates(board[row][i], set, list);
        }

        return set.size() == list.size();
    }

    private void countDuplicates(int element, Set<Integer> set, List<Integer> list)
    {
        if (element != 0)
        {
            set.add(element);
            list.add(element);
        }
    }

    /**
     * Reports whether the given column contains a contradiction or not.
     * This does not check whether the column has values in all locations,
     * but instead whether there are duplicate elements or not.
     *
     * @param column a number in the range [0,8]
     * @throws InvalidBoardPositionException thrown if the column is not
     * in the range [0,8]
     * @return returns false if there is a contradiction with the numbers
     * in the column, otherwise returns true
     */
    public boolean isColumnValid(int column)
    {
        checkIndex(column, IndexType.COLUMN);

        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++)
        {
            countDuplicates(board[i][column], set, list);
        }

        return set.size() == list.size();
    }

    /**
     * Reports whether the given region contains a contradiction or not.
     * This does not check whether the region has values in all locations,
     * but instead whether there are duplicate elements or not.
     *
     * A region is one of the 9 groups of 9 cells that make up the sudoku
     * board.
     *
     * @param region a number in the range [0,8]
     * @throws InvalidBoardPositionException thrown if the region is not
     * in the range [0,8]
     * @return returns false if there is a contradiction with the numbers
     * in the region, otherwise returns true
     */
    public boolean isRegionValid(int region)
    {
        checkIndex(region, IndexType.REGION);

        int regionRow = (region / REGION_SIZE) * REGION_SIZE;
        int regionColumn = (region % REGION_SIZE) * REGION_SIZE;

        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < REGION_SIZE; i++)
        {
            for (int j = 0; j < REGION_SIZE; j++)
            {
                int element = board[regionRow + i][regionColumn + j];

                if (element != 0)
                {
                    set.add(element);
                    list.add(element);
                }
            }
        }
        return set.size() == list.size();
    }

    /**
     * Reports whether the user has made a choice at every
     * available position on the board.
     *
     * @return returns true if the board has a choice at each
     * position, or false otherwise
     */
    public boolean isBoardFull()
    {
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int j = 0; j < BOARD_SIZE; j++)
            {
                if (board[i][j] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reports whether the user has solved the sudoku game.
     *
     * @return returns true if there are no contradictions on the
     * board and all locations have a number in the range [1,9]
     */
    public boolean isSolved()
    {
        if (!isBoardFull())
        {
            return false;
        }

        //check each row, column and region
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (!isRegionValid(i) || !isRowValid(i) || !isColumnValid(i))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Board state: " + Arrays.toString(board);
    }

    private enum IndexType
    {
        ROW("Row"),
        COLUMN("Column"),
        REGION("Region");

        private String output;

        IndexType(String output)
        {
            this.output = output;
        }

        @Override
        public String toString()
        {
            return output;
        }
    }
}
