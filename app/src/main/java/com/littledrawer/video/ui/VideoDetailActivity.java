package com.littledrawer.video.ui;

import androidx.annotation.RequiresApi;
import butterknife.BindView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.base.net.AuthUtil;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.littledrawer.R;
import com.littledrawer.common.BaseActivity;
import com.littledrawer.common.view.CommentView;
import com.littledrawer.http.bean.Like;
import com.littledrawer.http.bean.User;
import com.littledrawer.http.bean.Video;
import com.littledrawer.http.service.LikeService;
import com.littledrawer.util.TopicTag;
import com.littledrawer.util.Util;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;
import java.util.Map;

public class VideoDetailActivity extends BaseActivity {

    public static final String ARG_VIDEO = "ARG_VIDEO";
    private Video mVideo;

    private OrientationUtils orientationUtils;

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    @BindView(R.id.iv_icon)
    ImageView mIcon;
    @BindView(R.id.tv_author)
    TextView mAuthor;
    @BindView(R.id.iv_like)
    ImageView mLike;
    @BindView(R.id.tv_comment)
    TextView mAddComment;
    @BindView(R.id.tv_like)
    TextView mLikeCount;
    @BindView(R.id.tv_describe)
    TextView mDescribe;
    @BindView(R.id.comment_view)
    CommentView mCommentView;

    public static void startActivity(Context context, Video video) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(ARG_VIDEO, video);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData(Intent intent) {
        super.getIntentData(intent);
        if (intent != null) {
            mVideo = intent.getParcelableExtra(ARG_VIDEO);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_video_detail;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initWidget() {
        super.initWidget();
        if (mVideo != null) {
            initVideoPlay();
            Util.glideLoad(this, mVideo.author.iconUrl, mIcon);
            mAuthor.setText(mVideo.author.nickName);
            mLikeCount.setText("" + mVideo.like);
            if ("".equals(mVideo.describe)) {
                mDescribe.setText(getString(R.string.text_no_describe));
            } else {
                mDescribe.setText(getString(R.string.text_describe) + mVideo.describe);
            }
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
        getLikeStatus();
        mLike.setOnClickListener(v -> {
            if (AuthUtil.getInstance().isLogin()) {
                handleLike();
            } else {
                Toast.makeText(mActivity, getString(R.string.text_please_login), Toast.LENGTH_SHORT).show();
            }
        });

        mAddComment.setOnClickListener( v -> {
            if (mCommentView != null) {
                mCommentView.addComment();
            }
        });

        // 请求评论数据
        mCommentView.requestComments(new CommentView.Topic(TopicTag.VIDEO, mVideo.id));
    }

    // 查看用户是否已经给这个视频点过赞了
    private void getLikeStatus() {
        if (!AuthUtil.getInstance().isLogin()) {
            return;
        }
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, Integer> map = new HashMap<>();
        map.put(LikeService.TOPIC_TYPE, TopicTag.VIDEO.topicIndex);
        map.put(LikeService.TOPIC_ID, mVideo.id);
        map.put(LikeService.USER_ID, AuthUtil.getInstance().getUserId());

        manager.request(manager.getService(LikeService.class)
                .getLikeStatus(map), new BaseListener<Like>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(Like like) {
                if (like.status == 0) {
                    mLike.setClickable(true);
                } else {
                    // 已经点过赞了
                    mLike.setImageDrawable(getDrawable(R.drawable.icon_like));
                    mLike.setClickable(false);
                }
            }

            @Override
            public void onFail(BaseException e) {
                mLike.setClickable(true);
            }
        });
    }

    private void handleLike() {

        RetrofitManager manager = RetrofitManager.getInstance();
        Like like = new Like();
        like.setTopicType(TopicTag.VIDEO.topicIndex);
        like.setTopicId(mVideo.id);
        like.setStatus(1);
        User liker = new User();
        liker.setId(AuthUtil.getInstance().getUserId());
        like.setLiker(liker);
        manager.request(manager.getService(LikeService.class)
                .addLike(like), new BaseListener<Like>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSuccess(Like like) {
                mLike.setImageDrawable(getDrawable(R.drawable.icon_like));
                int likeCount = Integer.parseInt(mLikeCount.getText().toString());
                mLikeCount.setText("" + (likeCount + 1));
                mLike.setClickable(false);
            }

            @Override
            public void onFail(BaseException e) {
                Toast.makeText(VideoDetailActivity.this,
                        e.msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initVideoPlay() {
        videoPlayer.setUp(mVideo.sourceUrl, true, mVideo.title);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this)
                .load(mVideo.posterUrl)
                .placeholder(getDrawable(R.drawable.picture_default))
                .into(imageView);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
