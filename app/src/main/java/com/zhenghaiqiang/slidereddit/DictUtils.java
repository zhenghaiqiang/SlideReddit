package com.zhenghaiqiang.slidereddit;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.Locale;

public class DictUtils {

    public static void setClickble(String str, TextView definitionView, int color) {
        String definition = str.trim();
        definitionView.setMovementMethod(LinkMovementMethod.getInstance());
        definitionView.setText(definition, TextView.BufferType.SPANNABLE);
        Spannable spans = (Spannable) definitionView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(definition);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                .next()) {
            String possibleWord = definition.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = new  DictClickSpan(possibleWord,color);
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }


    public static void jumpToBubei(Context context,String word) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.zhenghaiqiang.bubeidict_new", "com.zhenghaiqiang.bubeidict_new.activity.BubeiWordActivity");
        intent.setComponent(comp);
        intent.putExtra("word", word);
        intent.setAction("android.intent.action.VIEW");
        context.startActivity(intent);
    }
}
