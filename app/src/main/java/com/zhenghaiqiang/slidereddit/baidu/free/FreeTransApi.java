package com.zhenghaiqiang.slidereddit.baidu.free;

import com.zhenghaiqiang.slidereddit.baidu.HttpGet;

import java.util.HashMap;
import java.util.Map;

public class FreeTransApi {
    private static final String TRANS_API_HOST = "https://fanyi.baidu.com/transapi";

    public FreeTransApi() {

    }

    private Map<String, String> buildParams(String query) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("from", "auto");
        params.put("to", "zh");
        params.put("query", query);

        return params;
    }

    public String getTransResult(String query) {
        Map<String, String> params = buildParams(query);
        return HttpGet.get(TRANS_API_HOST, params);
    }
}
