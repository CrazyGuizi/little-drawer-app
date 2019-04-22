package com.littledrawer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Set;

/**
 * @author 土小贵
 * @date 2019/4/18 10:29
 */
public class SharedPreUtil {
    private static SharedPreferences sPreferences;
    private static SharedPreferences.Editor sEditor;

    private SharedPreUtil() {}

    public static void init(Context context) {
        if (context !=  null) {
            sPreferences = context.getSharedPreferences(Const.SHARED_PREFERENCES_NAME,
                    Context.MODE_PRIVATE);
            if (sPreferences != null && sEditor == null) {
                sEditor = sPreferences.edit();
            }
        }
    }

    public static boolean save(String key, int val) {
        if (sEditor != null) {
            sEditor.putInt(key, val);
            return sEditor.commit();
        }

        return false;
    }

    public static boolean save(String key, boolean val) {
        if (sEditor != null) {
            sEditor.putBoolean(key, val);
            return sEditor.commit();
        }

        return false;
    }

    public static boolean save(String key, float val) {
        if (sEditor != null) {
            sEditor.putFloat(key, val);
            return sEditor.commit();
        }

        return false;
    }

    public static boolean save(String key, long val) {
        if (sEditor != null) {
            sEditor.putLong(key, val);
            return sEditor.commit();
        }

        return false;
    }

    public static boolean save(String key, String val) {
        if (sEditor != null) {
            sEditor.putString(key, val);
            return sEditor.commit();
        }

        return false;
    }

    public static boolean save(String key, Set<String> val) {
        if (sEditor != null) {
            sEditor.putStringSet(key, val);
            return sEditor.commit();
        }

        return false;
    }

    public static int getInt(String key) {
        if (sPreferences != null && !TextUtils.isEmpty(key)) {
            return sPreferences.getInt(key, 0);
        }

        return 0;
    }

    public static String getString(String key) {
        if (sPreferences != null && !TextUtils.isEmpty(key)) {
            return sPreferences.getString(key, "");
        }

        return "";
    }

    public static void remove(String key) {
        if (sEditor != null && !TextUtils.isEmpty(key)) {
            sEditor.remove(key);
            sEditor.commit();
        }
    }

    public static void clear() {
        if (sEditor != null) {
            sEditor.clear();
            sEditor.commit();
        }
    }

}
