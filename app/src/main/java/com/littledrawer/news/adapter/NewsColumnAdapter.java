package com.littledrawer.news.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.littledrawer.R;
import com.littledrawer.http.bean.News;
import com.littledrawer.util.Util;

import java.util.ArrayList;
import java.util.List;

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

    private void bindOne(BaseViewHolder helper, News news) {
        if (news != null) {
            helper.setText(R.id.tv_title, news.title);
            helper.setText(R.id.tv_author, news.author.nickName);
            helper.setText(R.id.tv_date, "" + news.date.getTime());
            if (news.picUrls != null && !news.picUrls.isEmpty())
            Glide.with(mContext)
                    .load(news.picUrls.get(0))
                    .centerCrop()
                    .into((ImageView)helper.getView(R.id.iv_cover));
        }
    }

    private void bindTwo(BaseViewHolder helper, News news) {
    }

    private void bindThree(BaseViewHolder helper, News news) {

    }
}
