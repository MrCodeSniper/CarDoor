package com.chenhong.android.carsdoor.cache;

import android.content.Context;
import android.util.Log;

import com.chenhong.android.carsdoor.entity.NewCar;

import java.lang.reflect.ParameterizedType;
import java.util.Hashtable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/8/28.
 */
public abstract class BmobCache<T> {


    public Hashtable<Integer,List<T>> mCache=new Hashtable<Integer,List<T>>();
    private int mkey;
    private Context context;
    private Class t;


    private int count=10;

    private int curPage;		// 当前页的编号，从0开始
    public static final int STATE_REFRESH = 0;// 下拉刷新
    public static final int STATE_MORE = 1;// 加载更多
    public int currentType;

    public BmobCache(Context context,int curPage,int currentType,Class t) {
        this.context = context;
        this.curPage=curPage;
        this.currentType=currentType;
        this.t=t;
    }

    public void getDate(int key){
        mkey=key;
        List<T> cache=mCache.get(key);
        if(cache!=null){
            querySuccess(cache);
        }else {
            getHttpDate(curPage,currentType);
        }
    }


    public void getHttpDate(int page,int actionType){

        final BmobQuery<T> query     = new BmobQuery<T>();
//        query.addWhereEqualTo("DyID", "b10bf7bc79");
        queryDetailorder(query);
        if(actionType == STATE_MORE){
            // 只查询大于等于nid0的数据
            queryDetail(query);
            // 跳过之前页数并去掉重复数据
            query.setSkip(page * count+1);
        }else{
            page=0;
            //跳过第多少条数据，分页时用到，获取下一页数据
            query.setSkip(page);
        }
        // 设置每页数据个数
        query.setLimit(count);


//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = query.hasCachedResult(t);
     Log.e("tazzz",isCache+"");

        if(isCache){
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        }else{
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        query.findObjects(new FindListener<T>() {
            @Override
            public void done(List<T> list, BmobException e) {
                if(e==null){
                    if(list.size()>0){
                        mCache.put(mkey,list);
                        querySuccess(list);
                    }else {
                        queryEmpty();
                    }
                }else {
                    Log.i("smile", "查询失败：" +e.getMessage());
                    queryError();
                }
            }

        });
    }

    public abstract void queryDetailorder(BmobQuery<T> query);
    public abstract void queryDetail(BmobQuery<T> query);
    public abstract void  queryError();
    public abstract void  querySuccess(List<T> list);
    public abstract void  queryEmpty();
}
