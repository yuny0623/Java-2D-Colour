package org.java3d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable{

    private static final long serialVersionUID = 1L;
    public int width, height;
    private Thread thread;
    private boolean running = false;

    InputHandler input;
    Resource resource;

    // spawn point
    int x = 120;
    int y = 80;

    public Display(int width, int height){
        this.width = width;
        this.height = height;

        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        resource = new Resource();
        input = new InputHandler();
        addKeyListener(input);
    }

    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    @Override
    public void run(){
        while(running){
            update();
            render();
        }
    }

    public void update(){
        if(input.keys[KeyEvent.VK_UP]){
            y--;
        }
        if(input.keys[KeyEvent.VK_DOWN]){
            y++;
        }
        if(input.keys[KeyEvent.VK_RIGHT]){
            x++;
        }
        if(input.keys[KeyEvent.VK_LEFT]){
            x--;
        }
    }


    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(resource.loadImage("res/level.png"), 0, 0, resource.getWidth(), resource.getHeight(), null);
        g.fillRect(x, y, 32, 32);
        g.setFont(new Font("Verdana", 0, 50));
        if(resource.getColour(x, y) == 0xFF7F27){
            int xx = x;
            int yy = y;
            if(xx < 20) xx = 20;
            if(xx > 405) xx = 405;
            if(yy < 45) yy = 45;
            if(yy > 350) yy = 350;
            g.drawString("Collision!", xx, yy);
        }
        if(resource.getColour(x, y) == 0x3F48CC){
            g.drawString("Blue!", 250, 150);
        }
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Display display = new Display(640, 360);
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("Colour");
        frame.add(display);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        display.start();
    }
}
