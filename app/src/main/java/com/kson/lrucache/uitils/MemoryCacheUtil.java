package com.kson.lrucache.uitils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.security.KeyStore;
import java.util.HashMap;
public class MemoryCacheUtil {
    private static final String TAG = MemoryCacheUtil.class.getSimpleName();
    private static MemoryCacheUtil mInstance;
    //1.因为强引用,容易造成内存溢出，所以考虑使用下面弱引用的方法
//     private HashMap<String,Bitmap> mStrongCache=new HashMap<>();
    //2.因为在Android2.3+后,系统会优先考虑回收弱引用对象,官方提出使用LruCache
    private HashMap<String, SoftReference<Bitmap>> mSoftCache = new HashMap<>();

    //3.lrucache
    private LruCache<String, Bitmap> mLruCache;


    private MemoryCacheUtil() {
        long memory = Runtime.getRuntime().maxMemory();//最大可用内存，
        System.out.println("memorysize:" + memory);
        mLruCache = new LruCache<String, Bitmap>((int) (memory / 5)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                System.out.println("bytecount:" + byteCount);
                return byteCount;
            }
        };
    }

    public static MemoryCacheUtil getInstance() {

        if (mInstance == null) {
            synchronized (MemoryCacheUtil.class) {
                if (mInstance == null) {

                    mInstance = new MemoryCacheUtil();
                }
            }
        }


        return mInstance;
    }

    /**
     * 放入缓存
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {

        //1.lrucache
        mLruCache.put(MD5Util.MD5(url), bitmap);
        //2.软引用
//        mSoftCache.put(MD5Util.MD5(url),new SoftReference<Bitmap>(bitmap));

    }

    /**
     * 获取缓存
     * @param url
     * @return
     */
    public Bitmap getBitmapToMemory(String url) {
        //1.lrucache
        if (mLruCache != null && mLruCache.size() > 0) {
            Bitmap bitmap = mLruCache.get(MD5Util.MD5(url));
            return bitmap;
        }
        //2.soft软引用
//        if (mSoftCache!=null&&mSoftCache.size()>0){
//            SoftReference<Bitmap> bitmapSoftReference = mSoftCache.get(url);
//            Bitmap bitmap = bitmapSoftReference.get();
//            return bitmap;
//        }
        return null;
    }


}
