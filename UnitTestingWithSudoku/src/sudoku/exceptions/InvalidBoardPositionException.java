package sudoku.exceptions;

/**
 * An exception that represents when an invalid position
 * is chosen on a game board.
 *
 * DO NOT ALTER THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public class InvalidBoardPositionException extends RuntimeException
{
    /**
     * Stores a message with this exception object.
     * @param message the message that describes the invalid
     *                board position encountered
     */
    public InvalidBoardPositionException(String message)
    {
        super(message);
    }
}
