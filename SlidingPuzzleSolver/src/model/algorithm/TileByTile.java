package model.algorithm;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import model.exceptions.PuzzleNumbersException;
import model.state.State;
import model.state.StateComparator;
import model.state.StateGenerator;
import view.MainView;

public class TileByTile extends Algorithm{
	
	public TileByTile(State initialState, State goalState) {
		super(initialState, goalState);
	}
	
	@Override
	public Stack<State> solve() {
		Stack<State> stack=new Stack<State>();
		Deque<State> deque=new LinkedList<State>();
		
		int[][] initialBoard=initialState.getBoard();
		int[][] goalBoard=goalState.getBoard();
		int rows=State.getRows();
		int columns=State.getColumns();
		int numOfTopRows=rows-2;
		int numOfSorts=(columns+1)/2;
		
		for(int currRow=0;currRow<=numOfTopRows;currRow++)
			for(int numOfTilesToSort=1;numOfTilesToSort<=numOfSorts;numOfTilesToSort++)
			{
				//Is false when topRows are sorted
				boolean twoByTwoTiles=true;
				if(currRow==numOfTopRows)
					twoByTwoTiles=false;
				
				int numOfBottomRows=rows-currRow;
				State.setRows(numOfBottomRows);
				
				int[][] newInitialBoard=new int[numOfBottomRows][columns];
				int[][] newGoalBoard=new int[numOfBottomRows][columns];
				int[][] previousBoard=new int[numOfBottomRows][columns];
				
				//Start from initialState
				if(currRow==0 && numOfTilesToSort==1)
					previousBoard=initialBoard;
				//Else continue from previous state
				else
					previousBoard=deque.pollLast().getBoard();
				
				//Ignore top rows
				newInitialBoard=getBottomHalf(previousBoard,columns, currRow, numOfBottomRows);
				newGoalBoard=getBottomHalf(goalBoard,columns,currRow,numOfBottomRows);

				//If true sort two by two tiles, else sort whole board
				if(twoByTwoTiles)
					newInitialBoard=pripritizeTiles(goalBoard[currRow], newInitialBoard, numOfBottomRows, columns, numOfTilesToSort);
				
				State newInitialState=new State(newInitialBoard, null);
				State newGoalState=new State(newGoalBoard,null);
				
				IterativeDeepeningAStar ida=new IterativeDeepeningAStar(newInitialState, newGoalState);
				ida.addSubscriber(MainView.getInstance());
				stack=ida.solve();
				this.numOfSteps+=ida.getNumOfSteps();
				this.nodeExplored+=ida.getNodeExplored();
				
				//Add top half that was ignored
				addTopHalf(stack,goalBoard,rows,columns,currRow);
				addToDeque(deque, stack);
			}	
			
		return dequeToStack(deque);
	}
	
	private int[][] pripritizeTiles(int[] pattern,int[][] initialBoard,int rows,int columns,int limit)
	{
		//Prioritize all tiles
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
				initialBoard[i][j]=Math.abs(initialBoard[i][j]);
		
		int[][] toReturn=new int[rows][columns];
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				boolean contains=false;
				for(int p=0;p<limit*2;p++)
				{
					//If last tile is in the next row, skip it
					if(p/columns==1)
						continue;
					if(pattern[p]==initialBoard[i][j] || initialBoard[i][j]==0)
						contains=true;
				}
				//If contains tile, prioritize it
				if(contains)
					toReturn[i][j]=initialBoard[i][j];
				//Else ignore it
				else
					toReturn[i][j]=initialBoard[i][j]*(-1);	
			}
		return toReturn;		
	}
	
	public void addToDeque(Deque<State> deque,Stack<State> stack)
	{
		while(!stack.isEmpty())
			deque.addLast(stack.pop());
	}
	
	public Stack<State> dequeToStack(Deque<State> deque)
	{
		Stack<State> toReturn=new Stack<State>();
		while(!deque.isEmpty())
			toReturn.push(deque.pollLast());
		return toReturn;
	}
	
	public int[][] getBottomHalf(int[][] board,int columns,int numOfTopRows,int numOfBottomRows)
	{
		int[][] toReturn=new int[numOfBottomRows][columns];
		for(int i=0;i<numOfBottomRows;i++)
			for(int j=0;j<columns;j++)
				toReturn[i][j]=Math.abs(board[i+numOfTopRows][j]);
		return toReturn;
	}
	
	public void addTopHalf(Stack<State> stack,int[][] goalBoard,int rows,int columns,int numOfTopRows)
	{
		State.setRows(rows);
		Stack<State> tempStack=new Stack<State>();
		
		while(!stack.isEmpty())
		{
			State currState=stack.pop();
			int[][] newBoard=new int[rows][columns];
			for(int i=0;i<rows;i++)
				for(int j=0;j<columns;j++)
				{
					if(i<numOfTopRows)
						newBoard[i][j]=goalBoard[i][j];
					else
						newBoard[i][j]=currState.getBoard()[i-numOfTopRows][j];
				}
			State newState=new State(newBoard, null);
			tempStack.push(newState);
		}
		while(!tempStack.isEmpty())
			stack.push(tempStack.pop());	
	}
}