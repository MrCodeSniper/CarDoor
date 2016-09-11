package com.chenhong.android.carsdoor.application;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;

import com.chenhong.android.carsdoor.exception.BaseExceptionHandler;
import com.chenhong.android.carsdoor.service.InitService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public abstract class BaseApplication extends Application {

	private SharedPreferences sp;

	private Context applicationcontext;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();


		applicationcontext = getApplicationContext();
		sp = getSharedPreferences("user", MODE_PRIVATE);
		Debug.startMethodTracing("CarsDoor");
		applicationcontext = getApplicationContext();

//		if (getDefaultUncaughtExceptionHandler() == null) {
//			Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(
//					applicationcontext));
//		} else {
//			Thread.setDefaultUncaughtExceptionHandler(getDefaultUncaughtExceptionHandler());
//		}

		//创建默认的ImageLoader配置参数
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);
		ImageLoader.getInstance().init(configuration);
		InitService.start(this);
		Debug.stopMethodTracing();




	}

	public abstract BaseExceptionHandler getDefaultUncaughtExceptionHandler();

}
