package com.littledrawer.http.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;
import java.util.List;

/**
 * @author 土小贵
 * @date 2019/4/17 23:10
 */
public class News implements MultiItemEntity {

    public static final int NEWS_WITH_ONE_PICTURE = 1;
    public static final int NEWS_WITH_NO_PICTURE = 2;
    public static final int NEWS_WITH_THREE_PICTURE = 3;

    public int id;
    public String title;
    // 类别
    public String column;
    public Date date;
    public String content;
    // 展示新闻时的一个tag，1展示一张封面，2为0张，3为3张
    public int style;
    // 封面图片
    public List<String> picUrls;
    public User author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public int getItemType() {
        return style;
    }
}
