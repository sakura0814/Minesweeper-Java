package com.sakura.window;

import java.awt.*;

/**
 * 工具类
 * 存放静态参数
 * 工具方法
 * @author sakura
 * @create 2022-06-15-16:20
 */
public class GameUtil {
    // 地雷数量
    public static int NUM_RAY = 100;
    // 地图的宽:横着的格子数量
    public static int MAP_W = 36;
    // 地图的高
    public static int MAP_H = 17;
    // 雷区偏移量
    public static int OFFSET = 45;
    // 格子边长
    public static int SQUARE_LENGTH = 50;

    // 插旗数量
    public static int FLAG_NUM = 0;

    // 鼠标坐标
    public static int MOUSE_X;
    public static int MOUSE_Y;
    // 鼠标状态
    public static boolean LEFT = false;
    public static boolean RIGHT = false;

    // 游戏状态 0游戏中 1胜利 2失败 3难度选择
    public static int STATE = 3;
    // 游戏难度
    public static int level;

    // 倒计时
    public static long START_TIME;
    public static long END_TIME;


    // 底层元素 -1雷 0空 1-8表示对应数字
    public static int[][] DATA_BOTTOM = new int[MAP_W + 2][MAP_H + 2];

    // 顶层元素 -1无覆盖 0覆盖 1插旗 2插错旗
    public static int[][] DATA_TOP = new int[MAP_W + 2][MAP_H + 2];

    // 载入图片
    public static Image lei = Toolkit.getDefaultToolkit().getImage("imgs/lei.png");
    public static Image top = Toolkit.getDefaultToolkit().getImage("imgs/top.gif");
    public static Image flag = Toolkit.getDefaultToolkit().getImage("imgs/flag.gif");
    public static Image noflag = Toolkit.getDefaultToolkit().getImage("imgs/noflag.png");

    public static Image face = Toolkit.getDefaultToolkit().getImage("imgs/face.png");
    public static Image over = Toolkit.getDefaultToolkit().getImage("imgs/over.png");
    public static Image win = Toolkit.getDefaultToolkit().getImage("imgs/win.png");

    public static Image[] nums = new Image[9];
    static {
        for(int i = 1;i <= 8;i++){
            nums[i] = Toolkit.getDefaultToolkit().getImage("imgs/num/" + i + ".png");
        }
    }

    public static void drawWord(Graphics g,String str,int x,int y,int size,Color color){
        // 绘制数字
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str,x,y);
    }
}
