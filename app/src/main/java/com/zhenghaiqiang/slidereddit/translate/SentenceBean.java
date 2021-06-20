package com.zhenghaiqiang.slidereddit.translate;

import android.widget.TextView;

public class SentenceBean {
    public String en = "";
    public String cn = "";
    public TextView tv;

    public SentenceBean(String en, String cn, TextView tv) {
        this.en = en;
        this.cn = cn;
        this.tv = tv;
    }
}
