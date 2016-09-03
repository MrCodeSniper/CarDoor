package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/7/23.
 */
public class news_important extends BmobObject {


    private int nid;
    private int id;
    private String title;
    private String cover_image;
    private int type;

    public BmobFile getImage_list() {
        return image_list;
    }

    public void setImage_list(BmobFile image_list) {
        this.image_list = image_list;
    }

    private BmobFile image_list;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
