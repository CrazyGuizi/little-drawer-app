package com.littledrawer.util;

import com.littledrawer.R;

/**
 * @author 土小贵
 * @date 2019/4/22 9:47
 */
public enum MeMenu {
    PERSON_INFO(0, "个人中心", R.drawable.icon_menu_person),
    MY_NEW(1, "我的新闻", R.drawable.icon_menu_news),
    MY_VIDEO(2, "我的视频", R.drawable.icon_menu_video),
    MY_PICTURE(3, "我的图片", R.drawable.icon_menu_picture),
    MY_COLLECTION(4, "我的收藏", R.drawable.icon_menu_collect),
    LOGOUT(5, "退出登录", R.drawable.icon_menu_logout);

    public int index;
    public String name;
    public int src;

    MeMenu(int index, String name, int src) {
        this.index = index;
        this.name = name;
        this.src = src;
    }
}
