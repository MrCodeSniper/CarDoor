package com.chenhong.android.carsdoor.exception;

/**
 * Created by Android on 2016/7/25.
 */
public class DataNotFoundException extends Exception {


    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return "未找到数据，数据为空";
    }


}
