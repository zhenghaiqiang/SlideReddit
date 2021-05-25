package com.zhenghaiqiang.slidereddit;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class DictClickSpan extends ClickableSpan {

    private String word;
    private int color = Color.BLACK;

    public DictClickSpan(String word, int color) {
        this.word = word;
        this.color = color;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(color);
    }

    @Override
    public void onClick(View widget) {
        DictUtils.jumpToBubei(widget.getContext(),word);
    }
}
