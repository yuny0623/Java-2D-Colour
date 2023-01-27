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
            e.printStackTrace();
            return null;
        }
    }

    public int getWidth(){
        return w;
    }
    public int getHeight(){
        return h;
    }
}
