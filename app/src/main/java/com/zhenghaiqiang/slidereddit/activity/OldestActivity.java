package com.zhenghaiqiang.slidereddit.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zhenghaiqiang.slidereddit.baidu.GsonUtil;
import com.zhenghaiqiang.slidereddit.baidu.HttpGet;
import com.zhenghaiqiang.slidereddit.base.BaseActivity;
import com.zhenghaiqiang.slidereddit.old.OldData;
import com.zhenghaiqiang.slidereddit.old.OldInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.ccrama.redditslide.Activities.CommentsScreen;
import me.ccrama.redditslide.Activities.CommentsScreenSingle;
import me.ccrama.redditslide.R;
import me.ccrama.redditslide.Reddit;


public class OldestActivity  extends BaseActivity {

    private ListView recycler_view;

    private String year = "";
    private String month = "";
    private String day = "";

    private long start;
    private long end;


    private String subreddit = "";
    @Override
    protected int getLayoutId() {
        return R.layout.layout_oldest;
    }

    @Override
    protected void initViews() {
        super.initViews();
        recycler_view = findViewById(R.id.recycler_view);

        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");
        day = getIntent().getStringExtra("day");

        subreddit = getIntent().getStringExtra("subreddit");

        if(month.length()<=1) {
            month = "0" + month;
        }
        if(day.length()<=1) {
            day = "0" + day;
        }

        start = DateUtil.strToLong3(year +"-" +month +"-" +day)/1000;


        Calendar calendar = Calendar.getInstance();
        Date date = new Date(DateUtil.strToLong3(year +"-" +month +"-" +day));
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        end = calendar.getTime().getTime()/1000;


    }

    @Override
    protected void initDatas() {
        super.initDatas();

        new Thread(){
            @Override
            public void run() {
                super.run();

                Map<String, String> params =  buildParams(start +"",end +"", subreddit);
                String json = HttpGet.get("https://api.pushshift.io/reddit/search/submission/", params);
                OldInfo info = GsonUtil.getGsonIns().fromJson(json, OldInfo.class);
                if(info==null || info.data==null || info.data.size()<=0) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        OldAdapter adapter = new OldAdapter(OldestActivity.this,info.data);

                        recycler_view.setAdapter(adapter);


                        recycler_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                OldData data = info.data.get(position);


                                Intent i = new Intent(OldestActivity.this, CommentsScreenSingle.class);

                                i.putExtra(CommentsScreenSingle.EXTRA_SUBREDDIT, data.subreddit);
                                i.putExtra(CommentsScreenSingle.EXTRA_CONTEXT, Reddit.EMPTY_STRING);
                                i.putExtra(CommentsScreenSingle.EXTRA_NP, false);
                                i.putExtra(CommentsScreenSingle.EXTRA_SUBMISSION, data.id);
                                startActivity(i);
                            }
                        });

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
        params.put("size", "500");
        return params;
    }



}
