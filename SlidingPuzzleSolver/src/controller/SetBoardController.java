package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.state.State;
import view.MainView;

public class SetBoardController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent arg0) {
        try {
            int rows = Integer.valueOf(MainView.getInstance().getTfRow());
            int columns = Integer.valueOf(MainView.getInstance().getTfCol());

            if (rows < 2 || columns < 2)
                throw new NumberFormatException();
            MainView.getInstance().setRows(rows);
            MainView.getInstance().setColumns(columns);
            MainView.getInstance().setBoard();
            MainView.getInstance().enableOrDisableCommands(true);
            MainView.getInstance().sizeToScene();

            State.setColumns(columns);
            State.setRows(rows);

        } catch (NumberFormatException nfe) {
            new Alert(AlertType.INFORMATION, "Please enter valid numbers", ButtonType.OK).show();
        }
    }

}
