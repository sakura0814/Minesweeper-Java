package com.sakura.window;

import com.sakura.top.MapTop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author sakura
 * @create 2022-06-15-15:15
 */
public class GameWin extends JFrame {
    int width = 2 * GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH;
    int height = 4 * GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH;

    // 新建一个画布解决窗口闪动问题
    Image offScreenImage = null;

    MapButtom mapButtom = new MapButtom();
    MapTop mapTop = new MapTop();

    GameSelect gameSelect = new GameSelect();

    boolean begin = false;

    // 设置窗口
    void launch(){
        GameUtil.START_TIME = System.currentTimeMillis();
        // 窗口是否可见
        this.setVisible(true);
        // 窗口大小
        if(GameUtil.STATE == 3){
            this.setSize(500,500);
        }else{
            this.setSize(width,height);
        }
        // 窗口位置:居中显示
        this.setLocationRelativeTo(null);
        // 窗口标题
        this.setTitle("小何定制版扫雷游戏");
        // 窗口关闭方法
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (GameUtil.STATE){
                    case 0:
                        // 鼠标左键点击
                        if(e.getButton() == 1){
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.LEFT = true;
                        }
                        // 鼠标右键点击
                        if(e.getButton() == 3){
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.RIGHT = true;
                        }
                        break;
                    case 1:
                    case 2:
                        if(e.getButton() == 1){
                            if(e.getX() > GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2) &&
                                    e.getX() < GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2) + GameUtil.OFFSET
                                     && e.getY() > GameUtil.OFFSET
                                     && e.getY() < GameUtil.OFFSET + GameUtil.SQUARE_LENGTH){
                                mapButtom.reGame();
                                mapTop.reGame();
                                GameUtil.FLAG_NUM = 0;
                                GameUtil.START_TIME = System.currentTimeMillis();
                                GameUtil.STATE = 0;
                            }
                        }
                        if((e.getModifiers() & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK){
//                        if(e.getButton() == MouseEvent.BUTTON2){
                            GameUtil.STATE = 3;
                            begin = true;
                        }
                        break;
                    case 3:
                        if(e.getButton() == 1){
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            begin = gameSelect.hard();
                        }
                    default:

                }
            }
        });

        while(true){
            repaint();
            isBegin();
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void isBegin(){
        if(begin){
            begin = false;
            gameSelect.hard(GameUtil.level);
            GameWin win = new GameWin();
            GameUtil.START_TIME = System.currentTimeMillis();
            mapButtom.reGame();
            mapTop.reGame();
            GameUtil.FLAG_NUM = 0;
            win.launch();
        }
    }

    // 雷区绘制
    @Override
    public void paint(Graphics g) {
        if(GameUtil.STATE == 3){
            g.setColor(Color.white);
            g.fillRect(0,0,500,500);
            gameSelect.paintSelf(g);
        }else{

            // 双缓存技术
            offScreenImage = this.createImage(width,height);
            Graphics gImage = offScreenImage.getGraphics();
            // 绘制
            mapButtom.paintSelf(gImage);
            mapTop.paintSelf(gImage);
            g.drawImage(offScreenImage,0,0,null);
        }
    }

    // 入口函数
    public static void main(String[] args){
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
