# SwipeRefreshView
HeaderView、FooterView、刷新、加载更多，支持自定义组装的RecyclerView组件

## __效果__
![](screenshot/screenshot.gif)

## __结构__
`RecyclerHeaderHelper`  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;可以给RecyclerView提供 增删多个HeaderView、FooterView，开关动画的能力  
		
`RecyclerLoadMoreHelper`  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;继承RecyclerHeaderHelper，在它的基础上又增加了加载更多的能力

`PagingHelper`  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;拥有分页逻辑，可以获取当前页码和逻辑增长

`SwipeRefreshRecyclerView`  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;拥有RecyclerHeaderHelper，RecyclerLoadMoreHelper，PagingHelper的能力，一种预设的RecyclerView

###### 这里只介绍`SwipeRefreshRecyclerView`的用法，更多可以看例子

## __SwipeRefreshRecyclerView__
####  Gradle
```
compile 'com.dyhdyh.widget:swiperefreshview:1.0.1'
```

#### 默认配置
```
<com.dyhdyh.widget.swiperefresh.view.SwipeRefreshRecyclerView
        android:id="@+id/swipe"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:refreshEnabled="true"
        app:loadMoreEnabled="true"
        app:pageCount="10"
        app:schemeColor="@color/colorAccent"
        app:startPage="0" />
```
#### 获取实际RecyclerView对象
```
rv.getView()
```

#### 获取实际Adapter对象
```
rv.getInnerAdapter()
```


#### 监听
```
//rv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener());
//rv.setOnLoadMoreListener(new OnLoadMoreListener());
rv.setOnRefreshListener(new OnRefreshListener2() {
    @Override
    public void onRefresh(boolean refresh) {
        if (refresh) {
            //刷新
        } else {
            //加载更多
        }
    }
});
```
#### 设置自定义加载更多样式
```
//参考LoadMoreView
rv.setLoadMoreFooter(loadMoreFooter);
```
#### 加载更多状态
```
rv.setLoadMoreState(LoadMoreFooter.State.GONE);
rv.setLoadMoreState(LoadMoreFooter.State.LOADING);
rv.setLoadMoreState(LoadMoreFooter.State.THE_END);
rv.setLoadMoreState(LoadMoreFooter.State.ERROR);
```

#### 刷新完成
```
rv.setRefreshComplete();
```

###### 可以参考例子`RecyclerViewActivity`
