package com.zhenghaiqiang.slidereddit.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    private ViewHolder mViewHolder;
    private View mRoot;
    protected Handler mHandler = new Handler();
    protected LayoutInflater mInflater;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mViewHolder = new ViewHolder(inflater, container, getLayoutId());
            mRoot = mViewHolder.getRootView();
            initViews(mViewHolder, container);
        }
        return mViewHolder.getRootView();
    }

    protected abstract void initViews(ViewHolder holder, ViewGroup root);
}
