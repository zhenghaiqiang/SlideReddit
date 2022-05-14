package com.zhenghaiqiang.slidereddit.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.zhenghaiqiang.slidereddit.old.OldData;

import java.util.ArrayList;

import me.ccrama.redditslide.R;

public class OldAdapter extends BaseAdapter {

    //声明一个链表和Context对象
    private ArrayList<OldData> mList;
    private Context mContext;

    public OldAdapter(Context context,ArrayList<OldData> list) {
        this.mContext = context;
        this.mList = list;
    }


    @Override
    //获取数据的数量
    public int getCount() {
        return mList.size();
    }

    @Override
    //获取数据的内容
    public Object getItem(int position) {
        return null;
    }

    @Override
    //获取数据的id
    public long getItemId(int position) {
        return position;

    }

    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //加载一个适配器界面
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_old, parent, false);
        //实例化元件
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);

        OldData data = mList.get(position);
        tv_title.setText(data.title);

        String date = DateUtil.getTimeDisplay(data.created_utc *1000);
        tv_date.setText(date);

        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        String thumnail = data.thumbnail;
        if(thumnail.startsWith("http")) {
            Glide.with(mContext).load(thumnail).into(iv_img);
            iv_img.setVisibility(View.VISIBLE);
        } else {
            iv_img.setVisibility(View.GONE);
        }
        return convertView;
    }

}
