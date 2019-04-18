package com.littledrawer.http.bean;

/**
 * @author 土小贵
 * @date 2019/4/17 21:56
 */
public class User {
    public Integer id;
    public String  nickName;
    public String  username;
    public String  password;
    public String  iconUrl;
    public String  token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
