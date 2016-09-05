package com.chenhong.android.carsdoor.interfa;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Android on 2016/9/2.
 */
public interface BmobCallback<T>{

    void LoadComplete(List<T> list);

    void LoadError(BmobException e);

    void LoadStart(BmobQuery<T> query);

}
