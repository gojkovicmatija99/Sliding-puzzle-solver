package model.heuristic;

import model.state.State;

import java.awt.*;

public class Manhatten implements IHeuristic {

    @Override
    public int getHeuristicValue(State initialState, State goalState) {
        int rows = State.getRows();
        int columns = State.getColumns();
        int[][] initialBoard = initialState.getBoard();
        int[][] goalBoard = goalState.getBoard();

        Point[] goalPositions = new Point[100];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (initialBoard[i][j] >= 0)
                    goalPositions[initialBoard[i][j]] = findInGoalState(initialBoard[i][j], goalBoard, rows, columns);
            }


        int diff = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (initialBoard[i][j] == 0 || initialBoard[i][j] < 0) continue;
                Point pnt = goalPositions[initialBoard[i][j]];
                diff += Math.abs(i - pnt.getX()) + Math.abs(j - pnt.getY());
            }
        return diff;
    }

    private Point findInGoalState(int element, int[][] goalBoard, int rows, int columns) {
        Point pnt = null;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (element == goalBoard[i][j])
                    pnt = new Point(i, j);
        return pnt;
    }
}