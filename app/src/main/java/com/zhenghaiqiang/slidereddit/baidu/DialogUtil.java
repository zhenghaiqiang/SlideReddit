package com.zhenghaiqiang.slidereddit.baidu;

import android.app.Activity;
import android.content.Context;

import com.cocosw.bottomsheet.BottomSheet;

public class DialogUtil {
    public static void showBottom( final Context mContext,
                                                 String title,String content) {
        BottomSheet.Builder b =
                new BottomSheet.Builder((Activity) mContext);
        b.sheet(1,title);
        b.sheet(2,content);
        b.show();
    }
}
