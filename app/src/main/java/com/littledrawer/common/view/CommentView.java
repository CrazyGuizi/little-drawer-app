package com.littledrawer.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.littledrawer.R;
import com.littledrawer.common.adapter.CommentAdapter;
import com.littledrawer.http.bean.Comment;
import com.littledrawer.http.bean.News;
import com.littledrawer.http.service.CommentService;
import com.littledrawer.util.TopicTag;
import com.littledrawer.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 土小贵
 * @date 2019/4/19 19:37
 */
public class CommentView extends LinearLayout {

    private List<Comment> mComments;
    // 是否正在请求数据
    private boolean isRequesting;
    private BaseQuickAdapter mAdapter;

    @BindView(R.id.tv_comment_count)
    TextView mCommentCount;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_content)
    NoContentView mNoContentView;

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public CommentView(Context context) {
        super(context);
        initView(context, null);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.view_comment, this);
        ButterKnife.bind(this, view);
        initWidget(view);
    }

    private void initWidget(View view) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new CommentAdapter(null, getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void requestError(String msg) {

    }

    /**
     * 请求评论数据
     */
    public void requestComments(Topic topic) {
        if (isRequesting) {
            return;
        }
        isRequesting = true;
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put(CommentService.TOPIC_TYPE, "" + topic.mTag.topicIndex);
        map.put(CommentService.TOPIC_ID, "" + topic.topicId);
        manager.request(manager.getService(CommentService.class)
                .getCommentsByTopic(map), new BaseListener<List<Comment>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(List<Comment> comments) {
                if (comments != null) {
                    mComments = comments;
                   showNoContent(false);
                   refreshData(comments);
                } else {
                    showNoContent(true);
                }
                isRequesting = false;
            }

            @Override
            public void onFail(BaseException e) {
                isRequesting = false;
                showNoContent(false);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refreshData(List<Comment> comments) {
        if (comments != null) {
            if (mAdapter != null) {
               mAdapter.setNewData(comments);
            }

            mCommentCount.setText("" + comments.size());
        }
    }

    private void showNoContent(boolean isShow) {
        if (mNoContentView != null) {
            if (isShow) {
                mNoContentView.setVisibility(View.VISIBLE);
            } else {
                mNoContentView.setVisibility(View.GONE);
            }
        }
    }

    public static class Topic {
        public TopicTag mTag;
        public int topicId;

        public Topic(TopicTag tag, int topicId) {
            mTag = tag;
            this.topicId = topicId;
        }
    }
}
