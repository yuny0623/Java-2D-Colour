package org.java3d;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Resource {
    BufferedImage image = null;
    int w, h;
    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(new FileInputStream(path));
            this.w = image.getWidth();
            this.h = image.getHeight();
            return image;
        }catch(IOException e){
            return null;
        }
    }

    public int getWidth(){
        return w;
    }
    public int getHeight(){
        return h;
    }

    public int getColour(int x, int y){
        if(image == null){
            return 0;
        }
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if(x > getWidth() - 1){ // why minus 1? if x is larger or equal than getWidth it is already late giving Exception
            x = getWidth() - 1;
        }
        if(y > getHeight() - 1){
            y = getHeight() - 1;
        }
        int color = image.getRGB(x, y);
        int red = (color & 0xFF0000) >> 16;
        int green = (color & 0x00FF00) >> 8;
        int blue = (color & 0x0000FF);
        int combinedColor = red << 16 | green << 8 | blue;
        return combinedColor;
    }
}
