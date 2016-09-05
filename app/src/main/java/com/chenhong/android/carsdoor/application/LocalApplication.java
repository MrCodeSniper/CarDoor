package com.chenhong.android.carsdoor.application;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.chenhong.android.carsdoor.exception.BaseExceptionHandler;

public class LocalApplication extends BaseApplication{

	@Override
	public BaseExceptionHandler getDefaultUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this) ;
	}
}
