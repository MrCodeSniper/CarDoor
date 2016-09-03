package com.chenhong.android.carsdoor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Administrator on 2016/8/2.
 */
public class CustomPtrHeader extends FrameLayout implements PtrUIHandler {
    ImageView car;
    private AnimationDrawable animationDrawable;
    int i;
    Context context;
    TextView tv_refresh;

    public CustomPtrHeader(Context context) {
        super(context);
        this.context=context;
        init();

    }

    public CustomPtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public CustomPtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

//    @SuppressLint("NewApi")
//    public CustomPtrHeader(Context context, AttributeSet attrs,
//                           int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.context=context;
//        init();
//    }

    /**
     * 初始化
     */
    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.refresh, this);
        car = (ImageView) view.findViewById(R.id.car);
        tv_refresh= (TextView) view.findViewById(R.id.tv_refresh);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        animationDrawable = (AnimationDrawable) car.getDrawable();
        animationDrawable.stop();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
//        float percent = Math.min(1f, currentPercent);
        car.setImageResource(R.drawable.refreshh);
        if(car!=null){
            car.clearAnimation();
            animationDrawable = (AnimationDrawable) car.getDrawable();
            animationDrawable.start();
        }


        final int offsetToRefresh = ptrIndicator.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if(lastPos >= offsetToRefresh) {
            if(isUnderTouch && status == frame.PTR_STATUS_PREPARE) {
                tv_refresh.setText("释放刷新");
            }
        }else if (status == PtrFrameLayout.PTR_STATUS_LOADING) {
           tv_refresh.setText("正在刷新......");
         }else if(status==PtrFrameLayout.PTR_STATUS_COMPLETE){
            tv_refresh.setText("加载完成^_^");
        }else if(status==PtrFrameLayout.PTR_STATUS_INIT){
            tv_refresh.setText("下拉刷新");
        }






    }








}
