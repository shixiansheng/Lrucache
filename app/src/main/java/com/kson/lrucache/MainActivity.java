package com.kson.lrucache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.kson.lrucache.adapter.ImageAdapter;
import com.kson.lrucache.common.Api;
import com.kson.lrucache.common.Constants;
import com.kson.lrucache.entity.BaseResponse;
import com.kson.lrucache.entity.ImageEntity;
import com.kson.lrucache.service.ApiService;
import com.kson.lrucache.uitils.OkHttpUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void loadData(View view) {

        Request request = new Request.Builder().url(Api.IMAGES_API)
                .cacheControl(new CacheControl.Builder().noStore().maxAge(60, TimeUnit.SECONDS).build())
                .build();

        OkHttpUtils.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("result1:"+response.body().string());
                System.out.println("cacheResponse:"+response.cacheResponse());
                System.out.println("netResponse:"+response.networkResponse());
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<List<ImageEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<ImageEntity>> value) {

                        List<ImageEntity> list = value.getData();
                        setAdapter(list);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void setAdapter(List<ImageEntity> list) {
        ImageAdapter imageAdapter = new ImageAdapter(this, list);
        mLv.setAdapter(imageAdapter);
    }

    private void initView() {
        mLv = findViewById(R.id.lv);
    }


}
