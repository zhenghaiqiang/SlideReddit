package com.zhenghaiqiang.slidereddit.baidu;

import com.google.gson.Gson;

public class GsonUtil {
    private static Gson gson = new Gson();

    public static Gson getGsonIns() {
        return gson;
    }
}
