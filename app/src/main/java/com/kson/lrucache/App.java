package com.kson.lrucache;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        //FaceBook调试器,可在Chrome调试网络请求,查看SharePreferences,数据库等
        Stetho.initializeWithDefaults(this);
    }
}
