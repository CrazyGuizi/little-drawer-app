package com.example.base.net;

/**
 * @author 土小贵
 * @date 2019/4/17 16:32
 */
public class AuthUtil {
    private static volatile AuthUtil INSTANCE = null;

    private String mToken;
    private int userId;
    private String nickName;
    private boolean isLogin;


    public AuthUtil() { }

    public static AuthUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (AuthUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthUtil();
                }
            }
        }

        return INSTANCE;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
        if (!login) {
            this.mToken = null;
            this.userId = 0;
            this.nickName = null;
        }
    }
}
