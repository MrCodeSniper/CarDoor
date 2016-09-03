package com.chenhong.android.carsdoor.factory;

import android.content.Context;

import com.chenhong.android.carsdoor.dao.dal.ICarNewsDao;
import com.chenhong.android.carsdoor.dao.dal.INewsPictureDao;

/**
 * Created by Android on 2016/7/27.
 */
public class NewsPictureFactory {
    public static INewsPictureDao newInstance(Context context){
        return new INewsPictureDao(context);
    }
}
