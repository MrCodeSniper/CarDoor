package com.chenhong.android.carsdoor.module;

import android.util.Log;

import com.chenhong.android.carsdoor.entity.NewsBuy;
import com.chenhong.android.carsdoor.interfa.BmobCallback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Android on 2016/9/2.
 */
public class QueryData {

    private BmobCallback bmobCallback;




   public void queryFirstData() {
        BmobQuery<NewsBuy> query = new BmobQuery<NewsBuy>();
//        query.order("-bid");
//       query.setSkip(0);
       Log.e("tazzz","onLoadStart");
       bmobCallback.LoadStart(query);
       query.findObjects(new FindListener<NewsBuy>() {
           @Override
           public void done(List<NewsBuy> list, BmobException e) {
                if(e==null){
                    Log.e("tazzz","onLoadComplete");
                  bmobCallback.LoadComplete(list);
                }else {
                    Log.e("tazzz","onLoadError");
                    bmobCallback.LoadError(e);
                }
           }
       });
    }

    public QueryData(BmobCallback bmobCallback) {
        this.bmobCallback = bmobCallback;
    }
}
