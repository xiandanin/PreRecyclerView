### Gradle
```
implementation 'com.dyhdyh.view:prerecyclerview:1.0.0'
```

### XML
```
<com.dyhdyh.view.prerecyclerview.PreRecyclerView
    android:id="@+id/recyclerview"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### 自定义属性
```
<!--显示到倒数第几个 才回调加载更多 默认1-->
<attr name="autoLoadByLastCount" format="integer" />
<!--是否开启加载更多 默认true-->
<attr name="loadMoreEnabled" format="boolean" />
<!--加载更多的View 完整类名-->
<attr name="loadMoreFooter" format="string" />
```

### 设置Header
```
//设置Header View
recyclerview.setHeaderView(headerView);
//移除Header View
recyclerview.removeHeaderView();
//这是LoadMoreView
recyclerview.setLoadMoreFooter(footerView);
```
如果要设置HeaderView或者LoadMoreView，将设置View放在第一步是最省性能的

`setHeaderView` -> `setLayoutManager` -> `setAdapter`

`setLoadMoreView` -> `setLayoutManager` -> `setAdapter`

##### 自定义LoadMoreView
参考`SimpleLoadMoreView`实现`LoadMoreFooter`


### 设置LoadMore状态
```
//STATE_NORMAL = 正常
//STATE_LOADING = 加载中
//STATE_ERROR = 异常
//STATE_THE_END = 到底了
recyclerview.setLoadMoreState(LoadMoreFooter.STATE_LOADING);
```

### 设置回调
```
recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
    @Override
    public void onLoadMore() {
        Log.d("---------->", "加载下一页--->");
    }
});
recyclerview.setOnItemCountChangedListener(new OnItemCountChangedListener() {
    @Override
    public void onItemCountChanged(int innerItemCount) {
        Log.d("---------->", "数据长度发生变化--->" + itemCount);
    }
});
```


### 结合实际情况
如果是刷新，需要在加载数据之前调用`reset()`来重置状态

```
refresh_Layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        //重置recyclerview
        recyclerview.reset();
        
        asyncRefreshPageData();
    }
});
```

如果是加载更多，需要在完成的时候调用`setLoadMoreCompleted()`，并设置合适的状态

```
new ExampleAsyncTask() {
    @Override
    protected void onPostExecute(List<ExampleModel> result) {
        //加载完成
        rv.setLoadMoreCompleted();
        
        rv.setLoadMoreState(state);
    }
}.execute(10);
```