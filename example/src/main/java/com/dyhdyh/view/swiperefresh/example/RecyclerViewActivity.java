package com.dyhdyh.view.swiperefresh.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.dyhdyh.view.swiperefresh.example.adapter.ExampleAdapter;
import com.dyhdyh.view.swiperefresh.example.adapter.ExampleModel;
import com.dyhdyh.view.swiperefresh.view.OnRefreshListener2;
import com.dyhdyh.view.swiperefresh.view.SwipeRefreshRecyclerView;

import java.util.List;

/**
 * author  dengyuhan
 * created 2017/7/4 15:14
 */
public class RecyclerViewActivity extends AppCompatActivity {
    SwipeRefreshRecyclerView rv;

    private ExampleAdapter mExampleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        rv = (SwipeRefreshRecyclerView) findViewById(R.id.swrv);

        final List<ExampleModel> exampleModels = ExampleData.random(10);
        mExampleAdapter = new ExampleAdapter(exampleModels);
        rv.setAdapter(mExampleAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setOnRefreshListener(new OnRefreshListener2() {
            @Override
            public void onRefresh(boolean refresh) {
                if (refresh) {
                    refresh();
                } else {
                    loadMore();
                }
            }
        });
    }


    private void refresh() {
        new ExampleAsyncTask() {
            @Override
            protected void onPostExecute(List<ExampleModel> result) {
                Toast.makeText(RecyclerViewActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                mExampleAdapter.setData(result);
                mExampleAdapter.notifyItemRangeChanged(0, result.size());
                rv.setRefreshComplete();
            }
        }.execute(10);
    }

    private void loadMore() {
        new ExampleAsyncTask() {
            @Override
            protected void onPostExecute(List<ExampleModel> result) {
                Toast.makeText(RecyclerViewActivity.this, "成功加载新的一页", Toast.LENGTH_SHORT).show();
                int dataSize = mExampleAdapter.getData().size();
                mExampleAdapter.getData().addAll(result);
                mExampleAdapter.notifyItemRangeInserted(dataSize, result.size());
                rv.setRefreshComplete();
            }
        }.execute(10);
    }

}
