package com.littledrawer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.base.net.AuthUtil;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.littledrawer.LittleDrawerApp;
import com.littledrawer.R;
import com.littledrawer.event.EventManager;
import com.littledrawer.event.UserLoginMessage;
import com.littledrawer.http.bean.User;
import com.littledrawer.http.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

/**
 * @author 土小贵
 * @date 2019/4/18 20:02
 */
public class Util {


    public static String transformDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static NewsColumn getNewsColumn(String colName) {
        NewsColumn column = null;

        if (NewsColumn.SOCIAL.columnName.equals(colName)) {
            column = NewsColumn.SOCIAL;
        } else if (NewsColumn.SCIENCE.columnName.equals(colName)) {
            column = NewsColumn.SCIENCE;
        } else if (NewsColumn.LIFE.columnName.equals(colName)) {
            column = NewsColumn.LIFE;
        } else if (NewsColumn.ENTERTAINMENT.columnName.equals(colName)) {
            column = NewsColumn.ENTERTAINMENT;
        } else if (NewsColumn.AGRICULTURAL.columnName.equals(colName)) {
            column = NewsColumn.AGRICULTURAL;
        }else if (NewsColumn.INTERNATIONAL.columnName.equals(colName)) {
            column = NewsColumn.INTERNATIONAL;
        } else if (NewsColumn.SPORTS.columnName.equals(colName)) {
            column = NewsColumn.SPORTS;
        }

        return column;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void glideLoad(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(context.getDrawable(R.drawable.picture_default))
                .into(imageView);
    }

    /**
     * 读取本地数据登录
     */
    public static void login() {
        // 已经登录了
        if (AuthUtil.getInstance().isLogin()) {
            return;
        }
        String token = SharedPreUtil.getString(Const.KEY_TOKEN);
        // 先判断有没有存有数据
        if ("".equals(token)) {
            return;
        }

        String  username = SharedPreUtil.getString(Const.KEY_USERNAME);
        String  password = SharedPreUtil.getString(Const.KEY_PASSWORD);
        login(username, password);
    }

    /**
     * 输入账号密码登录
     * @param username
     * @param password
     */
    public static void login(String username, String password) {
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put(UserService.USERNAME, username);
        map.put(UserService.PASSWORD, password);
        manager.request(manager.getService(UserService.class)
                .login(map), new BaseListener<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    // 更新数据
                    SharedPreUtil.save(Const.KEY_TOKEN, user.token);
                    SharedPreUtil.save(Const.KEY_USER_ID, user.id);
                    SharedPreUtil.save(Const.KEY_NICK_NAME, user.nickName);
                    SharedPreUtil.save(Const.KEY_USERNAME, user.username);
                    SharedPreUtil.save(Const.KEY_PASSWORD, user.password);
                    SharedPreUtil.save(Const.KEY_ICON_URL, user.iconUrl);

                    AuthUtil.getInstance()
                            .setToken(user.token)
                            .setNickName(user.nickName)
                            .setUserId(user.id)
                            .setIconUrl(user.iconUrl)
                            .setPassword(user.password)
                            .setUsername(user.username)
                            .setLogin(true);
                    EventManager.post(true,
                            new UserLoginMessage(true, user, LittleDrawerApp.getContext()
                            .getString(R.string.login_success)));
                }
            }

            @Override
            public void onFail(BaseException e) {
                Toast.makeText(LittleDrawerApp.getContext(),
                        LittleDrawerApp.getContext().getString(R.string.login_fail),
                        Toast.LENGTH_SHORT).show();
                EventManager.post(true,
                        new UserLoginMessage(true, null,LittleDrawerApp.getContext()
                                .getString(R.string.login_fail) + ","+ e.msg));
            }
        });
    }

    public static void logout() {
        if (!AuthUtil.getInstance().isLogin()) {
            return;
        }

        SharedPreUtil.remove(Const.KEY_TOKEN);
        SharedPreUtil.remove(Const.KEY_USER_ID);
        SharedPreUtil.remove(Const.KEY_NICK_NAME);
        SharedPreUtil.remove(Const.KEY_USERNAME);
        SharedPreUtil.remove(Const.KEY_PASSWORD);
        SharedPreUtil.remove(Const.KEY_ICON_URL);

        AuthUtil.getInstance().setLogin(false);
        EventManager.post(true,
                new UserLoginMessage(false,
                        null,
                        LittleDrawerApp.getContext().getString(R.string.logout)));
    }

    public static int dp2px(float dp) {
        float density = LittleDrawerApp.getContext()
                .getResources().getDisplayMetrics().density;

        return (int)(dp*density + 0.5F);
    }
}
