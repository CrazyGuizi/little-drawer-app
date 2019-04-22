package com.littledrawer.event;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 土小贵
 * @date 2019/4/22 11:15
 */
public class EventManager {

    public static void post(boolean isSticky, MessageEvent event) {
        if (isSticky) {
            EventBus.getDefault().postSticky(event);
        } else {
            EventBus.getDefault().post(event);
        }
    }
}
