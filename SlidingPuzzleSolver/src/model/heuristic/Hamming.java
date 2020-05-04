package model.heuristic;

import model.state.State;

public class Hamming implements IHeuristic {

    @Override
    public int getHeuristicValue(State initialState, State goalState) {
        int rows = State.getRows();
        int columns = State.getColumns();
        int[][] initialBoard = initialState.getBoard();
        int[][] goalBoard = goalState.getBoard();

        int diff = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                if (initialBoard[i][j] == 0 || initialBoard[i][j] < 0) continue;
                if (initialBoard[i][j] != goalBoard[i][j])
                    diff++;
            }
        return diff;
    }


}
