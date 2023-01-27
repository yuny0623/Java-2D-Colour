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

    double x = 80;
    double y = 80;

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(resource.loadImage("res/level.png"), 0, 0, resource.getWidth(), resource.getHeight(), null);
        g.fillRect((int) x, (int) y, 32, 32);
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
