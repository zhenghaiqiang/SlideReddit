package com.zhenghaiqiang.slidereddit.base;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {
    private SparseArray<View> mViews;
    private View mRootView;

    public ViewHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
        this.mViews = new SparseArray<>();
        mRootView = inflater.inflate(layoutId, parent, false);
    }

    public <T extends View> T get(int resId) {
        View view = mViews.get(resId);
        if(view == null) {
            view = mRootView.findViewById(resId);
            mViews.put(resId,view);
        }
        return (T) view;
    }

    public View getRootView() {
        return mRootView;
    }

    public void setOnClickListener(View.OnClickListener l, int id) {
        get(id).setOnClickListener(l);
    }

}
