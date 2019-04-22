package com.littledrawer.video.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.example.base.util.Log;
import com.littledrawer.R;
import com.littledrawer.common.BaseFragment;
import com.littledrawer.http.bean.News;
import com.littledrawer.http.bean.Video;
import com.littledrawer.http.service.NewsService;
import com.littledrawer.http.service.VideoService;
import com.littledrawer.news.adapter.NewsColumnAdapter;
import com.littledrawer.news.ui.NewsColumnFragment;
import com.littledrawer.util.NewsColumn;
import com.littledrawer.util.VideoType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * @author 土小贵
 * @date 2019/4/19 13:28
 */
public class VideoTabFragment extends BaseFragment {
    private static final String TAG_VIDEO_TYPE = "type";

    private VideoType mVideoType;
    // 请求分页
    private int pageNum = 1;
    private static final int pageSize = 20;

    private BaseQuickAdapter mAdapter;
    private List<Video> mVideos = new ArrayList<>();

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefresh;

    public static VideoTabFragment getInstance(VideoType type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_VIDEO_TYPE, type);
        VideoTabFragment fragment = new VideoTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void getBundleData(Bundle bundle) {
        super.getBundleData(bundle);
        mVideoType = (VideoType) bundle.getSerializable(TAG_VIDEO_TYPE);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_video_tab;
    }

    @Override
    public void initWidget(View view) {
        super.initWidget(view);
        initAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new BaseQuickAdapter<Video, BaseViewHolder>(R.layout.recycler_item_video,
                    null) {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                protected void convert(BaseViewHolder helper, Video video) {
                    if (video != null) {
                        Glide.with(getActivity())
                                .load(video.posterUrl)
                                .centerCrop()
                                .placeholder(getActivity().getDrawable(R.drawable.picture_default))
                                .into((ImageView)helper.getView(R.id.iv_poster));
                        Glide.with(getActivity())
                                .load(video.author.iconUrl)
                                .placeholder(getResources().getDrawable(R.drawable.picture_default,
                                        null))
                                .centerCrop()
                                .into((ImageView)helper.getView(R.id.iv_icon));
                        helper.setText(R.id.tv_title, video.title);
                        helper.setText(R.id.tv_author, video.author.nickName);
                        helper.setText(R.id.tv_like, "" + video.like);
                        helper.setText(R.id.tv_click, "" + video.click);
                    }
                }
            };
        }

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Video video = mVideos.get(position);
                VideoDetailActivity.startActivity(getActivity(), video);
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefresh.setOnRefreshListener(() -> {
            setupData();
        });
        setupData();
    }

    private void setupData() {
        RetrofitManager rm = RetrofitManager.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put(VideoService.TYPE_INDEX, mVideoType.typeIndex);
        map.put(VideoService.PAGE_NUM, pageNum);
        map.put(VideoService.PAGE_SIZE, pageSize);
        rm.request(rm.getService(VideoService.class)
                .getVideosByType(map), new BaseListener<List<Video>>() {
            @Override
            public void onSuccess(List<Video> videos) {

                mRefresh.setRefreshing(false);
                if (videos != null && !videos.isEmpty()) {
                    mVideos = videos;
                    mAdapter.setNewData(videos);
                }
            }

            @Override
            public void onFail(BaseException e) {
                mRefresh.setRefreshing(false);
            }
        });
    }
}
