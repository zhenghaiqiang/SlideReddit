package com.zhenghaiqiang.slidereddit.baidu;


import com.zhenghaiqiang.slidereddit.baidu.bean.BaiduFanyiInfo;
import com.zhenghaiqiang.slidereddit.baidu.bean.BaiduFanyiModule;

public class TransUtil {
    public static String getTrans(String content) {

        StringBuilder sb = new StringBuilder();

        try {

            String appid = "";
            String key = "";

            if(content.length() %2 == 0) {
                appid = "20190425000291503";
                key = "UAgCvNNnnxOi9oV2wyNq";
            } else {
                appid = "20210620000868094";
                key = "UcSC42SlkTYTQPLgQY23";
            }
            TransApi api = new TransApi(appid,key);
            String query = content;
            String json = api.getTransResult(query, "en", "zh");
            BaiduFanyiInfo info = GsonUtil.getGsonIns().fromJson(json, BaiduFanyiInfo.class);
            if (info != null && info.trans_result != null && info.trans_result.size() > 0) {
                for (BaiduFanyiModule module : info.trans_result) {
                    sb.append(module.dst);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
