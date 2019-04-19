package com.littledrawer.news.ui;

import butterknife.BindView;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.littledrawer.R;
import com.littledrawer.common.BaseActivity;
import com.littledrawer.http.bean.Comment;
import com.littledrawer.http.bean.News;
import com.littledrawer.common.view.CommentView;
import com.littledrawer.util.TopicTag;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends BaseActivity {

    public static final String ARG_NEWS = "ARG_NEWS";

    private News mNews;
    private List<Comment> mComments = new ArrayList<>();
    @BindView(R.id.iv_icon)
    ImageView mIcon;
    @BindView(R.id.tv_author)
    TextView mAuthor;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.comment_view)
    CommentView mComment;


    public static void startActivity(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(ARG_NEWS, news);
        context.startActivity(intent);
    }

    @Override
    protected void getIntentData(Intent intent) {
        super.getIntentData(intent);
        mNews = intent.getParcelableExtra(ARG_NEWS);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        if (mNews != null) {
            Glide.with(this)
                    .load(mNews.author.iconUrl)
                    .into(mIcon);

            mAuthor.setText(mNews.author.nickName);
            mTitle.setText(mNews.title);
            RichText.fromHtml(mNews.content)
                    .autoFix(true)
                    .autoPlay(true)
                    .scaleType(ImageHolder.ScaleType.fit_center)
                    .into(mContent);
            News news = mNews;

            mComment.requestComments(new CommentView.Topic(TopicTag.NEWS, mNews.id));
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }
}
