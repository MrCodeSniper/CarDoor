package com.chenhong.android.carsdoor.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * 这里只对View相关的操作进行封装，不涉及任何数据，并增加了两个方法，
 * 一个是notifyUpdateView(int position)，调用它之后将会去更新指定位置的视图。
 * 另一个方法是public View updateView(View view, int position)，在这里进行更新视图的具体操作。
 */
public abstract class AbstractViewPagerAdapter extends PagerAdapter{


    //类似Hashmap效率更高的存储方式
    protected SparseArray<View> mViews;

    public AbstractViewPagerAdapter() {
        mViews = new SparseArray<View>();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = newView(position);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    public abstract View newView(int position);

    public void notifyUpdateView(int position) {
        View view = updateView(mViews.get(position), position);
        mViews.put(position, view);
        notifyDataSetChanged();
    }

    public View updateView(View view, int position) {
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }







}
