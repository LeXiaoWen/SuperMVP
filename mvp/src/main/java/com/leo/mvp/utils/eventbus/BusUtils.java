package com.leo.mvp.utils.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Leo on 2017/6/26.
 */

public class BusUtils {
    public BusUtils() {
    }

    public static void postMessage(Object event) {
        EventBus.getDefault().post(event);
    }
}
