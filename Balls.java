import java.awt.*;
import javax.swing.*;
import java.util.Random;
public class Balls{
    public double xpos, ypos;
    public double xvel, yvel;
    //diameter of the ball is 15
    public Balls(double xpos, double ypos, double xvel, double yvel){
        this.xpos = xpos;
        this.ypos = ypos;
        this.xvel = xvel;
        this.yvel = yvel;
    }
    public void update(long dt){
        xpos += dt/1000.0 * xvel;
        ypos += dt/1000.0 * yvel;
        //get the position of the ball and check if the ball goes beyond the wall
        if(xpos < 100){
            xvel = -1 * xvel;
            xpos += 2*(100-xpos);
        }
        if(ypos < 100){
            yvel = 0;
            xvel = 0;
            ypos = 100;
            //if goes beyond the upper wall, it stops and the player1 wins
        }
        if(xpos > (500 - 15)){
            xvel = -1 * xvel;
            xpos -= 2*(xpos+15-500);
        }
        if(ypos > 500 - 15){
            yvel = 0;
            xvel = 0;
            ypos = 500 - 15;
            //if goes beyond the lower wall, it stops and the player2 wins
        }
    }
    public void paint(Graphics g){
        Random rng = new Random();
        g.setColor(new Color(rng.nextInt(255),rng.nextInt(255),rng.nextInt(255)));
        g.fillOval((int)xpos,(int)ypos,15,15);
    }
}