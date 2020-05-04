package model.heuristic;

import model.state.State;

import java.awt.*;

public class LinearConflict implements IHeuristic {

    @Override
    public int getHeuristicValue(State initialState, State goalState) {
        int rows = State.getRows();
        int columns = State.getColumns();
        int[][] initialBoard = initialState.getBoard();
        int[][] goalBoard = goalState.getBoard();
        Manhatten manhatten = new Manhatten();

        Point[] goalPositions = new Point[100];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (initialBoard[i][j] >= 0)
                    goalPositions[initialBoard[i][j]] = findInGoalState(initialBoard[i][j], goalBoard, rows, columns);
            }

        int diff = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (initialBoard[i][j] == 0 || initialBoard[i][j] < 0)
                    continue;
                else {
                    Point tile1GoalPosition = goalPositions[initialBoard[i][j]];

                    //Checks linear conflict for rows
                    for (int k = 0; k < columns; k++) {
                        if (initialBoard[i][k] == 0 || initialBoard[i][k] < 0)
                            continue;
                        Point tile2GoalPosition = goalPositions[initialBoard[i][k]];
                        if (i == tile1GoalPosition.getX() && tile1GoalPosition.getX() == tile2GoalPosition.getX() && j < k && tile1GoalPosition.getY() > tile2GoalPosition.getY())
                            diff++;
                    }
                    //Checks linear conflict for columns
                    for (int k = 0; k < rows; k++) {
                        if (initialBoard[k][j] == 0 || initialBoard[k][j] < 0)
                            continue;
                        Point tile2GoalPosition = goalPositions[initialBoard[k][j]];
                        if (j == tile1GoalPosition.getY() && tile1GoalPosition.getY() == tile2GoalPosition.getY() && i < k && tile1GoalPosition.getX() > tile2GoalPosition.getX())
                            diff++;
                    }
                }
            }

        return manhatten.getHeuristicValue(initialState, goalState) + 2 * diff;
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
