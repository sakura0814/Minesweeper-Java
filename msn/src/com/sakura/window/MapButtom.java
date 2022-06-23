package com.sakura.window;

import com.sakura.bottom.BottomNum;
import com.sakura.bottom.BottomRay;

import java.awt.*;

/**
 * 底层地图，负责绘制游戏相关的组件
 * @author sakura
 * @create 2022-06-15-15:52
 */
public class MapButtom {
    BottomRay bottomRay = new BottomRay();
    BottomNum bottomNum = new BottomNum();
    {
        bottomRay.newRay();
        bottomNum.newNum();
    }

    // 重置游戏
    void reGame(){
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                GameUtil.DATA_BOTTOM[i][j] = 0;
            }
        }
        // 重新生成雷和数字
        bottomRay.newRay();
        bottomNum.newNum();
    }

    // 绘制方法
    void paintSelf(Graphics g){
        g.setColor(Color.red);
        // 画竖线
        for(int i = 0;i <= GameUtil.MAP_W;i++){
            g.drawLine(GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET,
                    GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    3 * GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH);
        }
        // 画横线
        for(int i = 0;i <= GameUtil.MAP_H;i++){
            g.drawLine(GameUtil.OFFSET,
                    3*GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH,
                    3 * GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH);
        }
        // 雷
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_BOTTOM[i][j] == -1){
                    g.drawImage(GameUtil.lei,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
            }
        }
        // 数字
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_BOTTOM[i][j] >= 0){
                    g.drawImage(GameUtil.nums[GameUtil.DATA_BOTTOM[i][j]],
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 15,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 5,
                            null);
                }
            }
        }
        // 绘制数字 剩余雷数
        GameUtil.drawWord(g,"" + (GameUtil.NUM_RAY - GameUtil.FLAG_NUM),GameUtil.OFFSET,2 * GameUtil.OFFSET,30,Color.red);
        // 绘制数字 时间
        GameUtil.drawWord(g,"" + (GameUtil.END_TIME - GameUtil.START_TIME) / 1000,GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W - 1),
                2 * GameUtil.OFFSET,30,Color.red);

        // 状态
        switch (GameUtil.STATE){
            case 0:
                GameUtil.END_TIME = System.currentTimeMillis();
                g.drawImage(GameUtil.face,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 1:
                g.drawImage(GameUtil.win,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 2:
                g.drawImage(GameUtil.over,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2),
                        GameUtil.OFFSET,
                        null);
                break;
        }
    }
}
