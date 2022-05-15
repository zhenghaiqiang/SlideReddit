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

public class BaiduTransTask implements Runnable {
    private final Handler handler;
    private SentenceBean bean;
    private Context context;
    public HashMap<String,String> map;
    public BaiduTransTask(Context context, HashMap<String,String> map, SentenceBean bean, Handler handler) {
        this.context = context;
        this.map = map;
        this.bean = bean;
        this.handler = handler;
    }

    public static boolean isContainChinese(String str) {
        str = str.replace("0","").replace("1","").replace("2","").replace("3","").replace("4","").replace("5","").replace("6","").replace("7","").replace("8","").replace("9","");
        str = str.replace("?","").replace("？","").replace(" ","").replace(" ","");
        str = str.replace("「","");
        if(str.startsWith("《") || str.startsWith("'") || str.startsWith("“")) {
            String chinese = str.replace("《","").replace("'","").replace("“","");
            return isStartWithSymbol(chinese);
        }
        return isStartWithSymbol(str);
    }

    public static boolean isStartWithSymbol(String str) {
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

        boolean chinese = isContainChinese(bean.en);
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
                    bean.tv.setText(cn +"[百度]");
                }
            });
        } else {
            String myCn = TransUtil.getTrans(bean.en);

            if(!TextUtils.isEmpty(myCn)) {
                map.put(bean.en,myCn);
            } else {
                SystemClock.sleep(3000);
                String myCn2 = TransUtil.getTrans(bean.en);
                if(!TextUtils.isEmpty(myCn2)) {
                    map.put(bean.en,myCn2);
                } else {
                    SystemClock.sleep(3000);
                    String myCn3 = TransUtil.getTrans(bean.en);
                    if(!TextUtils.isEmpty(myCn3)) {
                        map.put(bean.en,myCn3);
                    }
                }
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    bean.tv.setVisibility(View.VISIBLE);

                    String text = map.get(bean.en);
                    bean.tv.setText(text +"[百度]");
                }
            });
        }
    }
}
