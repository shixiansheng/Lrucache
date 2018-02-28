package com.kson.lrucache.service;

import com.kson.lrucache.entity.BaseResponse;
import com.kson.lrucache.entity.ImageEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface ApiService {
    @GET("image/getImages")
    Observable<BaseResponse<List<ImageEntity>>> getData();
}
