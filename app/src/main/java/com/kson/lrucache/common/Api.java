package com.kson.lrucache.common;

import com.kson.lrucache.entity.ImageEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class Api {

    public static final String IMAGES_API = "https://www.zhaoapi.cn/image/getImages";

}
