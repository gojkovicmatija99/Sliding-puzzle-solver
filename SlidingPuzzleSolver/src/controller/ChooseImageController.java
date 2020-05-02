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

    String ln=File.separator;

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            long timestamp=System.currentTimeMillis();
            MainView.getInstance().setTimestamp(timestamp);

            if(!makeImageFolder())
                removeOldImages();

            //Provide number of rows and column
            int row = MainView.getInstance().getRows();
            int col = MainView.getInstance().getColumns();

            //Show dialog for image selection
            FileChooser fileChooser=new FileChooser();
            File file=fileChooser.showOpenDialog(MainView.getInstance());

            BufferedImage originalImgage = ImageIO.read(file);

            //total width and total height of an image
            int tWidth = originalImgage.getWidth();
            int tHeight = originalImgage.getHeight();

            //width and height of each piece
            int eWidth = tWidth / col;
            int eHeight = tHeight / row;

            int x = 0;
            int y = 0;

            for (int i = 0; i < row; i++) {
                y = 0;
                for (int j = 0; j < col; j++) {
                    try {
                        BufferedImage SubImage = originalImgage.getSubimage(y, x, eWidth, eHeight);
                        File outputfile = new File("SlidingPuzzleSolver"+ln+"bin"+ln+"view"+ln+"userImages"+ln+"img"+i+j+timestamp+".jpg");
                        ImageIO.write(SubImage, "jpg", outputfile);

                        y += eWidth;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                x += eHeight;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void removeOldImages() {
        try {
            File imageFolder=new File("SlidingPuzzleSolver"+ln+"bin"+ln+"view"+ln+"userImages"+ln);
            String[] images=imageFolder.list();

            for(String s: images) {
                File currentFile = new File(imageFolder.getPath(), s);
                currentFile.delete();
            }
        }
        catch (NullPointerException e){

        }

    }

    private Boolean makeImageFolder() {
        File imageFolder=new File("SlidingPuzzleSolver"+ln+"bin"+ln+"view"+ln+"userImages"+ln);
        return imageFolder.mkdir();
    }
}
