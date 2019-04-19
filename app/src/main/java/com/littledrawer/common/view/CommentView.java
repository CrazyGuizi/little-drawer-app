package com.littledrawer.common.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.littledrawer.R;
import com.littledrawer.http.bean.Comment;
import com.littledrawer.http.bean.News;
import com.littledrawer.http.service.CommentService;
import com.littledrawer.util.TopicTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 土小贵
 * @date 2019/4/19 19:37
 */
public class CommentView extends LinearLayout {

    private List<Comment> mComments;
    // 是否正在请求数据
    private boolean isRequesting;

    private RecyclerView mRecyclerView;

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public CommentView(Context context) {
        super(context);
        initView(context);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.view_comment, this);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = getChildAt(i);
//            measureChild(view, widthMeasureSpec, heightMeasureSpec);
//        }
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public void requestError(String msg) {

    }

    /**
     * 请求评论数据
     */
    public void requestComments(Topic topic) {
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put(CommentService.TOPIC_TYPE, topic.mTag.topicName);
        map.put(CommentService.TOPIC_ID, "" + topic.topicId);
        manager.request(manager.getService(CommentService.class)
                .getCommentsByTopic(map), new BaseListener<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> comments) {
                if (comments != null) {
                    mComments = comments;
                }
            }

            @Override
            public void onFail(BaseException e) {

            }
        });
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
