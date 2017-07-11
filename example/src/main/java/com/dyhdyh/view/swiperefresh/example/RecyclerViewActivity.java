package com.dyhdyh.view.swiperefresh.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.dyhdyh.view.swiperefresh.example.adapter.ExampleAdapter;
import com.dyhdyh.view.swiperefresh.example.adapter.ExampleModel;
import com.dyhdyh.view.swiperefresh.loadmore.LoadMoreFooter;
import com.dyhdyh.view.swiperefresh.listener.OnRefreshListener2;
import com.dyhdyh.view.swiperefresh.view.SwipeRefreshRecyclerView;

import java.util.List;
import java.util.Random;

/**
 * author  dengyuhan
 * created 2017/7/4 15:14
 */
public class RecyclerViewActivity extends AppCompatActivity {
    SwipeRefreshRecyclerView rv;

    private ExampleAdapter mExampleAdapter;

    private final int mPageCount = 10;//每页10条

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        rv = (SwipeRefreshRecyclerView) findViewById(R.id.swrv);

        final List<ExampleModel> exampleModels = ExampleData.random(mPageCount);
        mExampleAdapter = new ExampleAdapter(exampleModels);
        rv.setAdapter(mExampleAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //rv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener());
        //rv.setOnLoadMoreListener(new OnLoadMoreListener());
        rv.setOnRefreshListener(new OnRefreshListener2() {
            @Override
            public void onRefresh(boolean refresh) {
                if (refresh) {
                    //刷新
                    refresh();
                } else {
                    //加载更多
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
        }.execute(mPageCount);
    }

    private void loadMore() {
        new ExampleAsyncTask() {
            @Override
            protected void onPostExecute(List<ExampleModel> result) {
                try {
                    super.onPostExecute(result);

                    Toast.makeText(RecyclerViewActivity.this, "成功加载新的一页", Toast.LENGTH_SHORT).show();
                    int dataSize = mExampleAdapter.getData().size();
                    mExampleAdapter.getData().addAll(result);
                    mExampleAdapter.notifyItemRangeInserted(dataSize, result.size());
                    if (rv.getPage() >= 3) {
                        //如果是最后一页
                        rv.setLoadMoreState(LoadMoreFooter.State.THE_END);
                    }else{
                        //成功添加后翻页
                        rv.pageDown();
                    }
                } catch (Exception e) {
                    rv.setLoadMoreState(LoadMoreFooter.State.ERROR);
                    Toast.makeText(RecyclerViewActivity.this, "随机模拟异常", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    rv.setRefreshComplete();
                }
            }
        }.execute(new Random().nextBoolean() ? -1 : mPageCount);
    }

}
