package com.kson.lrucache.service;

import com.kson.lrucache.entity.BaseResponse;
import com.kson.lrucache.entity.ImageEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/12/19
 * Description:
 */
public interface ApiService {
    @GET("image/getImages")
    Observable<BaseResponse<List<ImageEntity>>> getData();
}
