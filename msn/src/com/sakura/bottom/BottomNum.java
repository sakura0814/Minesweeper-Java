package com.sakura.bottom;

import com.sakura.window.GameUtil;

/**
 * 底层数字类
 * @author sakura
 * @create 2022-06-15-20:08
 */
public class BottomNum {

    public void newNum(){
        for(int i = 1;i <= GameUtil.MAP_W;i++){
            for(int j = 1;j <= GameUtil.MAP_H;j++){
                if(GameUtil.DATA_BOTTOM[i][j] == -1){
                    // 如果该点为雷，则遍历附近的九宫格区域
                    for(int p = i - 1;p <= i + 1;p++){
                        for(int q = j - 1;q <= j + 1;q++){
                            if(GameUtil.DATA_BOTTOM[p][q] >= 0){
                                GameUtil.DATA_BOTTOM[p][q]++;
                            }
                        }
                    }
                }
            }
        }
    }
}
