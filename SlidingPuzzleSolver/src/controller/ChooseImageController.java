package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import view.MainView;

import java.io.File;

public class ChooseImageController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(MainView.getInstance());
        if (file != null)
            MainView.getInstance().setImageFile(file);
    }
}
