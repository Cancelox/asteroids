package Game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;


public class Window extends JPanel implements KeyListener, ActionListener{
    // 初始化关卡信息
    private int score = 0;
    private int lives = 3;
    private int level = 0;
    // 实例化
    boolean[] key = new boolean[3];

    Ship s = new Ship(); 
    AlienShip as = new AlienShip(); 
    Random rnd = new Random();
    HashMap<Integer, Bullets> bulletsHashMap = new HashMap<>(); // 己方子弹
    HashMap<Integer, Bullets> alienBulletsHashMap = new HashMap<>(); // 敌方子弹
    HashMap<Integer, Asteroid> asteroidsHashMap = new HashMap<>(); // 小行星
    // 初始化参数
    int bulletsId = 0; //number of bullets
    int asteroidId = 0; //number of asteroids
    int alienShotDelay = 0; //delay of fire
    int alienBulletsId = 0; //number of bullets fired from alien ship
    
    public Timer t = new Timer(20,this); // 20ms刷新一次
    

    public Window(){
        score = 0;
        lives = 3;
        level = 1;
        repaint();//repaint the window of the game
        startNewLevel(level);
        t.start();
    }

    public void startNewLevel(int level){ // 开始新关卡
        // 清除所有上关留下的信息
        bulletsHashMap.clear(); 
        asteroidsHashMap.clear();
        alienBulletsHashMap.clear();
        alienShotDelay = 0;
        alienBulletsId = 0;
        as.active = false; // 每一关中敌方飞船不能第一个出现
        bulletsId = 0;
        asteroidId = 0;
        // 每关加新的小行星
        for(int i = 0; i < level; ++i){
            asteroidsHashMap.put(asteroidId, new Asteroid(1)); //value:asteroidId key:Asteroid
            asteroidId++;
        }
    }
    // 实现飞船瞬移
    private void moveShipSafe(){
        boolean flag = false;
        while(!flag) {
            // 将飞船转移到一个随机位置
            s.xcenter = 1080 * rnd.nextDouble();
            s.ycenter = 720 * rnd.nextDouble();
            boolean crash = false;
            // 遍历所有小行星，如果飞船随机的坐标碰上某一个小行星，则代表坠毁，退出循环
            for (HashMap.Entry<Integer, Asteroid> entry : asteroidsHashMap.entrySet()) {
                if(entry.getValue().isCrash(s)) {
                    crash = true;
                    continue;
                }
            }
            // 随机一次后，如果飞机处于坠毁状态，则重新选择位置，否则结束循环
            flag = !crash;
        }
    }

