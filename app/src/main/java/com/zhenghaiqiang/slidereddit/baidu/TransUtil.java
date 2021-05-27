package com.zhenghaiqiang.slidereddit.baidu;


import com.zhenghaiqiang.slidereddit.baidu.bean.BaiduFanyiInfo;
import com.zhenghaiqiang.slidereddit.baidu.bean.BaiduFanyiModule;

public class TransUtil {
    public static String getTrans(String content) {

        StringBuilder sb = new StringBuilder();

        try {
            TransApi api = new TransApi("20190425000291503", "UAgCvNNnnxOi9oV2wyNq");
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
