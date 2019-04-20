package com.littledrawer.news.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuBuilder;
import butterknife.BindView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.littledrawer.R;
import com.littledrawer.common.BaseActivity;
import com.littledrawer.http.bean.Comment;
import com.littledrawer.http.bean.News;
import com.littledrawer.common.view.CommentView;
import com.littledrawer.util.TopicTag;
import com.littledrawer.util.Util;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initWidget() {
        super.initWidget();
        if (mNews != null) {
            Util.glideLoad(this, mNews.author.iconUrl, mIcon);
            mAuthor.setText(mNews.author.nickName);
            mTitle.setText(mNews.title);
            RichText.fromHtml(mNews.content)
                    .autoFix(true)
                    .autoPlay(true)
                    .scaleType(ImageHolder.ScaleType.fit_center)
                    .into(mContent);

            mComment.requestComments(new CommentView.Topic(TopicTag.NEWS, mNews.id));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news_comment:
                Toast.makeText(mActivity, "添加评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.news_report:
                Toast.makeText(mActivity, "举报", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }
}
