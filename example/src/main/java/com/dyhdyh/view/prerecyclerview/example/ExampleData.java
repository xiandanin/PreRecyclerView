package com.dyhdyh.view.prerecyclerview.example;


import android.graphics.Color;

import com.dyhdyh.view.prerecyclerview.example.adapter.ExampleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * author  dengyuhan
 * created 2017/7/4 15:34
 */
public class ExampleData {

    public static int randomColor() {
        int r = new Random().nextInt(95) + 160;
        int g = new Random().nextInt(95) + 160;
        int b = new Random().nextInt(95) + 160;
        return Color.rgb(r, g, b);
    }

    public static List<ExampleModel> random(int count) {
        List<ExampleModel> models = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Random random = new Random();
            ExampleModel model = new ExampleModel();
            model.setTitle("Item " + i);
            model.setImage(IMAGES[random.nextInt(IMAGES.length)]);
            model.setColor(randomColor());
            models.add(model);
        }
        return models;
    }

    private static final String[] IMAGES = new String[]{
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/0D/ChMkJ1gyrq2IQiRcAAxu-yQ2xsMAAX8QQM6NvgADG8T113.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0A/0C/ChMkJlkml2uIesS-AANfwoUyLFQAAcmwwKyNMAAA1_a251.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/08/ChMkJ1auzJOIOhDpAADr5asmhJ8AAH8_ACy330AAOv9593.png",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/08/ChMkJ1auzKKIFoweAAUEw5cfoHsAAH8_QGWdRUABQTb292.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/01/04/ChMkJ1g2YQeIbYQRABTlVmUrO4AAAYB0gGpqwMAFOVu246.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/09/ChMkJlauzayIN_OiAAT7FymPjGEAAH9HQGYs1AABPsv049.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/01/ChMkJleYmWmICTv0AA1WDC_l7BQAAT5kgEopawADVYk806.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0E/07/ChMkJ1eXS-CIEttfAAZZAk8SBAAAAT3GwE2q6AABlka401.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/05/0A/ChMkJ1d99iSIHh7jAAzf0hzuF2QAATT7gFwwDsADN_q930.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0A/00/ChMkJllRwNCIGYOJAAGec-06_NkAAdjmQEwcFwAAZ6L122.jpg",
            "http://bizhi.zhuoku.com/2016/11/14/Kawasaki/2017-Kawasaki-Ninja-ZX-10RR-01.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/00/0F/ChMkJ1imoXiILZOzAALYASwv-sEAAaANAKgcPkAAtgZ252.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/09/ChMkJlauzaqIIVVVAAYZO0_rS14AAH9HQDLRycABhlT039.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/09/ChMkJ1auzjqIBkk5AAH8QmK-1foAAH9LQJPIRIAAfxa994.png",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/04/02/ChMkJlbiMTmIWDhOAAtPyLVvsBMAANPYAM2icYAC0_g470.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/09/ChMkJlauzYiIIU3UAFNltyuxFDQAAH9GAKkOzMAU2XP642.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/0F/07/ChMkJ1auyjeId0FuAAr5XKgWE68AAH8rgOmFj4ACvl0405.jpg",
            "http://desk.fd.zol-img.com.cn/t_s1280x800c5/g5/M00/05/07/ChMkJleprBSIcf7rAAmFKP43miEAAUS_wNrOtsACYVA923.jpg",
    };
    private static final String[] SMALL_IMAGES = new String[]{
            "http://v1.qzone.cc/avatar/201707/04/16/08/595b4d03b0d5e348.jpg!200x200.jpg",
            "http://img2.woyaogexing.com/2017/05/23/9d57a6b09cad3760!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/bf98e9790f448dd0!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/7dfd1e1fbd1f6b4f!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/43c3f01a4a829c58!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/3910a2a1d040b7c5!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/9285e9833cb62de2!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/96d26446cf0621ba!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/0fd7e4ed7ee3f40b!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/6328826168a72175!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/bc223394844abbfb!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/e84a81097d61980f!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/1daa34c891abe5c9!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/e3101294f9e53434!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/459763becf4d7a44!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/5ef9d58959508fd7!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/b3385fd1e61f037c!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/68e53fc9391d90cd!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/05/23/bc7453ffe6c9c699!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/780f98a73cd46ac2!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/10e60c77da9afe12!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/576dcd453a6432c6!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/a355e098f3d664a0!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/ed180fe2514b913f!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/1467be8102d4efe3!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/3171dce95bd0f3f8!400x400_big.jpg",
            "http://img2.woyaogexing.com/2017/06/28/e65da07564449637!400x400_big.jpg",
    };

}
