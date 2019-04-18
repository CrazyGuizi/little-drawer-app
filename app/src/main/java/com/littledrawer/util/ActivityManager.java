package com.littledrawer.util;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理activity，一键退出app
 *
 * @author 土小贵
 * @date 2019/4/18 10:12
 */
public class ActivityManager {

    private static List<WeakReference<Activity>> mActivityList = new ArrayList<>();

    public static void add(WeakReference<Activity> activity) {
        if (mActivityList == null) {
            mActivityList = new ArrayList<>();
        }
        mActivityList.add(activity);
    }

    public int size() {
        if (mActivityList !=null) {
            return mActivityList.size();
        }

        return 0;
    }

    public boolean remove(WeakReference<Activity> activity) {
        if (mActivityList != null) {
            return mActivityList.remove(activity);
        }

        return false;
    }

    public Activity getTop() {
        if (mActivityList != null && mActivityList.size() > 0) {
            WeakReference<Activity> weakReference = mActivityList.get(mActivityList.size() - 1);
            return weakReference.get();
        }

        return null;
    }

    public void exitApp() {
        if (mActivityList != null && !mActivityList.isEmpty()) {
            for (WeakReference<Activity> reference : mActivityList) {
                Activity activity = reference.get();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
            mActivityList.clear();
        }
    }
}
