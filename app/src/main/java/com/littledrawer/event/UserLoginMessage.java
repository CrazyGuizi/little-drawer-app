package com.littledrawer.event;

/**
 * @author 土小贵
 * @date 2019/4/22 11:08
 */
public class UserStatusMessage extends MessageEvent {
    public boolean isLogin;

    public UserStatusMessage(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
