package com.chenhong.android.carsdoor.module;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.chenhong.android.carsdoor.adapter.CarLogoAdapter;
import com.chenhong.android.carsdoor.entity.car_logo;
import com.chenhong.android.carsdoor.interfa.BmobCallback;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Android on 2016/9/2.
 */
public class QueryData {

    private BmobCallback bmobCallback;

    public void setBmobCallback(BmobCallback bmobCallback){
        this.bmobCallback=bmobCallback;
    }


   public boolean queryfirstdata() {
        boolean result;
        BmobQuery<car_logo> query = new BmobQuery<car_logo>();
        query.order("cid");
        query.addWhereGreaterThanOrEqualTo("cid", 1);
        query.setLimit(60);
        //执行查询方法
        if(bmobCallback!=null){
            bmobCallback.LoadStart();
            query.findObjects(new FindListener<car_logo>() {
                @Override
                public void done(List<car_logo> list, final BmobException e) {
                    if (e == null) {
                        bmobCallback.LoadComplete(list);
                    } else {
                        bmobCallback.LoadError(e);
                    }
                }
            });
           result=true;
        }else {
            result=false;
        }
        return result;
    }







}
