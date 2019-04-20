package com.littledrawer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.littledrawer.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.RequiresApi;

/**
 * @author 土小贵
 * @date 2019/4/18 20:02
 */
public class Util {


    public static String transformDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static NewsColumn getNewsColumn(String colName) {
        NewsColumn column = null;

        if (NewsColumn.SOCIAL.columnName.equals(colName)) {
            column = NewsColumn.SOCIAL;
        } else if (NewsColumn.SCIENCE.columnName.equals(colName)) {
            column = NewsColumn.SCIENCE;
        } else if (NewsColumn.LIFE.columnName.equals(colName)) {
            column = NewsColumn.LIFE;
        } else if (NewsColumn.ENTERTAINMENT.columnName.equals(colName)) {
            column = NewsColumn.ENTERTAINMENT;
        } else if (NewsColumn.AGRICULTURAL.columnName.equals(colName)) {
            column = NewsColumn.AGRICULTURAL;
        }else if (NewsColumn.INTERNATIONAL.columnName.equals(colName)) {
            column = NewsColumn.INTERNATIONAL;
        } else if (NewsColumn.SPORTS.columnName.equals(colName)) {
            column = NewsColumn.SPORTS;
        }

        return column;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void glideLoad(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(context.getDrawable(R.drawable.picture_default))
                .into(imageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void glideLoadNoCenterCrop(Context context, String url, ImageView imageView) {
        RequestBuilder<Bitmap> bitmap = Glide.with(context)
                .asBitmap();
        int width = bitmap.getOverrideWidth();

        int height = bitmap.getOverrideHeight();
        Glide.with(context).load(bitmap).override(width, height)
                .centerCrop().into(imageView);
//        imageView
//                .load(url)
//                .override()
//                .placeholder(context.getDrawable(R.drawable.picture_default))
//                .into(imageView);
    }
}
