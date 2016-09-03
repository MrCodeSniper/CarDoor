package com.chenhong.android.carsdoor.adapter;

import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhong.android.carsdoor.R;

/**
 * Created by Administrator on 2016/7/26.
 */
public abstract class BasePagerAdapter extends PagerAdapter {



    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public abstract  int getCount();


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }


}
