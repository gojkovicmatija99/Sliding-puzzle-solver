package controller;

import view.MainView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PartitionImage {
    private String ln = File.separator;

    public void divide() {
        try {
            long timestamp = System.currentTimeMillis();
            MainView.getInstance().setTimestamp(timestamp);

            if (!makeImageFolder())
                removeOldImages();

            //Provide number of rows and column
            int row = MainView.getInstance().getRows();
            int col = MainView.getInstance().getColumns();

            File file = MainView.getInstance().getImageFile();
            BufferedImage originalImage = ImageIO.read(file);

            //total width and total height of an image
            int tWidth = originalImage.getWidth();
            int tHeight = originalImage.getHeight();

            //width and height of each piece
            int eWidth = tWidth / col;
            int eHeight = tHeight / row;

            int x = 0;
            int y = 0;

            for (int i = 0; i < row; i++) {
                y = 0;
                for (int j = 0; j < col; j++) {
                    try {
                        BufferedImage SubImage = originalImage.getSubimage(y, x, eWidth, eHeight);
                        File outputfile = new File("SlidingPuzzleSolver" + ln + "bin" + ln + "view" + ln + "userImages" + ln + "img" + i + j + timestamp + ".jpg");
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
            File imageFolder = new File("SlidingPuzzleSolver" + ln + "bin" + ln + "view" + ln + "userImages" + ln);
            String[] images = imageFolder.list();

            for (String s : images) {
                File currentFile = new File(imageFolder.getPath(), s);
                currentFile.delete();
            }
        } catch (NullPointerException e) {

        }

    }

    private Boolean makeImageFolder() {
        File imageFolder = new File("SlidingPuzzleSolver" + ln + "bin" + ln + "view" + ln + "userImages" + ln);
        return imageFolder.mkdir();
    }
}
