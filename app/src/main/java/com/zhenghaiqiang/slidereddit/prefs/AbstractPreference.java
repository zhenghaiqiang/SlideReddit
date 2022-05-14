package com.zhenghaiqiang.slidereddit.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AbstractPreference {
    private Context context;
    protected SharedPreferences settings;
    protected SharedPreferences.Editor editor;

    public AbstractPreference(Context context) {
        this.context = context;
        settings = context.getSharedPreferences(getClass().getSimpleName(), 0);
        editor = settings.edit();
    }

}
