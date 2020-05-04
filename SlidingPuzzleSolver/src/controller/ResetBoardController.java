package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import view.MainView;

public class ResetBoardController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent arg0) {
        try {
            int[][] previousStartBoard = MainView.getInstance().getPreviousStartBoard();
            MainView.getInstance().setTfsStart(previousStartBoard);
        } catch (NullPointerException npe) {
            new Alert(AlertType.INFORMATION, "There is no previous board", ButtonType.OK).show();
        }
    }

}
