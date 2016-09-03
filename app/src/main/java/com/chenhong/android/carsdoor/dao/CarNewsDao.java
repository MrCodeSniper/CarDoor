package com.chenhong.android.carsdoor.dao;

import com.chenhong.android.carsdoor.entity.news_important;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Android on 2016/7/25.
 */
public interface CarNewsDao {


    BmobQuery<news_important> queryDataFromServer(int page, int actionType, int count);





}
