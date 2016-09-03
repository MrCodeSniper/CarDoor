package com.chenhong.android.carsdoor.dao.dal;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chenhong.android.carsdoor.dao.NewsPictureDao;
import com.chenhong.android.carsdoor.entity.NewsTitle;
import com.chenhong.android.carsdoor.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/7/26.
 */
public class INewsPictureDao  implements NewsPictureDao {

    private Context context;


    public INewsPictureDao(Context context) {
        // TODO Auto-generated constructor stub
    this.context=context;
    }

    /**
     * 异步阻塞了赋值
     */
    @Override
    public BmobQuery<NewsTitle> queryDataFromServer() {
        BmobQuery<NewsTitle> query = new BmobQuery<>();
        query.order("id");
        query.addWhereGreaterThanOrEqualTo("id", 0);
        CacheUtils.setCachePolicy(query,NewsTitle.class);
       return  query;

    }






    }


