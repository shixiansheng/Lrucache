package com.kson.lrucache.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kson.lrucache.R;
import com.kson.lrucache.entity.ImageEntity;
import com.kson.lrucache.uitils.ImageLoaderUtil;
import com.kson.lrucache.uitils.MD5Util;

import java.util.List;
public class ImageAdapter extends BaseAdapter {
    List<ImageEntity> mList;
    private Context mContext;

    public ImageAdapter(Context mContext,List<ImageEntity> mList ) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.image_item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.iv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setTag(MD5Util.MD5(mList.get(position).getImgurl()));

        if (viewHolder.imageView.getTag()!=null&&viewHolder.imageView.getTag().equals(MD5Util.MD5(mList.get(position).getImgurl()))){

            ImageLoaderUtil.getInstance().display(viewHolder.imageView,mList.get(position).getImgurl());
        }
        return convertView;
    }

    static class ViewHolder{
        private ImageView imageView;

    }
}
