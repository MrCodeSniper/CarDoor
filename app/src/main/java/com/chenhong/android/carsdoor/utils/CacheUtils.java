package com.chenhong.android.carsdoor.utils;

import android.util.Log;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Administrator on 2016/8/6.
 */
public class CacheUtils  {

    public static void setCachePolicy(BmobQuery query,Class z){

        boolean isCache = query.hasCachedResult(z);
        if(isCache){
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        }else{
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        Log.e("tazz",isCache+":isCache");
    }



}
