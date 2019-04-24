package sudoku.board;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Reads from a file of 100 solved Sudoku1 puzzles. Puzzles can be selected
 * at random to seed solutions to the Sudoku1 class.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public class SudokuFileReader
{
    private String[] boards;

    /**
     * Loads 100 solutions to Sudoku1 puzzles into memory.
     */
    public SudokuFileReader()
    {
        List<String> list = new ArrayList<>();
        try (Scanner reader = new Scanner(new FileInputStream("files/100sudoku.txt")))
        {
            //add all lines to an array list and then convert to a static array
            while (reader.hasNextLine())
            {
                list.add(reader.nextLine());
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Error reading file: " + ex.getMessage());
        }
        boards = list.toArray(new String[0]);
    }

    /**
     * Retrieves a single solution to a Sudoku1 puzzle.
     * @return a random solution to a Sudoku1 puzzle.
     */
    public int[] getRandomBoard()
    {
        //get the board and separate into characters
        int[] results = new int[Sudoku.BOARD_SIZE * Sudoku.BOARD_SIZE];
        String board = boards[Sudoku.RANDOM.nextInt(boards.length)];
        String[] characters = board.split("");

        //add each character as an int
        for (int i = 0; i < characters.length; i++)
        {
            results[i] = Integer.parseInt(characters[i]);
        }

        return results;
    }

    @Override
    public String toString()
    {
        return "Random boards: " + Arrays.toString(boards);
    }
}
