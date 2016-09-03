package com.chenhong.android.carsdoor.dao.dal;

import android.content.Context;

import com.chenhong.android.carsdoor.adapter.NewsImportantAdapter;
import com.chenhong.android.carsdoor.dao.CarNewsDao;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.fragment.newsfragments.NewsImportantFragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 需要实例化调用方法必须写构造
 * Created by Android on 2016/7/25.
 */
public class ICarNewsDao implements CarNewsDao {





    private Context context;

    public ICarNewsDao(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }


    /**
     * 得到query包含了结果数据
     * @param page
     * @param actionType
     * @param count
     * @return
     */

    @Override
    public BmobQuery<news_important> queryDataFromServer(int page, final int actionType,int count) {


        BmobQuery<news_important> query = new BmobQuery<>();
        // 按nid升序查询
        query.order("-nid");
        // 如果是加载更多
        if(actionType == NewsImportantFragment.STATE_MORE){
            // 只查询大于等于nid0的数据
            query.addWhereLessThanOrEqualTo("nid", 158);
            // 跳过之前页数并去掉重复数据
            query.setSkip(page * count+1);
        }else{
            page=0;
            //跳过第多少条数据，分页时用到，获取下一页数据
            query.setSkip(page);
        }
        // 设置每页数据个数
        query.setLimit(count);

         return query;
    }
}
