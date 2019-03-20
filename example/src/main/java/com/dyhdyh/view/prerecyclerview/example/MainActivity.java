package com.dyhdyh.view.prerecyclerview.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dyhdyh.view.prerecyclerview.LoadMoreFooter;
import com.dyhdyh.view.prerecyclerview.OnItemCountChangedListener;
import com.dyhdyh.view.prerecyclerview.OnLoadMoreListener;
import com.dyhdyh.view.prerecyclerview.PreRecyclerView;
import com.dyhdyh.view.prerecyclerview.example.adapter.ExampleAdapter;
import com.dyhdyh.view.prerecyclerview.example.adapter.ExampleModel;

import java.util.List;

/**
 * author  dengyuhan
 * created 2017/7/6 11:43
 */
public class MainActivity extends AppCompatActivity {
    private RadioGroup rg_option;
    private RadioButton rb_success;
    private SwipeRefreshLayout refresh_Layout;
    private PreRecyclerView rv;
    private ExampleAdapter mExampleAdapter;

    private int mLoadMoreState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);
        rv = findViewById(R.id.rv);
        refresh_Layout = findViewById(R.id.refresh_Layout);
        rg_option = findViewById(R.id.rg_option);
        rg_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.rb_error == checkedId) {
                    mLoadMoreState = LoadMoreFooter.STATE_ERROR;
                } else if (R.id.rb_end == checkedId) {
                    mLoadMoreState = LoadMoreFooter.STATE_THE_END;
                } else {
                    mLoadMoreState = LoadMoreFooter.STATE_NORMAL;
                }
            }
        });
        rb_success = findViewById(R.id.rb_success);
        rb_success.setChecked(true);

        TextView headerView = new TextView(this);
        headerView.setGravity(Gravity.CENTER);
        headerView.setTextColor(Color.WHITE);
        headerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        headerView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, getTheme()));
        headerView.setText("Pre Header");
        rv.setHeaderView(headerView);

        mExampleAdapter = new ExampleAdapter(ExampleData.random(10));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mExampleAdapter);
        rv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d("---------->", "加载下一页--->");
                asyncLoadPageData();
            }
        });
        rv.setOnItemCountChangedListener(new OnItemCountChangedListener() {
            @Override
            public void onItemCountChanged(int itemCount) {
                Log.d("---------->", "数据长度发生变化--->" + itemCount);
            }
        });

        refresh_Layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.reset();
                asyncRefreshPageData();
            }
        });
    }

    private void asyncRefreshPageData() {
        new ExampleAsyncTask() {
            @Override
            protected void onPostExecute(List<ExampleModel> result) {
                refresh_Layout.setRefreshing(false);
                mExampleAdapter = new ExampleAdapter(result);
                rv.setAdapter(mExampleAdapter);
            }
        }.execute(10);
    }

    private void asyncLoadPageData() {
        new ExampleAsyncTask() {
            @Override
            protected void onPostExecute(List<ExampleModel> result) {
                rv.setLoadMoreCompleted();
                if (rb_success.isChecked()) {
                    int dataSize = mExampleAdapter.getData().size();
                    mExampleAdapter.getData().addAll(result);
                    mExampleAdapter.notifyItemRangeInserted(dataSize, result.size());
                }
                rv.setLoadMoreState(mLoadMoreState);
            }
        }.execute(10);
    }

    public void clickSetHeaderView(MenuItem menuItem) {
        TextView headerView = new TextView(this);
        headerView.setGravity(Gravity.CENTER);
        headerView.setTextColor(Color.WHITE);
        headerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        headerView.setBackgroundColor(ExampleData.randomColor());
        headerView.setText("Header");
        rv.setHeaderView(headerView);
    }

    public void clickRemoveHeaderView(MenuItem item) {
        rv.removeHeaderView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loadmore_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void clickLinearLayoutManager(View view) {
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void clickGridLayoutManager(View view) {
        rv.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public void clickStaggeredGridLayoutManager(View view) {
        mExampleAdapter.setStaggeredGrid(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    public void clickCustomFooterView(MenuItem item) {
        final CustomFooterView footerView = new CustomFooterView(this);
        footerView.setState(rv.getLoadMoreState());
        rv.setLoadMoreFooter(footerView);
    }

}