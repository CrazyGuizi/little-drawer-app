package com.example.base.util;

/**
 * @author 土小贵
 * @date 2019/4/17 15:50
 */
public class Log {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;

    private static final int level = VERBOSE;

    private static String getTag(Object o) {
        return o.getClass().getSimpleName();
    }


    public static void v(Object o, String s) {
        if (level <= DEBUG) {
            android.util.Log.v(getTag(o), s);
        }
    }

    public static void d(Object o, String s) {
        if (level <= DEBUG) {
//            android.util.Log.d(getTag(o), s);
            System.out.println(getTag(o) + s);
        }
    }

    public static void i(Object o, String s) {
        if (level <= INFO) {
            android.util.Log.i(getTag(o), s);
        }
    }

    public static void w(Object o, String s) {
        if (level <= WARN) {
            android.util.Log.w(getTag(o), s);
        }
    }

    public static void e(Object o, String s) {
        if (level <= ERROR) {
            android.util.Log.e(getTag(o), s);
        }
    }

}
