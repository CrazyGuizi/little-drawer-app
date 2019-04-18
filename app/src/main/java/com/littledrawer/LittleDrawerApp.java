package com.littledrawer;

import android.app.Application;
import android.content.Context;

import com.example.base.net.AuthUtil;
import com.littledrawer.util.ActivityManager;
import com.littledrawer.util.SharedPreUtil;

/**
 * @author 土小贵
 * @date 2019/4/18 10:11
 */
public class LittleDrawerApp extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SharedPreUtil.init(this);
    }

    public static Context getContext() {
        return sContext;
    }
}
