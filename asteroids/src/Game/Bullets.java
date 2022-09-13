package Game;

import java.awt.*;

public class Bullets extends Entity{ // 继承自entity的bullets类
    // 特有的参数：方向，速度，类型
    private double direction = 0.;
    private double speed = 0.;
    private String type = "";

    public Bullets(double xcenter, double ycenter, double direction, String type){ // 声明bullets类
        this.xcenter = xcenter;
        this.ycenter = ycenter;
        this.direction = direction;
        this.type = type;
        this.speed = 5.; // 总速度设定为5.0，分解到x和y
        this.xv = this.speed * Math.cos(Math.toRadians(270.+this.direction));
        this.yv = this.speed * Math.sin(Math.toRadians(270.+this.direction));
        this.radius = 4.; // 子弹大小半径
    }

    public void draw(Graphics g){//code to put color on bullets
        this.move();

        if(type == "ship") // 如果是自己的飞船发射的，用颜色1
            g.setColor(new Color(255, 100, 0));
        else // 如果是敌人的飞船发射的，用颜色2
            g.setColor(new Color(255, 0, 0));
        g.fillOval(new Double(this.xcenter - this.radius).intValue(),
                new Double(this.ycenter - this.radius).intValue(),
                new Double(this.radius*2).intValue(),
                new Double(this.radius*2).intValue());
        g.setColor(new Color(0, 0, 0));
    }

    @Override
    public void move(){ // 移动函数，重写Entity的move，使子弹出界就消失
        xcenter += xv;
        ycenter += yv;
    }

    public boolean isOut(){ // 判断实体是否出界的函数
        if(this.xcenter > 0 && this.xcenter < 1080 && this.ycenter > 0 && this.ycenter < 720)
            return false;
        else
            return true;
    }
}