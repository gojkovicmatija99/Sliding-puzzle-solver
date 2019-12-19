package controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.state.State;
import model.state.StateGenerator;
import view.MainView;

public class RandomBoardController implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent arg0) {	
		State currentState=StateGenerator.makeGoalState();
		StateGenerator generator=new StateGenerator();
		
		int numOfShuffles=10000;
		while(numOfShuffles>0)
		{
			List<State> nextStates=generator.generateStates(currentState);
			
			int maxIndex=nextStates.size();
			double randomDouble = Math.random();
			randomDouble = randomDouble * maxIndex;
			int randomInt = (int) randomDouble;
			
			currentState=nextStates.get(randomInt);
			numOfShuffles--;
		}
		
		MainView.getInstance().setTfsStart(currentState.getBoard());
	}
}
