package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import view.MainView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ChooseImageController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        File file=fileChooser.showOpenDialog(MainView.getInstance());
        if(file!=null)
            MainView.getInstance().setImageFile(file);
    }
}
