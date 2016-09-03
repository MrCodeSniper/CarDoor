package com.chenhong.android.carsdoor.interfa;

import com.chenhong.android.carsdoor.entity.car_logo;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Android on 2016/9/2.
 */
public interface BmobCallback<T>{

    void LoadComplete(List<T> list);

    void LoadError(BmobException e);

    void LoadStart();

}
