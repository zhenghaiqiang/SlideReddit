package com.zhenghaiqiang.slidereddit.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.zhenghaiqiang.slidereddit.baidu.GsonUtil;
import com.zhenghaiqiang.slidereddit.baidu.HttpGet;
import com.zhenghaiqiang.slidereddit.base.BaseActivity;
import com.zhenghaiqiang.slidereddit.old.OldInfo;
import com.zhenghaiqiang.slidereddit.prefs.SettingData;

import java.util.HashMap;
import java.util.Map;

import me.ccrama.redditslide.R;

public class CalActivity extends BaseActivity {

    private CalendarView cv_view;

    private String subreddit = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cal;
    }

    @Override
    protected void initViews() {
        super.initViews();
        cv_view = findViewById(R.id.cv_view);
        cv_view.setDate(System.currentTimeMillis());

        subreddit = getIntent().getStringExtra("subreddit");

        cv_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String sYear = String.valueOf(year);
                String sMonth = String.valueOf( month + 1);
                String sDay = String.valueOf( dayOfMonth );
                Intent intent = new Intent(CalActivity.this,OldestActivity.class);
                intent.putExtra("year",sYear);
                intent.putExtra("month",sMonth);
                intent.putExtra("day",sDay);
                intent.putExtra("subreddit",subreddit);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initDatas() {
        super.initDatas();

        long start = SettingData.getInstance(CalActivity.this).getSizeLongTypeAll(subreddit);
        if(start>0) {
            cv_view.setMinDate(start);
            cv_view.setDate(start);
            cv_view.setMaxDate(System.currentTimeMillis());
            return;
        }


        new Thread(){
            @Override
            public void run() {
                super.run();

                String before = String.valueOf(System.currentTimeMillis());
                Map<String, String> params =  buildParams("0",before, subreddit);
                String json = HttpGet.get("https://api.pushshift.io/reddit/search/submission/", params);
                OldInfo info = GsonUtil.getGsonIns().fromJson(json, OldInfo.class);
                if(info==null || info.data==null || info.data.size()<=0) {
                    return;
                }

               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long time = info.data.get(0).created_utc * 1000;

                        SettingData.getInstance(CalActivity.this).setRedditType(subreddit,time);


                        cv_view.setMinDate(time);
                        cv_view.setDate(time);
                        cv_view.setMaxDate(System.currentTimeMillis());

                    }
                });

            }
        } .start();

    }

    private Map<String, String> buildParams(String after, String before, String subreddit ) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("after", after);
        params.put("before", before);
        params.put("subreddit", subreddit);
        params.put("sort", "asc");
        params.put("sort_type", "created_utc");
        params.put("size", "1");
        return params;
    }

}
