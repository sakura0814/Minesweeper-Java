package com.sakura.bottom;

import com.sakura.window.GameUtil;

/**
 * 初始化地雷
 * -1表示地雷
 * @author sakura
 * @create 2022-06-15-17:16
 */
public class BottomRay {
    // 存放坐标，每两个代表一个坐标
    static int[] rays = new int[GameUtil.NUM_RAY * 2];
    // 地雷坐标
    int x, y;
    // 是否放置
    boolean isPlace = true;

    // 生成地雷坐标

    public void newRay(){
        for(int i = 0;i < GameUtil.NUM_RAY * 2;i += 2){
            x = (int) (Math.random() * GameUtil.MAP_W + 1);//1-12
            y = (int) (Math.random() * GameUtil.MAP_H + 1);

            rays[i] = x;
            rays[i + 1] = y;

            // 判断坐标是否已经存在
            for(int j = 0;j < i;j += 2){
                if(x == rays[j] && y == rays[j + 1]){
                    i -= 2;
                    isPlace = false;
                    break;
                }
            }
            // 坐标放入数组
            if(isPlace){
                rays[i] = x;
                rays[i + 1] = y;
            }
            // 释放状态
            isPlace = true;
        }

        for(int i = 0;i < GameUtil.NUM_RAY * 2;i += 2){
            GameUtil.DATA_BOTTOM[rays[i]][rays[i + 1]] = -1;
        }
    }
}
