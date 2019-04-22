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
    private String  username;
    private String  password;
    private String  iconUrl;

    public AuthUtil() { }

    public interface OnLoginListener {
        void login();
    }

    public interface OnLogoutListener {
        void logout();
    }

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

    public AuthUtil setToken(String token) {
        mToken = token;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public AuthUtil setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public AuthUtil setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AuthUtil setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthUtil setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public AuthUtil setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
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
            this.username = null;
            this.password = null;
            this.iconUrl = null;
        }
    }
}
