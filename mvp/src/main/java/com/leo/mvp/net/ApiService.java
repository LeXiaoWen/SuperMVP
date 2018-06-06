package com.leo.mvp.net;

import com.leo.mvp.net.bean.LoginBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * created by Leo on 2018/6/6 23 : 01
 */


public interface ApiService {
    @FormUrlEncoded
    @POST("login/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("login/register")
    Observable<LoginBean> register(@Part("username") RequestBody nameBody, @Part("password") RequestBody passwordBody, @Part("photo\"; filename=\"photo.jpg\"") RequestBody fileBody);

}
