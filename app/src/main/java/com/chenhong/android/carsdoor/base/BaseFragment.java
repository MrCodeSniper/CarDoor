package com.chenhong.android.carsdoor.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Android on 2016/7/29.
 */
public abstract class BaseFragment extends Fragment{


    private Context context;
    private boolean isLoading=false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }



    @Override
    public void onResume() {
        super.onResume();
        if (!isLoading) {
            initLoading();
            isLoading = true;
        }
    }
        protected abstract void initLoading();





    }














