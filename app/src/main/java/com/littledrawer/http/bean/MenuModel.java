package com.littledrawer.http.bean;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 土小贵
 * @date 2019/4/22 8:46
 */
public class MenuModel implements Parcelable {

    public int index;
    public String name;
    public String icon;

    public MenuModel(int index, String name, String icon) {
        this.index = index;
        this.name = name;
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeString(this.name);
        dest.writeString(this.icon);
    }

    public MenuModel() {
    }

    protected MenuModel(Parcel in) {
        this.index = in.readInt();
        this.name = in.readString();
        this.icon = in.readString();
    }

    public static final Creator<MenuModel> CREATOR = new Creator<MenuModel>() {
        @Override
        public MenuModel createFromParcel(Parcel source) {
            return new MenuModel(source);
        }

        @Override
        public MenuModel[] newArray(int size) {
            return new MenuModel[size];
        }
    };
}
