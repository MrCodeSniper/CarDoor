package com.chenhong.android.carsdoor.dao;

import com.chenhong.android.carsdoor.dao.dal.INewsPictureDao;
import com.chenhong.android.carsdoor.entity.NewsTitle;
import com.chenhong.android.carsdoor.entity.news_important;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface NewsPictureDao {

    BmobQuery<NewsTitle> queryDataFromServer();


}
