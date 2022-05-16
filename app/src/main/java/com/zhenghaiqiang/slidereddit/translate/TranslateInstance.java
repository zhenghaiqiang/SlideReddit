package com.zhenghaiqiang.slidereddit.translate;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhq on 2017/5/7.
 */

public class TranslateInstance {
    private static volatile TranslateInstance instance;
    private ExecutorService taskExecutor;
    private Context context;
    public HashMap<String,String> map;

    private TranslateInstance(Context context,HashMap<String,String> map) {
        taskExecutor = Executors.newFixedThreadPool(10);
        this.context = context;
        this.map = map;
    }

    public void shutdown() {
        taskExecutor.shutdownNow();
    }

    public static TranslateInstance getInstance(Context context,HashMap<String,String> map) {
        if (instance == null) {
            synchronized (TranslateInstance.class) {
                if (instance == null) {
                    instance = new TranslateInstance(context,map);
                }
            }
        }
        return instance;
    }

    public void translate(SentenceBean bean) {

        if(TextUtils.isEmpty(bean.en)) {
            return;
        }

        String cn = map.get(bean.en);
        if(!TextUtils.isEmpty(cn)) {
            bean.tv.setVisibility(View.VISIBLE);
            bean.tv.setText(cn);
        } else {
            bean.tv.setVisibility(View.GONE);
        }


        BaiduTransTask task = new BaiduTransTask(context,map, bean, defineHandler());
        if (taskExecutor != null && !taskExecutor.isShutdown()) {
            taskExecutor.execute(task);
        } else {
            taskExecutor = Executors.newFixedThreadPool(5);
            taskExecutor.execute(task);
        }
    }

    public void translateGoogle(SentenceBean bean) {

        if(TextUtils.isEmpty(bean.en)) {
            return;
        }

        String cn = map.get(bean.en);
        if(!TextUtils.isEmpty(cn)) {
            bean.tv.setVisibility(View.VISIBLE);
            bean.tv.setText(cn);
        } else {
            bean.tv.setVisibility(View.GONE);
        }


        GoogleTransTask task = new GoogleTransTask(context,map, bean, defineHandler());
        if (taskExecutor != null && !taskExecutor.isShutdown()) {
            taskExecutor.execute(task);
        } else {
            taskExecutor = Executors.newFixedThreadPool(5);
            taskExecutor.execute(task);
        }
    }

    public Handler defineHandler() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            return new Handler();
        } else {
            return new Handler();
        }
    }
}
