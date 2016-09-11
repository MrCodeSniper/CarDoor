package com.chenhong.android.carsdoor.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * 默认在子线程的service
 * Created by Administrator on 2016/9/9.
 */
public class InitService extends IntentService {

    private static final  String ACTION_INIT="com.chenhong.android.carsdoor.service.InitService.INIT";

    public InitService() {
        super("InitService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

         if(intent!=null){
              final  String action=intent.getAction();
             if(action.equals(ACTION_INIT)){
                 //如果传入action值正确初始化
                 initSDK();
             }

         }


    }


    public static  void start(Context context){
        Intent intent=new Intent(context, InitService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }



    private void initSDK() {
        //第一：默认初始化
        Bmob.initialize(this, "889d433bebc0a39ddcc156c085fd84e2");
        SDKInitializer.initialize(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //初始化sharesdk
        ShareSDK.initSDK(this);


    }


}
