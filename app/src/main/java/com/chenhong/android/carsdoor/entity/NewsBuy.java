package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Android on 2016/8/16.
 */
public class NewsBuy extends BmobObject {


    private String title;
    private int comment;
    private int bid;
    private BmobFile TitleImage;
    private String website;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public BmobFile getTitleImage() {
        return TitleImage;
    }

    public void setTitleImage(BmobFile titleImage) {
        TitleImage = titleImage;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
