package model.state;

import model.heuristic.IHeuristic;

import java.awt.*;
import java.util.Stack;

public class State {
    private static IHeuristic heuristic;
    private static int rows;
    private static int columns;

    private State parent;
    private int[][] board;
    private Point emptyField;
    private int depth;
    private int goalDistance;

    public State(int[][] board, State parent) {
        this.emptyField = new Point(0, 0);
        this.board = new int[rows][columns];

        this.parent = parent;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == 0) {
                    emptyField.x = j;
                    emptyField.y = i;
                } else
                    this.board[i][j] = board[i][j];
            }
    }

    public static int getRows() {
        return rows;
    }

    public static void setRows(int rows) {
        State.rows = rows;
    }

    public static int getColumns() {
        return columns;
    }

    public static void setColumns(int columns) {
        State.columns = columns;
    }

    public static void setHeuristic(IHeuristic heuristic) {
        State.heuristic = heuristic;
    }

    public void setDepth() {
        if (parent == null)
            this.depth = 0;
        else
            this.depth = parent.getDepth() + 1;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(State goalState) {
        this.goalDistance = heuristic.getHeuristicValue(this, goalState);
    }

    public int getDepth() {
        return depth;
    }

    public int getHeuristicValue() {
        return goalDistance + depth;
    }

    public void setHeuristicValue(State goalState) {
        setGoalDistance(goalState);
        setDepth();
    }

    public State getParent() {
        return parent;
    }

    public Point getEmptyField() {
        return emptyField;
    }

    public Stack<State> getPath() {
        State current = this;
        Stack<State> path = new Stack<State>();
        while (current != null) {
            path.push(current);
            current = current.getParent();
        }
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State)) return false;
        State s = (State) o;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (this.board[i][j] != s.getBoard()[i][j] && (this.board[i][j] >= 0 && s.getBoard()[i][j] >= 0))
                    return false;
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (board[i][j] >= 0)
                    result = prime * result + board[i][j];
                else
                    result = prime * result - 1;
            }
        return result;
    }
}
