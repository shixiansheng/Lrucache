package com.kson.lrucache.uitils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/12/19
 * Description:
 */
public class NetCacheUtil {
    private static final String TAG = NetCacheUtil.class.getSimpleName();
    private static NetCacheUtil mInstance;

    private NetCacheUtil() {

    }

    public static NetCacheUtil getInstance() {

        if (mInstance == null) {
            synchronized (NetCacheUtil.class) {
                if (mInstance == null) {

                    mInstance = new NetCacheUtil();
                }
            }
        }


        return mInstance;
    }

    /**
     * 从网络下载图片
     *
     * @param ivPic 显示图片的imageview
     * @param url   下载图片的网络地址
     */
    public void getBitmapFromNet(ImageView ivPic, String url, int width, int height) {
        new BitmapAsyncTask().execute(ivPic, url, width, height);//启动AsyncTask

    }
    /**
     * 从网络下载图片
     *
     * @param ivPic 显示图片的imageview
     * @param url   下载图片的网络地址
     */
    public void getBitmapFromNet(ImageView ivPic, String url) {
        //启动AsyncTask
        new BitmapAsyncTask().execute(ivPic, url, 1, 1);

    }


    class BitmapAsyncTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView mIv;
        private String url;
        private int width;
        private int height;

        @Override
        protected Bitmap doInBackground(Object... objects) {
            mIv = (ImageView) objects[0];
            url = (String) objects[1];
            width = (int) objects[2];
            height = (int) objects[3];
            return downloadBitmap(url, width, height);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            System.out.println("bitmap:" + bitmap);
            if (bitmap==null){
                return;
            }

            mIv.setImageBitmap(bitmap);//绘制ui
//            //保存到sdcard
            LocalCacheUtil.getInstance().setBitmapToSdcard(url, bitmap);
//            //保存到内存
            MemoryCacheUtil.getInstance().setBitmapToMemory(url,bitmap);

        }
    }

    /**
     * 二次采样
     *
     * @param string
     * @return
     */
    private Bitmap downloadBitmap(String string, int width, int height) {

        System.out.println("urlurl:"+string);
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bis = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            URL url = new URL(string);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            System.out.println(":::" + httpURLConnection.getResponseCode());
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                bis = new BufferedInputStream(httpURLConnection.getInputStream());

                byte[] buffer = new byte[1024 * 8];

                int c = 0;

                while ((c = bis.read(buffer)) != -1) {

                    baos.write(buffer, 0, c);

                    baos.flush();

                }


//                InputStream inputStream = httpURLConnection.getInputStream();
                //二次采样
                //第一次
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, options);

//                BitmapFactory.decodeStream(inputStream, null, options);
                int oldHeight = options.outHeight;
                int oldWidth = options.outWidth;
                Log.i(TAG, "第一次采样高度是：" + oldHeight + "，第一次采样宽度是：" + oldWidth);

                //设置恰当的inSampleSize是解决该问题的关键之一。
                //查看Android源码，我们得知，为了得到恰当的inSampleSize，Android提供了一种动态计算的方法。
//                int ratioHeight = oldHeight / height;
//                int ratioWidth = oldWidth / width;
//                options.inSampleSize = ratioHeight > ratioWidth ? ratioWidth
//                        : ratioHeight;
//                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inSampleSize = 2;
                //第二次采样
                options.inJustDecodeBounds = false;
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                Bitmap bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, options);
                Log.i(TAG, "第二次高度是：" + options.outHeight + "，第二次宽度是：" + options.outWidth);
                return compressImage(bitmap);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
        }

        return null;
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG,100 , baos);

        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少10
            options -= 10;

        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);

        return bitmap;

    }
}
