package com.chenhong.android.carsdoor.entity;

import com.chenhong.android.carsdoor.utils.PinYinUtil;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/7/23.
 */
public class car_logo extends BmobObject implements Comparable<car_logo>{


    private int cid;
    private int  id;
    private String name;
    private String nameSpell;
    private BmobFile imagefile;

    public BmobFile getImagefile() {
        return imagefile;
    }

    public void setImagefile(BmobFile imagefile) {
        this.imagefile = imagefile;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpell() {
        return nameSpell;
    }

    public void setNameSpell(String nameSpell) {
        this.nameSpell = nameSpell;
    }

    @Override
    public int compareTo(car_logo car_logo) {
//        String anotherPinyin= PinYinUtil.getPinYin(car_logo.getNameSpell());
        return	getNameSpell().compareTo(car_logo.getNameSpell());
    }
}
