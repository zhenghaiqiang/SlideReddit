package com.zhenghaiqiang.slidereddit.baidu;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

public class ToastUtil {
    public static void showToast(Context context, String text) {
        new Thread(){
            @Override
            public void run() {
                super.run();

                String content = TransUtil.getTrans(text);

                Activity activity = (Activity) context;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtil.showBottom(context, text,content);
                    }
                });

            }
        } .start();
    }

    public static void showCn(Context context, String text, TextView tv, HashMap<String,String> map) {

        tv.setText("");


        String trans = map.get(text);
        if(!TextUtils.isEmpty(trans)) {
            tv.setText(trans);
            return;
        }


        new Thread(){
            @Override
            public void run() {
                super.run();

                int v = new Random().nextInt(1000);

                SystemClock.sleep(v);

                String content = "";
                String cn = map.get(text);
                if(TextUtils.isEmpty(cn)) {
                    content = TransUtil.getTrans(text);
                    if(!TextUtils.isEmpty(content)) {
                        map.put(text,content);
                    }
                } else {
                    content = map.get(text);
                }
                Activity activity = (Activity) context;
                String finalContent = content;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(finalContent);
                    }
                });
            }
        } .start();
    }
}
