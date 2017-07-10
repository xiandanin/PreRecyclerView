package com.dyhdyh.view.swiperefresh.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dyhdyh.view.swiperefresh.example.adapter.ExampleAdapter;
import com.dyhdyh.view.swiperefresh.example.adapter.ExampleModel;
import com.dyhdyh.view.swiperefresh.loadmore.LoadMoreFooter;
import com.dyhdyh.view.swiperefresh.loadmore.OnLoadMoreListener;
import com.dyhdyh.view.swiperefresh.loadmore.RecyclerLoadMoreHelper;

import java.util.List;

/**
 * author  dengyuhan
 * created 2017/7/6 11:43
 */
public class LoadMoreActivity extends AppCompatActivity {
    RecyclerView rv;
    private RecyclerLoadMoreHelper mLoadMoreHelper;
    private ExampleAdapter mExampleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);
        rv = (RecyclerView) findViewById(R.id.rv);

        mLoadMoreHelper = new RecyclerLoadMoreHelper(rv);

        mExampleAdapter = new ExampleAdapter(ExampleData.random(3));
        mLoadMoreHelper.setAdapter(mExampleAdapter);
        mLoadMoreHelper.setLayoutManager(new LinearLayoutManager(this));
        mLoadMoreHelper.setLoadMoreState(LoadMoreFooter.State.GONE);
    }


    public void clickOnePage(MenuItem menuItem) {
        mExampleAdapter.setData(ExampleData.random(3));
        mExampleAdapter.notifyDataSetChanged();
        mLoadMoreHelper.setLoadMoreState(LoadMoreFooter.State.GONE);
        mLoadMoreHelper.setLoadMore(false);
    }

    public void clickLoadPageData(MenuItem menuItem) {
        mExampleAdapter = new ExampleAdapter(ExampleData.random(10));
        mLoadMoreHelper.setAdapter(mExampleAdapter);
        mLoadMoreHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new ExampleAsyncTask() {
                    @Override
                    protected void onPostExecute(List<ExampleModel> result) {
                        Toast.makeText(LoadMoreActivity.this, "成功加载新的一页", Toast.LENGTH_SHORT).show();
                        int dataSize = mExampleAdapter.getData().size();
                        mExampleAdapter.getData().addAll(result);
                        mExampleAdapter.notifyItemRangeInserted(dataSize, result.size());
                        mLoadMoreHelper.setLoadMore(false);
                    }
                }.execute(10);
            }
        });
    }


    public void clickTheEndData(MenuItem menuItem) {
        mExampleAdapter = new ExampleAdapter(ExampleData.random(10));
        mLoadMoreHelper.setAdapter(mExampleAdapter);
        mLoadMoreHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new ExampleAsyncTask() {
                    @Override
                    protected void onPostExecute(List<ExampleModel> result) {
                        mLoadMoreHelper.setLoadMoreState(LoadMoreFooter.State.THE_END);
                        mLoadMoreHelper.setLoadMore(false);
                    }
                }.execute(10);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loadmore_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}