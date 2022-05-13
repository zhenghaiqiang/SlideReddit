package com.zhenghaiqiang.slidereddit.activity;

import android.widget.CalendarView;

import com.zhenghaiqiang.slidereddit.base.BaseActivity;

import me.ccrama.redditslide.R;

public class CalActivity extends BaseActivity {

    private CalendarView cv_view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cal;
    }

    @Override
    protected void initViews() {
        super.initViews();
        cv_view = findViewById(R.id.cv_view);
        cv_view.setDate(System.currentTimeMillis());
    }

    @Override
    protected void initDatas() {
        super.initDatas();

    }
}
