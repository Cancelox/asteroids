package Game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Ship extends Entity{
    public double direction = 0.;//initial the direction, which is a double variable
    private double speed = 0.;//initial the speed, which is a double variable
    public Ship(){
        Random rnd= new Random();//Create an variable based on the Random class, named rnd
        this.xcenter = 1080./2.;
        this.ycenter = 720./2.;
        this.radius = 30.;
        this.xv = 0.;
        this.yv = 0.;
        speed = 0.1;//Store the variable
    }

    public void rotateC(){//Define the method rotateC
        this.direction -= 2.;
    }

    public void rotateA(){
        this.direction += 2.;
    }

    public void accelerate(){
        this.xv += this.speed * Math.cos(Math.toRadians(270.+this.direction));
        this.yv += this.speed * Math.sin(Math.toRadians(270.+this.direction));//accelerate
    }


    public void draw(Graphics g){

        this.move();
        int[] xPoly = {new Double(this.radius*Math.cos(Math.toRadians(270.+this.direction)) + xcenter).intValue(),
                new Double(this.radius*Math.cos(Math.toRadians(120.+this.direction)) + xcenter).intValue(),
                new Double(this.radius*Math.cos(Math.toRadians(60.+this.direction)) + xcenter).intValue()};
        int[] yPoly = {new Double(this.radius*Math.sin(Math.toRadians(270.+this.direction)) + ycenter).intValue(),
                new Double(this.radius*Math.sin(Math.toRadians(120.+this.direction)) + ycenter).intValue(),
                new Double(this.radius*Math.sin(Math.toRadians(60.+this.direction)) + ycenter).intValue()};

        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g.setColor(new Color(0, 0, 255));
        g.fillPolygon(poly);
        g.setColor(new Color(0, 0, 0));
    }
}
