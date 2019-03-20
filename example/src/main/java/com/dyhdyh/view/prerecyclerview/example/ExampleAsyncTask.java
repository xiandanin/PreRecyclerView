package com.dyhdyh.view.prerecyclerview.example;


import android.os.AsyncTask;

import com.dyhdyh.view.prerecyclerview.example.adapter.ExampleModel;

import java.util.List;

/**
 * 模拟 请求获取数据
 * author  dengyuhan
 * created 2017/7/6 18:03
 */
public class ExampleAsyncTask extends AsyncTask<Integer, Void, List<ExampleModel>> {

    @Override
    protected List<ExampleModel> doInBackground(Integer... params) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (params[0] == -1) {
            return null;
        }
        return ExampleData.random(params[0]);
    }

    @Override
    protected void onPostExecute(List<ExampleModel> exampleModels) {
        if (exampleModels == null) {
            throw new NullPointerException("模拟抛出异常");
        }
    }
}
