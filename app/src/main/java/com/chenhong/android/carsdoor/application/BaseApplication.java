package com.chenhong.android.carsdoor.application;



import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.mapapi.SDKInitializer;
import com.chenhong.android.carsdoor.exception.BaseExceptionHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;


public abstract class BaseApplication extends Application {

	private SharedPreferences sp;

	private Context applicationcontext;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();


		applicationcontext = getApplicationContext();
		//第一：默认初始化
		Bmob.initialize(this, "889d433bebc0a39ddcc156c085fd84e2");
		SDKInitializer.initialize(getApplicationContext());

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		sp = getSharedPreferences("user", MODE_PRIVATE);
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
		//初始化sharesdk
		ShareSDK.initSDK(this);



	}

	public abstract BaseExceptionHandler getDefaultUncaughtExceptionHandler();

}
