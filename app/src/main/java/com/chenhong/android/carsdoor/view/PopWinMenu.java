package com.chenhong.android.carsdoor.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chenhong.android.carsdoor.R;

/**
 * Created by Android on 2016/8/17.
 */
public class PopWinMenu extends PopupWindow {

    private View mainView;
    private LinearLayout layouthelp, layoutmsg;

    public PopWinMenu(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2){
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popup_layout, null);
        //分享布局
        layouthelp = ((LinearLayout)mainView.findViewById(R.id.help));
        //复制布局
        layoutmsg = (LinearLayout)mainView.findViewById(R.id.msg);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            layouthelp.setOnClickListener(paramOnClickListener);
            layoutmsg.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));

}










}
