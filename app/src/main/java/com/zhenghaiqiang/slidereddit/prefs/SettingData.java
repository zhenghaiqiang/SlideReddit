package com.zhenghaiqiang.slidereddit.prefs;

import android.content.Context;

public class SettingData extends AbstractPreference {
    private static SettingData instance;

    public static SettingData getInstance(Context context) {
        return instance == null ? (instance = new SettingData(context.getApplicationContext())): instance;
    }

    private SettingData(Context context) {
        super(context);
    }



    public void setRedditType(String type,long start) {
        editor.putLong(type,start);
        editor.commit();
    }

    public long getSizeLongTypeAll(String type) {
        return settings.getLong(type,0L);
    }

}
