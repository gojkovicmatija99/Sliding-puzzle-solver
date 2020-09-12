package model.algorithm;

import model.state.State;
import model.state.StateComparator;
import model.state.StateGenerator;

import java.util.*;

public class IterativeDeepeningAStar extends Algorithm {

    public IterativeDeepeningAStar(State initialState, State goalState) {
        super(initialState, goalState);
    }

    @Override
    public Stack<State> solve() {
        int nodeExplored = 0;
        double cutoff = initialState.getGoalDistance();
        while (true) {

            Set<State> explored = new HashSet<State>();
            PriorityQueue<State> queue = new PriorityQueue<State>(10000, new StateComparator());
            Stack<State> toReturn = new Stack<>();
            StateGenerator generator = new StateGenerator();

            double min_above = Integer.MAX_VALUE;
            queue.add(initialState);
            explored.add(initialState);
            while (!queue.isEmpty()) {
                State current = queue.poll();
                nodeExplored++;
                //Updates View every 100 000 nodes explored
                if (nodeExplored % 100000 == 0)
                    this.notifySubscriber(nodeExplored);

                if (current.equals(goalState)) {
                    this.numOfSteps = current.getDepth();
                    this.nodeExplored = nodeExplored;
                    toReturn = current.getPath();
                    return toReturn;
                }

                List<State> nextStates = generator.generateStates(current);
                for (State state : nextStates) {
                    state.setHeuristicValue(goalState);
                    if (explored.contains(state)) {
                        continue;
                    }
                    if (state.getHeuristicValue() > cutoff) {
                        if (state.getHeuristicValue() < min_above) {
                            min_above = state.getHeuristicValue();
                        }
                        continue;
                    }
                    queue.add(state);
                    explored.add(state);
                }
            }
            cutoff = min_above;
        }
    }

}
