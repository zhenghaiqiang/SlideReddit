package com.zhenghaiqiang.slidereddit.translate;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import com.zhenghaiqiang.slidereddit.activity.GoogleInfo;
import com.zhenghaiqiang.slidereddit.activity.GoogleTranslation;
import com.zhenghaiqiang.slidereddit.activity.RequestInfo;
import com.zhenghaiqiang.slidereddit.baidu.GsonUtil;
import com.zhenghaiqiang.slidereddit.baidu.HttpGet;
import com.zhenghaiqiang.slidereddit.baidu.TransUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhq on 2017/5/7.
 */

public class GoogleTransTask implements Runnable {
    private final Handler handler;
    private SentenceBean bean;
    private Context context;
    public HashMap<String,String> map;
    public GoogleTransTask(Context context, HashMap<String,String> map, SentenceBean bean, Handler handler) {
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
                    bean.tv.setText(cn + "[谷歌]");
                }
            });
        } else {
            RequestInfo info = new RequestInfo();
            info.q = bean.en;
            String json = GsonUtil.getGsonIns().toJson(info);
            String html = HttpGet.post("https://translation.googleapis.com/language/translate/v2?key=AIzaSyDyRdYCk6oB1L-r4JrYRrhOrIkwiSN_l9k",null,json);

            GoogleInfo googleInfo = GsonUtil.getGsonIns().fromJson(html,GoogleInfo.class);
            StringBuilder sb = new StringBuilder();
            if(googleInfo!=null&&googleInfo.data!=null&&googleInfo.data.translations!=null&&googleInfo.data.translations.size()>0) {
                for (GoogleTranslation trans:googleInfo.data.translations) {
                    sb.append(trans.translatedText);
                }
            }

            String tran = sb.toString();

            if(!TextUtils.isEmpty(tran)) {
                map.put(bean.en,tran);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    bean.tv.setVisibility(View.VISIBLE);

                    String text = map.get(bean.en);
                    bean.tv.setText(text + "[谷歌]");
                }
            });
        }
    }
}
