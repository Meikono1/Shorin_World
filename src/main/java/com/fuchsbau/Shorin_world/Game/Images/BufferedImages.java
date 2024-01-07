package com.fuchsbau.Shorin_world.Game.Images;

import java.util.logging.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum BufferedImages {

    woodenbackground("woodBG.png"),
    stonebackground("stoneBG.png"),
    corner("corner.png"),
    devider1("devider_1.png"),
    devider2("devider_2.png"),
    devider3("devider_3.png"),
    devider4("devider_4.png"),
    devider5("devider_5.png"),
    devider6("devider_6.png"),
    devider1side("devider_side_1.png"),
    devider2side("devider_side_2.png"),
    devider3side("devider_side_3.png"),
    devider4side("devider_side_4.png"),
    devider5side("devider_side_5.png"),
    devider6side("devider_side_6.png"),
    bottom_ending("Ending_Bottom.png"),
    top_ending("Ending_Top.png");

    private BufferedImage bufferedImage;
    private Image scaledImage;
    int width, height;

    BufferedImages(String imagePath) {
        this.bufferedImage = loadimage(imagePath);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Image getScaledImage() {
        return scaledImage;
    }

    public void setScaledImage(int height, int width) {
        this.width = width;
        this.height = height;
        this.scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }


    private BufferedImage loadimage(String imagePath) {
        Logger logger = Logger.getLogger((Logger.GLOBAL_LOGGER_NAME));
        String workingDir = System.getProperty("user.dir");
        File imageFile = new File(workingDir + "\\resources\\images\\" + imagePath);
        if (imageFile.exists()) {
            // File exists, try reading it
            try {
                return ImageIO.read(imageFile);
            } catch (IOException e) {
                logger.severe(e.toString());
                e.printStackTrace();
            }
        } else {
            logger.severe("Image file not found: " + imagePath);
            System.err.println("Image file not found: " + imagePath);
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
