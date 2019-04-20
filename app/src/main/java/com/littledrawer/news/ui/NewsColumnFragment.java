package com.littledrawer.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.example.base.util.Log;
import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;
import com.littledrawer.http.bean.News;
import com.littledrawer.http.service.NewsService;
import com.littledrawer.news.adapter.NewsColumnAdapter;
import com.littledrawer.util.NewsColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * @author 土小贵
 * @date 2019/4/18 19:46
 */
public class NewsColumnFragment extends BaseFragment {

    private static final String TAG_NEWS_COLUMN = "column";

    private NewsColumn mColumn;
    // 请求分页
    private int pageNum = 1;
    private static final int pageSize = 20;

    private NewsColumnAdapter mAdapter;
    private List<News> mNews = new ArrayList<>();

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefresh;

    public static NewsColumnFragment getInstance(NewsColumn column) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_NEWS_COLUMN, column);
        NewsColumnFragment fragment = new NewsColumnFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void getBundleData(Bundle bundle) {
        super.getBundleData(bundle);
        mColumn = (NewsColumn) bundle.getSerializable(TAG_NEWS_COLUMN);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_news_column;
    }

    @Override
    public void initWidget(View view) {
        super.initWidget(view);
        if (mAdapter == null) {
            mAdapter = new NewsColumnAdapter(null, getActivity());
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefresh.setOnRefreshListener(() ->{
            pageNum++;
            setupData();
        });

        setupData();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                News news = mNews.get(position);
                NewsDetailActivity.startActivity(getActivity(), news);
            }
        });
    }

    private void setupData() {
        if (mRefresh != null && !mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(true);
        }
        RetrofitManager rm = RetrofitManager.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put(NewsService.COLUMN, mColumn.columnName);
        map.put(NewsService.PAGE_NUM, pageNum);
        map.put(NewsService.PAGE_SIZE, pageSize);
        Log.d(this, mColumn.columnName);
        rm.request(rm.getService(NewsService.class)
                .getNewsByColumn(map), new BaseListener<List<News>>() {
            @Override
            public void onSuccess(List<News> news) {
                if (mRefresh.isRefreshing()) {
                    mRefresh.setRefreshing(false);
                }
                if (news != null && news.size() > 0) {
                    mNews = news;
                    mAdapter.setNewData(news);
                }
            }

            @Override
            public void onFail(BaseException e) {
                if (mRefresh.isRefreshing()) {
                    mRefresh.setRefreshing(false);
                }
            }
        });
    }
}
