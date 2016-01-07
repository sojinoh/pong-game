import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Motions extends JPanel implements KeyListener{
    private double p1x = 250;
    //player1 xposition
    private double p2x = 250;
    //player2 xposition
    private double xvelo1 = 0;
    //x velocity of player1
    private double xvelo2 = 0;
    //x velocity of player2
    private int realp1L = 0;
    private int realp1R = 0;
    private int realp2A = 0;
    private int realp2D = 0;
    //variable to check the real keyboard press
    private int p1s = 0;
    //score of the first player
    private int p2s = 0;
    //score of the first player
    private int timer = 300;
    //pause timer
    private Balls b;
    public Motions(){
        b = new Balls(300, 300, 200, 200);
        //creates the ball
    }
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(100, 100, 400, 400);
        g.setColor(Color.white);
        g.drawString("player 1: " + p1s,120, 480);
        g.drawString("player 2: " + p2s, 350, 480);
        //shows the score
        if(b.ypos == 500 - 15){
            //the ball reaches the wall under first player
            g.drawString("The Second Player Is The Winner", 220, 300);        
        }
        if(b.ypos == 100){
            //the ball reaches the wall above second player
            g.drawString("The First Player Is The Winner", 220, 300);   
        }
        //draws the second and first player
        g.setColor(Color.red);
        g.fillRect((int)p2x, 150, 100, 5);
        g.drawString("2nd player", (int)p2x, 170);
        g.setColor(Color.blue);
        g.fillRect((int)p1x, 450, 100, 5);
        g.drawString("1st player", (int)p1x, 470);
        //draws the ball
        b.paint(g);
    }
    public void update (long dt){
        if(b.ypos >= 150  && b.ypos <= 455){
            //if the ball does not go over the bar
            b.update(dt);
            //update the ball
            if((b.ypos + 15 >= 450) && (p1x - 15 < b.xpos && b.xpos < (p1x + 100))){
                //if the main ball is on player 1's bar
                b.yvel = -1.05 * b.yvel;
                //main ball changes the yvelocity (just the direction) and the ball moves 1.05 faster
                b.xvel += 1/10 * xvelo1;
                //1/10 of the bar's velocity is added to xvelocity of main ball
                b.ypos -= 2*(b.ypos+15-450);
                //position of the ball after hitting the bar
            }
            if((b.ypos <= 155) && (p2x - 15 < b.xpos && b.xpos < (p2x + 100))){
                //if the main ball is on player 2's bar
                b.yvel = -1.05 * b.yvel;
                //main ball changes the yvelocity (just the direction) and the ball moves 1.05 faster
                b.xvel += 1/10 * xvelo2;
                //1/10 of the bar's velocity is added to xvelocity of main ball
                b.ypos += 2*(155 - b.ypos);
                //position of the ball after hitting the bar
            }                    
            // change the position of the bar
            p1x += dt/1000.0 * xvelo1;
            p2x += dt/1000.0 * xvelo2;
            //preventing the bar from going off the wall
            if(p1x < 100){
                p1x = 100;
            }
            if(p1x > 400){
                p1x = 400;
            }
            if(p2x < 100){
                p2x = 100;
            }
            if(p2x > 400){
                p2x = 400;
            }
        }
        else if((100 <b.ypos && b.ypos< 150)  || (500-15 > b.ypos && b.ypos > 455)){
            //if the ball is between the wall and the bar, the ball continues to move
            b.update(dt);
        }
        else{
            //game is over and increase score
            if(timer == 300 && b.ypos == 100){
                //first player won 
                p1s += 100;
            }
            if(timer == 300 && b.ypos == 500-15){
                //second player won
                p2s += 100;
            }
            timer -= 5;
            //the game pauses until timer variable reaches 0
            if(timer == 0){
                //game starts again
                if(b.ypos == 100){
                    timer = 300;
                    b.ypos = 300;
                    b.xpos = 300;
                    b.xvel = 200;
                    b.yvel = 200;
                }
                if(b.ypos == 500-15){
                    timer = 300;
                    b.ypos = 300;
                    b.xpos = 300;
                    b.xvel = 200;
                    b.yvel = -200;
                }
            }
        }
    }
    public static void main(String [] args){
        JFrame jf = new JFrame("Motion Demo");
        Motions m = new Motions();
        jf.setSize(800,600);
        jf.add(m);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.addKeyListener(m);
        long prevframe = System.currentTimeMillis();
        while(true){
            long curframe = System.currentTimeMillis();
            if(curframe - prevframe < 20){//ideal frame time
                try{
                    Thread.sleep(20 - curframe + prevframe);
                }
                catch(Exception e){
                    
                }
            }
            else{
                 m.update(curframe - prevframe);
                 prevframe = curframe;
                 m.repaint();
             }
        }
    }
    public void keyPressed(KeyEvent e){
        //movement of the first player
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            realp1L = realp1L + 1;
            if(realp1L == 1){
                xvelo1 -= 500;
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            realp1R = realp1R + 1;
            if(realp1R == 1){
                xvelo1 += 500;
            }
        }
        //movement of the second player
        if(e.getKeyCode()==KeyEvent.VK_A){
            realp2A = realp2A + 1;
            if(realp2A == 1){
                xvelo2 -= 500;
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            realp2D = realp2D + 1;
            if(realp2D == 1){
                xvelo2 += 500;
            }
        }
    }
    public void keyReleased(KeyEvent e){
        //stops the movement
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            realp1L = 0;
            xvelo1 += 500;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            realp1R = 0;
            xvelo1 -= 500;
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            realp2A = 0;
            xvelo2 += 500;
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            realp2D = 0;
            xvelo2 -= 500;
        }
    }
    public void keyTyped(KeyEvent e){
        
    }
}
    