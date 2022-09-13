package Game;

import java.awt.*;

public abstract class Entity { // 构建Entity类
    public int pid; // id
    public double xcenter; // 中心点坐标
    public double ycenter;
    public double radius; // 半径（碰撞体积）
    public double xv; // 方向上的速度
    public double yv;

    public boolean isCrash(Entity other){ // 判断是否和other碰撞的函数，如果两实体碰撞体积重合（距离 <= 0），代表发生碰撞，返回True
        double distance = 0;
        distance = Math.pow(this.xcenter - other.xcenter, 2) + Math.pow(this.ycenter - other.ycenter, 2); // 求绝对距离
        distance = Math.sqrt(distance);
        if(distance > (this.radius + other.radius)){
            return false;
        }else{
            return true;
        }
    }

    public void move(){ // 移动函数，如果从边界出去，会从另一边回来
        xcenter += xv;
        ycenter += yv;
        if(xcenter < 0) xcenter = 1080;
        if(ycenter < 0) ycenter = 720;
        if(xcenter > 1080) xcenter = 0;
        if(ycenter > 720) ycenter = 0;
    }

    public abstract void draw(Graphics g); // 声明抽象的draw函数，放到子类再具体写（每个子类画的不一样）
}
