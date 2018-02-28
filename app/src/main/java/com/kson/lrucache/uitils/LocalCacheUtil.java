package com.kson.lrucache.uitils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kson.lrucache.common.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalCacheUtil {
    private static LocalCacheUtil mInstance;

    private LocalCacheUtil() {

    }

    public static LocalCacheUtil getInstance() {

        if (mInstance == null) {
            synchronized (LocalCacheUtil.class) {
                if (mInstance == null) {

                    mInstance = new LocalCacheUtil();
                }
            }
        }


        return mInstance;
    }

    /**
     * 保存文件到sdcard
     *
     * @param url
     * @param bitmap
     */
    public static void setBitmapToSdcard(final String url, final Bitmap bitmap) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //保存到本地
                try {
                    String name = MD5Util.MD5(url);
                    File file = new File(Constants.IMG_CACHE_PATH, name);
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    if (!file.exists()){
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 从本地获取bitmap
     *
     * @param url
     * @return
     */
    public Bitmap getBitmapFromSdcard(String url) {
        try {
            String name = MD5Util.MD5(url);
            File file = new File(Constants.IMG_CACHE_PATH, name);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


}
