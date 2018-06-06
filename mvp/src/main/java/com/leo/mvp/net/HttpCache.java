package com.leo.mvp.net;

import com.leo.mvp.utils.data.AppUtils;

import java.io.File;

import okhttp3.Cache;

/**
 * created by Leo on 2018/6/5 00 : 34
 */


class HttpCache {
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppUtils.getContext().getCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
