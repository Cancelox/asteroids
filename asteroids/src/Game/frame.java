package Game;
import javax.swing.*;

public class frame {
    public static void main(String[] args) {
        new frame(); // 主函数启动游戏
    }

    public frame() {
        JFrame f = new JFrame("Asteroids");
        Window w = new Window();
        f.setBounds(150,0,1080,720);//set the bounds of the game window
        f.add(w);//add new window()
        f.addKeyListener(w);//listen the key action
        f.setVisible(true);//visiable the frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//press X to close

    }
}
