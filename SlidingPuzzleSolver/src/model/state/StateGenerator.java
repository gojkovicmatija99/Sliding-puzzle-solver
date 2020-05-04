package model.state;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StateGenerator {
    public static State makeGoalState() {
        int rows = State.getRows();
        int columns = State.getColumns();

        int num = 1;
        int[][] goalBoard = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (i == rows - 1 && j == columns - 1)
                    goalBoard[i][j] = 0;
                else
                    goalBoard[i][j] = num++;
            }
        return new State(goalBoard, null);
    }

    public List<State> generateStates(State currentState) {
        Point emptyField = currentState.getEmptyField();
        State parent = currentState;
        int rows = State.getRows();
        int columns = State.getColumns();

        List<State> nextStates = new ArrayList<>();
        if (this.checkRight(emptyField, columns))
            nextStates.add(this.moveRight(rows, columns, emptyField, parent));
        if (this.checkDown(emptyField, rows))
            nextStates.add(this.moveDown(rows, columns, emptyField, parent));
        if (this.checkUp(emptyField))
            nextStates.add(this.moveUp(rows, columns, emptyField, parent));
        if (this.checkLeft(emptyField, columns))
            nextStates.add(this.moveLeft(rows, columns, emptyField, parent));

        return nextStates;
    }

    private boolean checkUp(Point emptyField) {
        int pos = emptyField.y - 1;
        if (pos < 0)
            return false;
        return true;
    }

    private boolean checkDown(Point emptyField, int rows) {
        int pos = emptyField.y + 1;
        if (pos >= rows)
            return false;
        return true;
    }

    private boolean checkLeft(Point emptyField, int columns) {
        if (emptyField.x % columns == 0)
            return false;
        return true;
    }

    private boolean checkRight(Point emptyField, int columns) {
        if (emptyField.x % columns == columns - 1)
            return false;
        return true;
    }

    private State moveUp(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y - 1][emptyField.x];
        newBoard[emptyField.y - 1][emptyField.x] = temp;

        return new State(newBoard, parent);
    }

    private State moveDown(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y + 1][emptyField.x];
        newBoard[emptyField.y + 1][emptyField.x] = temp;

        return new State(newBoard, parent);
    }

    private State moveLeft(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y][emptyField.x - 1];
        newBoard[emptyField.y][emptyField.x - 1] = temp;

        return new State(newBoard, parent);
    }

    private State moveRight(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y][emptyField.x + 1];
        newBoard[emptyField.y][emptyField.x + 1] = temp;

        return new State(newBoard, parent);
    }

    private void copyBoard(int[][] newBoard, int rows, int columns, State parent) {
        int[][] board = parent.getBoard();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                newBoard[i][j] = board[i][j];
    }
}
