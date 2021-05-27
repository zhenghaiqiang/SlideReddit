package com.zhenghaiqiang.slidereddit.baidu;

import android.app.Activity;
import android.content.Context;

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
}
