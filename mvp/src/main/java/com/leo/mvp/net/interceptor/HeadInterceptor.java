package com.leo.mvp.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 请求头
 * created by Leo on 2018/6/6 23 : 06
 */


public class HeadInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain
                .proceed(chain.request())
                .newBuilder()
                .addHeader("key","value")
                .build();
    }
}
