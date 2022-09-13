package Game;

import java.awt.*;
import java.util.Random;

public class Asteroid extends Entity{
    public int size = 0;
    // 1: Large, 2: Medium, 3: Small

    public Asteroid(int size){//Define the method Asteroid, and int size
        this.size = size;
        Random rnd= new Random();//Create an variable based on the Random class, named rnd
        this.xcenter = 1080*rnd.nextDouble();
        this.ycenter = 720*rnd.nextDouble();//random appear location
        double speed = 0;
        if(size==1){
            speed = 1;
            this.radius = 100;
        }else if(size==2){
            speed = 1.5;
            this.radius = 50;
        }else if(size==3){
            speed = 2;
            this.radius = 30;//define the speed and radius of different size asteroid
        }
        this.xv = 2.*speed*(0.5-rnd.nextDouble());
        this.yv = 2.*speed*(0.5-rnd.nextDouble());//define the velocity in xy direction
    }

    public Asteroid(int size, double xcenter, double ycenter){//Define a constructor for the class. This constructor take three double as parameters and use them to set size xcenter ycenter.
        this.size = size;
        Random rnd= new Random();;//Create an variable based on the Random class, named rnd
        this.xcenter = xcenter;
        this.ycenter = ycenter;//store
        double speed = 0;
        if(size==1){
            speed = 1;
            this.radius = 100;
        }else if(size==2){
            speed = 1.5;
            this.radius = 50;
        }else if(size==3){
            speed = 2;
            this.radius = 30;
        }
        this.xv = 2.*speed*(0.5-rnd.nextDouble());
        this.yv = 2.*speed*(0.5-rnd.nextDouble());
    }

    public void draw(Graphics g){
        this.move();
        int[] xPoly = {new Double(this.radius*Math.cos(Math.toRadians(30.)) + xcenter).intValue(), new Double(4./5.*this.radius*Math.cos(Math.toRadians(90.)) + xcenter).intValue(),
                new Double(this.radius*Math.cos(Math.toRadians(180.)) + xcenter).intValue(), new Double(2./5.*this.radius*Math.cos(Math.toRadians(210.)) + xcenter).intValue(),
                new Double(this.radius*Math.cos(Math.toRadians(270.)) + xcenter).intValue(), new Double(3./5.*this.radius*Math.cos(Math.toRadians(345.)) + xcenter).intValue()};
        int[] yPoly = {new Double(this.radius*Math.sin(Math.toRadians(30.)) + ycenter).intValue(), new Double(4./5.*this.radius*Math.sin(Math.toRadians(90.)) + ycenter).intValue(),
                new Double(this.radius*Math.sin(Math.toRadians(180.)) + ycenter).intValue(), new Double(2./5.*this.radius*Math.sin(Math.toRadians(210.)) + ycenter).intValue(),
                new Double(this.radius*Math.sin(Math.toRadians(270.)) + ycenter).intValue(), new Double(3./5.*this.radius*Math.sin(Math.toRadians(345.)) + ycenter).intValue()};

        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);//draw a polygon
        g.setColor(new Color(100, 100, 100));
        g.fillPolygon(poly);//fill it with colour
        g.setColor(new Color(0, 0, 0));
    }
}
