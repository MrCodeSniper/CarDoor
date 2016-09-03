package com.chenhong.android.carsdoor.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.chenhong.android.carsdoor.utils.ActivityStack;
import com.lidroid.xutils.ViewUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;



public abstract class BaseActivity extends BaseFragmentActiviy{
	
	


	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		ActivityStack.getInstace().addActivity(this);
		setContentView(getLayoutID());
//		PushAgent.getInstance(this).onAppStart();
//		//当系统大于4.4以上时才支持 状态栏透明
//		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//			//透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			//透明导航栏
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//			// create our manager instance after the content view is set
//			SystemBarTintManager tintManager = new SystemBarTintManager(this);
//			// enable status bar tint
//			tintManager.setStatusBarTintEnabled(true);
//			tintManager.setTintColor(Color.parseColor("#FF6000"));
//		}





		ViewUtils.inject(this);
		initView();
		
	}

	protected abstract void initView();

	public abstract int getLayoutID();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityStack.getInstace().removeActivity(this);
	}
}
