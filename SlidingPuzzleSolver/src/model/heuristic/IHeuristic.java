package model.heuristic;

import model.state.State;

public interface IHeuristic {
    public int getHeuristicValue(State initialState, State goalState);
}
