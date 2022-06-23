package com.sakura.top;

import com.sakura.window.GameUtil;

import java.awt.*;

/**
 * 顶层地图类
 * 绘制顶层组件、判断逻辑
 * @author sakura
 * @create 2022-06-15-20:29
 */
public class MapTop {
    // 格子位置
    int temp_x = 0;
    int temp_y = 0;

    // 判断逻辑
    void logic(){
        if(GameUtil.MOUSE_X > GameUtil.OFFSET && GameUtil.MOUSE_Y > 3 * GameUtil.OFFSET){
            temp_x = (GameUtil.MOUSE_X - GameUtil.OFFSET) / GameUtil.SQUARE_LENGTH + 1;
            temp_y = (GameUtil.MOUSE_Y - 3 * GameUtil.OFFSET) / GameUtil.SQUARE_LENGTH + 1;
        }
        if(temp_x > 0 && temp_x <= GameUtil.MAP_W && temp_y > 0 && temp_y <= GameUtil.MAP_H){
            // 左键被点击
            if(GameUtil.LEFT){
                if(GameUtil.DATA_TOP[temp_x][temp_y] == 0){
                    GameUtil.DATA_TOP[temp_x][temp_y] = -1;
                }
                spaceOpen(temp_x,temp_y);
                GameUtil.LEFT = false;
            }
            // 右键被点击
            if(GameUtil.RIGHT){
                // 覆盖就插旗，插旗就取消
                if(GameUtil.DATA_TOP[temp_x][temp_y] == 0){
                    GameUtil.DATA_TOP[temp_x][temp_y] = 1;
                    GameUtil.FLAG_NUM++;
                }else if(GameUtil.DATA_TOP[temp_x][temp_y] == 1){
                    GameUtil.DATA_TOP[temp_x][temp_y] = 0;
                    GameUtil.FLAG_NUM--;
                }else if(GameUtil.DATA_TOP[temp_x][temp_y] == -1){
                    numOpen(temp_x,temp_y);
                }
                GameUtil.RIGHT = false;
            }
        }
        booms();
        victory();
    }

    // 绘制方法
    public void paintSelf(Graphics g){
        logic();
        // 覆盖
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_TOP[i][j] == 0){
                    g.drawImage(GameUtil.top,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
            }
        }
        // 插旗
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_TOP[i][j] == 1){
                    g.drawImage(GameUtil.flag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
            }
        }
        // 插错旗
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_TOP[i][j] == 2){
                    g.drawImage(GameUtil.noflag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
            }
        }
    }

    // 打开空格
    void spaceOpen(int x,int y){
        if(GameUtil.DATA_BOTTOM[x][y] == 0){
            for(int i = x - 1;i <= x + 1;i++){
                for(int j = y - 1;j <= y + 1;j++){
                    if(GameUtil.DATA_TOP[i][j] != -1){
                        if(GameUtil.DATA_TOP[i][j] == 1) GameUtil.FLAG_NUM--;
                        GameUtil.DATA_TOP[i][j] = -1;
                        if(i > 0 && i <= GameUtil.MAP_W && j > 0 && j <= GameUtil.MAP_H){
                            spaceOpen(i,j);
                        }
                    }
                }
            }
        }
    }

    // 数字翻开
    void numOpen(int x,int y){
        // 记录旗数
        int count = 0;
        if(GameUtil.DATA_BOTTOM[x][y] > 0){
            for(int i = x - 1;i <= x + 1;i++){
                for(int j = y - 1;j <= y + 1;j++){
                    if(GameUtil.DATA_TOP[i][j] == 1){
                        count++;
                    }
                }
            }
        }
        if(count == GameUtil.DATA_BOTTOM[x][y]){
            for(int i = x - 1;i <= x + 1;i++){
                for(int j = y - 1;j <= y + 1;j++){
                    if(GameUtil.DATA_TOP[i][j] != 1){
                        GameUtil.DATA_TOP[i][j] = -1;
                    }
                    if(i > 0 && i <= GameUtil.MAP_W && j > 0 && j <= GameUtil.MAP_H){
                        spaceOpen(i,j);
                    }
                }
            }
        }
    }

    // 失败判定 true失败
    boolean booms(){
        if(GameUtil.FLAG_NUM == GameUtil.NUM_RAY){
            for(int i = 1;i <= GameUtil.MAP_W;i++){
                for(int j = 1;j <= GameUtil.MAP_H;j++) {
                    if(GameUtil.DATA_TOP[i][j] == 0){
                        GameUtil.DATA_TOP[i][j] = -1;
                    }
                }
            }
        }
        // 顶层无覆盖且底层为雷判定为失败
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] == -1){
                    GameUtil.STATE = 2;
                    seeBoom();
                    return true;
                }
            }
        }
        return false;
    }

    // 失败显示所有雷
    void seeBoom(){
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                // 底层是雷，顶层不是旗，显示
                if(GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] != 1){
                    GameUtil.DATA_TOP[i][j] = -1;
                }
                // 底层不是雷，顶层是旗，显示插错旗
                if(GameUtil.DATA_BOTTOM[i][j] != -1 && GameUtil.DATA_TOP[i][j] == 1){
                    GameUtil.DATA_TOP[i][j] = 2;
                }
            }
        }
    }

    // 判断胜利
    boolean victory(){
        // 统计未打开格子数
        int count = 0;
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_TOP[i][j] != -1) count++;
            }
        }
        if(count == GameUtil.NUM_RAY){
            GameUtil.STATE = 1;
            for(int i = 1;i <= GameUtil.MAP_W;i++){
                for(int j = 1;j <= GameUtil.MAP_H;j++){
                    // 未翻开，变成旗
                    if(GameUtil.DATA_TOP[i][j] == 0){
                        GameUtil.DATA_TOP[i][j] = 1;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // 重置游戏
    public void reGame(){
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                GameUtil.DATA_TOP[i][j] = 0;
            }
        }
    }
}
