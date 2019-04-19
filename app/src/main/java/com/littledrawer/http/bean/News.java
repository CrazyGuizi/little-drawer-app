package com.littledrawer.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 土小贵
 * @date 2019/4/17 23:10
 */
public class News implements MultiItemEntity, Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.column);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.content);
        dest.writeInt(this.style);
        dest.writeStringList(this.picUrls);
        dest.writeParcelable(this.author, flags);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.column = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.content = in.readString();
        this.style = in.readInt();
        this.picUrls = in.createStringArrayList();
        this.author = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
