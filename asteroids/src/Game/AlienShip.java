package Game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class AlienShip extends Entity{
    public double direction = 0.;//initial the direction, which is a double variable
    private double speed = 0.;//initial the speed, which is a double variable
    public boolean active = false;//initial the active, a boolean variable
    Random rnd= new Random();//Create an variable based on the Random class, named rnd
    public AlienShip(){//Define the method AlienShip
        this.xcenter = 1080./2.;
        this.ycenter = 720./2.;
        this.radius = 40.;
        this.xv = 0.;
        this.yv = 0.;
        speed = 3.;
        active = false;//Store the variable
    }

    public void activate(){//Define the method activate

        this.xcenter = 0.2;
        this.ycenter = rnd.nextDouble();//Call the next random nextDouble
//        this.ycenter = 0.1;
        this.xv = speed*rnd.nextDouble();
        this.yv = speed*(0.5-rnd.nextDouble())*2.;
    }

    public boolean isOut(){//See that if the alienship out of the screen
        if(this.xcenter > 0 && this.xcenter < 1080 && this.ycenter > 0 && this.ycenter < 720)
            return false;
        else
            return true;
    }



    public void draw(Graphics g){//draw Graphics g

        this.move();
        int[] xPoly = {new Double(this.radius * Math.cos(Math.toRadians(0. + this.direction)) + xcenter).intValue(),
                new Double(0.3 * this.radius * Math.cos(Math.toRadians(45. + this.direction)) + xcenter).intValue(),
                new Double(0.5 * this.radius * Math.cos(Math.toRadians(70. + this.direction)) + xcenter).intValue(),
                new Double(0.5 * this.radius * Math.cos(Math.toRadians(110. + this.direction)) + xcenter).intValue(),
                new Double(0.3 * this.radius * Math.cos(Math.toRadians(135. + this.direction)) + xcenter).intValue(),
                new Double(this.radius * Math.cos(Math.toRadians(180. + this.direction)) + xcenter).intValue(),
                new Double(0.3 * this.radius * Math.cos(Math.toRadians(225. + this.direction)) + xcenter).intValue(),
                new Double(0.5 * this.radius * Math.cos(Math.toRadians(250. + this.direction)) + xcenter).intValue(),
                new Double(0.5 * this.radius * Math.cos(Math.toRadians(290. + this.direction)) + xcenter).intValue(),
                new Double(0.3 * this.radius * Math.cos(Math.toRadians(315. + this.direction)) + xcenter).intValue(),
        };
        int[] yPoly = {new Double(this.radius * Math.sin(Math.toRadians(0. + this.direction)) + ycenter).intValue(),
                new Double(0.3 * this.radius * Math.sin(Math.toRadians(45. + this.direction)) + ycenter).intValue(),
                new Double(0.5 * this.radius * Math.sin(Math.toRadians(70. + this.direction)) + ycenter).intValue(),
                new Double(0.5 * this.radius * Math.sin(Math.toRadians(110. + this.direction)) + ycenter).intValue(),
                new Double(0.3 * this.radius * Math.sin(Math.toRadians(135. + this.direction)) + ycenter).intValue(),
                new Double(this.radius * Math.sin(Math.toRadians(180. + this.direction)) + ycenter).intValue(),
                new Double(0.3 * this.radius * Math.sin(Math.toRadians(225. + this.direction)) + ycenter).intValue(),
                new Double(0.5 * this.radius * Math.sin(Math.toRadians(250. + this.direction)) + ycenter).intValue(),
                new Double(0.5 * this.radius * Math.sin(Math.toRadians(290. + this.direction)) + ycenter).intValue(),
                new Double(0.3 * this.radius * Math.sin(Math.toRadians(315. + this.direction)) + ycenter).intValue(),
        };

        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);//draw a polygon
        g.setColor(new Color(255, 0, 255));
        g.fillPolygon(poly);//fill it with Magenta
        g.setColor(new Color(0, 0, 0));
    }
}
