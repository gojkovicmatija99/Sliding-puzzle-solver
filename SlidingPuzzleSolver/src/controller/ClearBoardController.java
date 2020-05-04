package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.MainView;

public class ClearBoardController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent arg0) {
        int rows = MainView.getInstance().getRows();
        int columns = MainView.getInstance().getColumns();
        int[][] clearBoard = new int[rows][columns];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                clearBoard[i][j] = 0;
            }
        MainView.getInstance().setTfsStart(clearBoard);
        MainView.getInstance().setImages(clearBoard);
    }

}
