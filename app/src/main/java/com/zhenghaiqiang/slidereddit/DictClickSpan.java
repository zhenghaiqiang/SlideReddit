package com.zhenghaiqiang.slidereddit;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import me.ccrama.redditslide.Activities.Website;
import me.ccrama.redditslide.util.LinkUtil;

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

        boolean isInstalled = DictUtils.checkApkExist(widget.getContext(),"com.zhenghaiqiang.bubeidict_new");
        if(isInstalled) {
            DictUtils.jumpToBubei(widget.getContext(),word);
        } else   {
            Intent i = new Intent(widget.getContext(), Website.class);
            i.putExtra(LinkUtil.EXTRA_URL, "https://www.iciba.com/word?w=" + word);
            widget.getContext().startActivity(i);
        }

    }
}
