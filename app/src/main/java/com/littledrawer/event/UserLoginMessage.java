package com.littledrawer.event;

import com.littledrawer.http.bean.User;

/**
 * @author 土小贵
 * @date 2019/4/22 11:08
 */
public class UserLoginMessage extends MessageEvent {
    public boolean isLogin;
    public User user;
    public String msg;

    public UserLoginMessage(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public UserLoginMessage(boolean isLogin, User user, String msg) {
        this.isLogin = isLogin;
        this.user = user;
        this.msg = msg;
    }
}
