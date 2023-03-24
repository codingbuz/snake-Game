package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int b_height=400;
    int b_width=400;
    int dots_size=10;
    int max_dots=1600;
    int dots;
    int apple_x;
    int apple_y;
    int x[]=new int[max_dots];
    int y[]=new int[max_dots];



    Image body,head,apple;
    Timer timer;
    int delay=200;

    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;

    boolean downDirection=false;

    boolean inGame=true;

    Board(){
        TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(b_width,b_height));
        setBackground(Color.BLACK);
        initGame();
        loadImage();
       // locateApple();
    }
    public void initGame(){
       dots=3;
       // position of snake
        x[0]=350;
        y[0]=350;
       for(int i=0;i<dots;i++){
           x[i]=x[0]+dots_size*i;
           y[i]=y[0];
       }
      locateApple();
       timer=new Timer(delay,this);
       timer.start();
    }
    // load the image from the resources folder
    public  void loadImage(){
        ImageIcon bodyIcon=new ImageIcon("src/resources/dot.png");
        body=bodyIcon.getImage();
        ImageIcon headIcon=new ImageIcon("src/resources/head.png");
        head=headIcon.getImage();
        ImageIcon appleIcon=new ImageIcon("src/resources/apple.png");
        apple=appleIcon.getImage();

    }
    // draw the image at snake and apple position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    public  void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this) ;

            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head,x[0],y[0],this);
                }else{
                    g.drawImage(body,x[i],y[i],this);
                }
            }
    }else{
            gameOver(g);
            timer.stop();
        }
    }

    public void locateApple(){
        apple_x=((int)(Math.random()*39))*dots_size;
        apple_y=((int)(Math.random()*39))*dots_size;

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){

       if(inGame) {

           checkApple();
           checkCollision();
           move();
       }
         repaint();
    }
    public void move(){
        for(int i=dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]-=dots_size;
        }
        if(rightDirection){
            x[0]+=dots_size;
        }
        if(upDirection){
            y[0]-=dots_size;
        }
        if(downDirection){
            y[0]+=dots_size;
        }
    }
    // make eat food by the snake
    public  void checkApple(){
        if(apple_x==x[0] && apple_y==y[0]){
            dots++;
            locateApple();

        }
    }
    public void checkCollision(){
        //in body collision
        for(int i=0;i<dots;i++){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
                inGame=false;
            }
        }
        if(x[0]<0){
            inGame=false;
        }
        if(x[0]>b_width){
            inGame=false;

        }
        if(y[0]<0){
            inGame=false;
        }
        if(y[0]>b_height){
            inGame=false;
        }
    }
    public void gameOver(Graphics g){
        String msg="Game Over";
        int score=(dots-3)*10;
        String ans="Score:"+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(b_width-fontMetrics.stringWidth(msg))/2,b_height/4);
        g.drawString(ans,(b_width-fontMetrics.stringWidth(ans))/2,3*(b_height/4));

    }


    //implement control
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !rightDirection){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && !downDirection){
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && !upDirection){
                leftDirection=false;
                rightDirection=false;
                downDirection=true;
            }
        }
    }
}
