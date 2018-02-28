package com.kson.lrucache.uitils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.kson.lrucache.R;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/12/19
 * Description:
 */
public class ImageLoaderUtil {
    private static ImageLoaderUtil mInstance;

    private ImageLoaderUtil() {

    }

    public static ImageLoaderUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 加载图片
     * @param iv
     * @param url
     * @param width 宽度
     * @param height 高度
     */
    public void display(ImageView iv,String url,int width,int height){
        //默认占位图
        iv.setImageResource(R.mipmap.ic_launcher);
        Bitmap bitmap = MemoryCacheUtil.getInstance().getBitmapToMemory(url);

        //内存缓存
        if (bitmap!=null){
            System.out.println("我从内存获取");
            iv.setImageBitmap(bitmap);
            return;
        }

        bitmap = LocalCacheUtil.getInstance().getBitmapFromSdcard(url);
        //本地缓存
        if (bitmap!=null){
            System.out.println("我从本地获取");
            iv.setImageBitmap(bitmap);
            return;
        }

        System.out.println("我从网络获取");

        //网络缓存
        NetCacheUtil.getInstance().getBitmapFromNet(iv,url,width,height);

    }

    /**
     * 加载图片
     * @param iv
     * @param url
     */
    public void display(ImageView iv,String url){
        //默认占位图
        iv.setImageResource(R.mipmap.ic_launcher);
        //从内存读取
        Bitmap bitmap = MemoryCacheUtil.getInstance().getBitmapToMemory(url);

        //内存缓存
        if (bitmap!=null){
            System.out.println("我从内存获取");
            iv.setImageBitmap(bitmap);
            return;
        }

        bitmap = LocalCacheUtil.getInstance().getBitmapFromSdcard(url);
        //本地缓存
        if (bitmap!=null){
            System.out.println("我从本地获取");
            iv.setImageBitmap(bitmap);
            return;
        }

        System.out.println("我从网络获取");

        //网络缓存
        NetCacheUtil.getInstance().getBitmapFromNet(iv,url);

    }


}
