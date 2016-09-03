package com.chenhong.android.carsdoor.factory;

import android.content.Context;

import com.chenhong.android.carsdoor.dao.dal.ICarNewsDao;

/**
 * Created by Android on 2016/7/25.
 */
public class CarNewsFactory {

   public static ICarNewsDao newInstance(Context context){
         return new ICarNewsDao(context);
   }






}
