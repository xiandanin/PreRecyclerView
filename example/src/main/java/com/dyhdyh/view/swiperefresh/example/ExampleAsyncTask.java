package com.dyhdyh.view.swiperefresh.example;

import android.os.AsyncTask;

import com.dyhdyh.view.swiperefresh.example.adapter.ExampleModel;

import java.util.List;
import java.util.Random;

/**
 * 模拟 请求获取数据
 * author  dengyuhan
 * created 2017/7/6 18:03
 */
public class ExampleAsyncTask extends AsyncTask<Integer, Void, List<ExampleModel>> {


    @Override
    protected List<ExampleModel> doInBackground(Integer... params) {
        try {
            Thread.sleep(new Random().nextInt(2000)+1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ExampleData.random(params[0]);
    }

}
