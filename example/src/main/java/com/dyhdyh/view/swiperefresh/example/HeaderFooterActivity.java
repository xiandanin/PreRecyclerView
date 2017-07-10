package com.dyhdyh.view.swiperefresh.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dyhdyh.view.swiperefresh.example.adapter.ExampleAdapter;
import com.dyhdyh.view.swiperefresh.header.RecyclerHeaderHelper;

import java.util.Random;

/**
 * author  dengyuhan
 * created 2017/7/4 15:14
 */
public class HeaderFooterActivity extends AppCompatActivity {
    RecyclerView rv;
    private boolean staggeredGrid;
    private RecyclerHeaderHelper mHeaderHelper;
    private ExampleAdapter mExampleAdapter;

    private Random mRandom = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        rv = (RecyclerView) findViewById(R.id.rv);

        mHeaderHelper = new RecyclerHeaderHelper(rv);

        mExampleAdapter = new ExampleAdapter(ExampleData.random(3), staggeredGrid);
        mHeaderHelper.setAdapter(mExampleAdapter);
        clickLinearLayoutManager(null);
    }

    public void clickOneData(MenuItem menuItem) {
        mExampleAdapter.getData().addAll(ExampleData.random(1));
        mExampleAdapter.notifyItemInserted(mExampleAdapter.getData().size() - 1);
    }

    public void clickFiveData(MenuItem menuItem) {
        int positionStart = mExampleAdapter.getData().size();
        mExampleAdapter.getData().addAll(ExampleData.random(5));
        mExampleAdapter.notifyItemRangeInserted(positionStart, 5);
    }

    public void clickLinearLayoutManager(MenuItem menuItem) {
        staggeredGrid = false;
        mHeaderHelper.setLayoutManager(new LinearLayoutManager(this));
    }

    public void clickGridLayoutManager(MenuItem menuItem) {
        staggeredGrid = false;
        mHeaderHelper.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public void clickStaggeredGridLayoutManager(MenuItem menuItem) {
        if (mHeaderHelper.getInnerAdapter() instanceof ExampleAdapter) {
            ((ExampleAdapter) mHeaderHelper.getInnerAdapter()).setStaggeredGrid(true);
            staggeredGrid = true;
        }
        mHeaderHelper.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    public void clickAddHeaderView(MenuItem menuItem) {
        final View headerView = createHeaderView("HeaderView - 点击删除");

        headerView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mHeaderHelper.removeHeaderView(headerView);
                //mHeaderHelper.removeHeaderView(v, false);
            }
        });
        mHeaderHelper.addHeaderView(headerView);
    }

    public void clickAddFooterView(MenuItem menuItem) {
        final View footerView = createHeaderView("FooterView - 点击删除");
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeaderHelper.removeFooterView(footerView);
                //mHeaderHelper.removeHeaderView(v, false);
            }
        });
        mHeaderHelper.addFooterView(footerView);
    }

    private View createHeaderView(String text) {
        TextView view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mRandom.nextInt(100) + 100));
        int rgb = Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
        Toast.makeText(this,String.valueOf(rgb), Toast.LENGTH_SHORT).show();
        view.setBackgroundColor(rgb);
        view.setTextColor(Color.WHITE);
        view.setText(text);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
