package model.exceptions;

public class PuzzleNumbersException extends Exception {

    public PuzzleNumbersException(int maxNum) {
        super("Please insert valid non repeating numbers from 1-" + maxNum + " and one empty field");
    }

}
