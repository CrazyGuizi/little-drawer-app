package com.littledrawer.news.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.littledrawer.R;
import com.littledrawer.http.bean.News;
import com.littledrawer.util.Util;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.ig.DefaultImageGetter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * @author 土小贵
 * @date 2019/4/18 22:13
 */
public class NewsColumnAdapter extends BaseMultiItemQuickAdapter<News, BaseViewHolder> {

    private List<News> mNews = new ArrayList<>();
    private Context mContext;

    public NewsColumnAdapter(List<News> data, Context context) {
        super(data);
        mNews = data;
        mContext = context;
        addItemType(News.NEWS_WITH_ONE_PICTURE, R.layout.recycler_item_news_one);
        addItemType(News.NEWS_WITH_NO_PICTURE, R.layout.recycler_item_news_two);
        addItemType(News.NEWS_WITH_THREE_PICTURE, R.layout.recycler_item_news_three);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, News item) {
        switch (item.style) {
            case News.NEWS_WITH_ONE_PICTURE:
                bindOne(helper, item);
                break;
            case News.NEWS_WITH_NO_PICTURE:
                bindTwo(helper, item);
                break;
            case News.NEWS_WITH_THREE_PICTURE:
                bindThree(helper, item);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindOne(BaseViewHolder helper, News news) {
        if (news != null) {
            bindCommon(helper, news);
            if (news.picUrls != null && !news.picUrls.isEmpty()) {
                bindImage(helper, R.id.iv_cover, news.picUrls.get(0));
            }
        }
    }

    private void bindCommon(BaseViewHolder helper, News news) {
        if (news != null) {
            helper.setText(R.id.tv_title, news.title);
            helper.setText(R.id.tv_author, news.author.nickName);
            helper.setText(R.id.tv_date, "" + news.date.getTime());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindImage(BaseViewHolder helper, int rId, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(mContext)
                    .load(url)
                    .placeholder(mContext.getDrawable(R.drawable.picture_default))
                    .centerCrop()
                    .into((ImageView) helper.getView(rId));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindTwo(BaseViewHolder helper, News news) {
        if (news != null) {
            bindCommon(helper, news);
//            TextView contentView = helper.getView(R.id.tv_content);
//            RichText.fromHtml(news.content)
//                    .autoFix(true)
//                    .autoPlay(true)
//                    .clickable(false)
//                    .scaleType(ImageHolder.ScaleType.fit_center)
//                    .into(contentView);
            TextView view = helper.getView(R.id.tv_content);
            view.setText(Html.fromHtml(news.content, FROM_HTML_MODE_COMPACT));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindThree(BaseViewHolder helper, News news) {
        if (news != null) {
            bindCommon(helper, news);
            if (news.picUrls != null && !news.picUrls.isEmpty()) {
                int size = news.picUrls.size();
                List<String> urls = news.picUrls;
                bindImage(helper, R.id.iv_cover_1, 0 < size ? urls.get(0) : "");
                bindImage(helper, R.id.iv_cover_2, 1 < size ? urls.get(1) : "");
                bindImage(helper, R.id.iv_cover_3, 2 < size ? urls.get(2) : "");
            }
        }
    }
}
