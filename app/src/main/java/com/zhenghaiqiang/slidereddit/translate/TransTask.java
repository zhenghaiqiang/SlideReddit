package com.zhenghaiqiang.slidereddit.translate;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import com.zhenghaiqiang.slidereddit.baidu.TransUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhq on 2017/5/7.
 */

public class TransTask implements Runnable {
    private final Handler handler;
    private SentenceBean bean;
    private Context context;
    public HashMap<String,String> map;
    public TransTask(Context context,HashMap<String,String> map,SentenceBean bean,Handler handler) {
        this.context = context;
        this.map = map;
        this.bean = bean;
        this.handler = handler;
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    @Override
    public void run() {


        if(TextUtils.isEmpty(bean.en)) {
            return;
        }

        boolean chinese = isContainChinese(bean.en.substring(0,1));
        if(chinese) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    bean.tv.setVisibility(View.GONE);
                }
            });
            return;
        } else {
        }

        String cn = map.get(bean.en);
        if(!TextUtils.isEmpty(cn)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    bean.tv.setVisibility(View.VISIBLE);
                    bean.tv.setText(cn);
                }
            });
        } else {
            String myCn = TransUtil.getTrans(bean.en);

            if(!TextUtils.isEmpty(myCn)) {
                map.put(bean.en,myCn);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    bean.tv.setVisibility(View.VISIBLE);
                    bean.tv.setText(myCn);
                }
            });
        }
    }
}