    public void paintComponent(Graphics g){ // 用于在画面上画出实体的函数
        super.paintComponent(g); // 继承JComponent

        try {
            for (HashMap.Entry<Integer, Bullets> entry : bulletsHashMap.entrySet()) {//traverse bulletsHashMap
                if (entry.getValue().isOut()) { // 如果子弹出界了，就将其移出hashmap
                    bulletsHashMap.remove(entry.getKey());
                } else {
                    entry.getValue().draw(g); // 否则在画面上画出它
                }
            }
        }catch (Exception e){ //catch exceptions

        }
        try {
            for (HashMap.Entry<Integer, Bullets> entry : alienBulletsHashMap.entrySet()) {//traverse alienBulletsHashMap
                if (entry.getValue().isOut()) {//if the alien's bullet is out of bounds
                    alienBulletsHashMap.remove(entry.getKey());//remove bullets from the bulletsHashMap
                } else {
                    entry.getValue().draw(g);//keep on drawing the next location the alien's bullet will reach
                }
            }
        }catch (Exception e){//catch exceptions

        }
        try {
            for (HashMap.Entry<Integer, Asteroid> entry : asteroidsHashMap.entrySet()) {
                entry.getValue().draw(g);//keep on drawing the next location the asteroid will reach
            }
        }catch (Exception e){//catch exceptions

        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));//set the font of UI
        g.drawString("Level: " + this.level, 10, 30);
        g.drawString("Lives: " + this.lives, 10, 50);
        g.drawString("Score: " + this.score, 10, 70);
        s.draw(g);
        if(as.active)//if alien ship is active
            as.draw(g);//draw alien ship

    }
    @Override//because KeyEvent e extends subclass,so we need @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {//ship controller--press
        if(e.getKeyCode()==32){//press space
            bulletsHashMap.put(new Integer(bulletsId), new Bullets(s.xcenter, s.ycenter, s.direction, "ship"));
            bulletsId++;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){//press left
            key[0]=true;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){//press right
            key[1]=true;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){//press up
            key[2]=true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {//ship controller--release
        if(e.getKeyCode()==KeyEvent.VK_LEFT){//release left
            key[0]=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){//release right
            key[1]=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){//release up
            key[2]=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN){//when release down, run moveShipSafe() method
            moveShipSafe();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {//action of the game
        try {
            for (HashMap.Entry<Integer, Asteroid> entry : asteroidsHashMap.entrySet()) {//traverse the asteroidsHashMap
                Asteroid now = entry.getValue();
                if(s.isCrash(now)){//if crush
                    lives--;//life-1
                    moveShipSafe();//reset ship to a safe place
                    if (lives == 0){//if player died, restart the game
                        score = 0;
                        lives = 3;
                        level = 1;
                        startNewLevel(1);
                        s = new Ship();
                    }
                }
                for (HashMap.Entry<Integer, Bullets> bentry : bulletsHashMap.entrySet()) {//traverse the bulletHashMap
                    if(as.isCrash(bentry.getValue()) && as.active){//if alien ship appears and hit by bullets
                        as.active = false;//destroy the alien ship
                        score += 1000;//score+1000
                    }
                    if(now.isCrash(bentry.getValue())){//if asteroids is hit by bullets
                        if(now.size == 1){//if size is large
                        	asteroidsHashMap.remove(entry.getKey());//remove this asteroids form asteroidsHashMap
                            asteroidId++;//create 2 medium size asteroids at present location
                            asteroidsHashMap.put(asteroidId, new Asteroid(2, now.xcenter, now.ycenter));
                            asteroidId++;
                            asteroidsHashMap.put(asteroidId, new Asteroid(2, now.xcenter, now.ycenter));
                            score+=100;//score+100 by destroy a large size asteroids
                        }else if(now.size == 2){//if size is medium
                        	asteroidsHashMap.remove(entry.getKey());//remove this asteroids form asteroidsHashMap
                            asteroidId++;//create 2 small size asteroids at present location
                            asteroidsHashMap.put(asteroidId, new Asteroid(3, now.xcenter, now.ycenter));
                            asteroidId++;
                            asteroidsHashMap.put(asteroidId, new Asteroid(3, now.xcenter, now.ycenter));
                            score+=200;//score+200 by destroy a medium size asteroids
                        }else{
                        	asteroidsHashMap.remove(entry.getKey());//remove this asteroids form asteroidsHashMap
                            score+=400;//score+400 by destroy a small size asteroids
                        }
                        
                        System.out.println("One asteroid is shoot");

                        break;//end the circulation
                    }
                }
            }
            for (HashMap.Entry<Integer, Bullets> entry : alienBulletsHashMap.entrySet()) {//traverse the alienbulletHashMap
                Bullets now = entry.getValue();
                if (s.isCrash(now)) {//if player hit by alien ship's bullet
                    lives--;//life-1
                    moveShipSafe();//reset ship to a safe place
                    if (lives == 0) {//if player died, restart the game
                        score = 0;
                        lives = 3;
                        level = 1;
                        startNewLevel(1);
                        s = new Ship();
                    }
                }
            }
        }catch (Exception ex){//catch exceptions

        }
        if(asteroidsHashMap.keySet().size()==0){//if all the asteroids are cleared
            level++;//step into the next level
            startNewLevel(level);
        }
        if(score > 10000){//if player reach 10000 points
            System.out.println("Gained a new life");
            score -= 10000;//score-10000
            lives += 1;//gain another life
        }
        if(as.active){//if there is an alien ship
            alienShotDelay++;//count
        }else{
            alienShotDelay = 0;
        }

        if(alienShotDelay == 100 && as.active){//if count reach 100 and alien ship is still alive
        	//alien ship fire a bullet to player
            if(as.xcenter < s.xcenter) {//method to check player's location and fire
                double direction = Math.atan((s.ycenter - as.ycenter) / (s.xcenter - as.xcenter));//retrun reverse tangent value
                alienBulletsHashMap.put(new Integer(alienBulletsId), new Bullets(as.xcenter, as.ycenter, Math.toDegrees(direction) + 90., "alien"));
            }else{
                double direction = Math.atan((s.ycenter - as.ycenter) / (s.xcenter - as.xcenter));
                alienBulletsHashMap.put(new Integer(alienBulletsId), new Bullets(as.xcenter, as.ycenter, Math.toDegrees(direction) - 90., "alien"));
            }
            alienBulletsId++;//alien bullet+1
            alienShotDelay = 0;//reset the count
        }

        if(rnd.nextDouble()*1000 < 1 && !as.active){//alien ship activate randomly
            System.out.println("Alien ship is activated");
            as.active = true;
            as.activate();
        }
        if(as.isOut()){//if alien ship move out from the bounds
            as.active = false;//clear the alien ship
        }

        repaint();
        update();
    }

    private void update() {//the controller method of player's ship
        if(key[0]) {//when key[0]==true,which means press left
            s.rotateC();
        }
        if(key[1]){//when key[1]==true,which means press right
            s.rotateA();
        }
        if(key[2]){//when key[2]==true,which means press up
            s.accelerate();
        }
    }
}
