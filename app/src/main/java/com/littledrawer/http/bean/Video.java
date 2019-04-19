package com.littledrawer.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 土小贵
 * @date 2019/4/18 9:16
 */
public class Video implements Parcelable {
    public int id;
    public String title;
    public String describe;
    // 视频封面
    public String posterUrl;
    // 视频源
    public String sourceUrl;
    public int like;
    public int click;
    public Date date;
    public int typeIndex;
    public String typeName;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.describe);
        dest.writeString(this.posterUrl);
        dest.writeString(this.sourceUrl);
        dest.writeInt(this.like);
        dest.writeInt(this.click);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.typeIndex);
        dest.writeString(this.typeName);
        dest.writeParcelable(this.author, flags);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.describe = in.readString();
        this.posterUrl = in.readString();
        this.sourceUrl = in.readString();
        this.like = in.readInt();
        this.click = in.readInt();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.typeIndex = in.readInt();
        this.typeName = in.readString();
        this.author = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
