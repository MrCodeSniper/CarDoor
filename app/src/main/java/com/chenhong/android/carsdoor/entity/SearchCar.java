package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Android on 2016/8/24.
 */
public class SearchCar {


    private String type;
    private String price_tag;
    private String price;
    private String out;
    private String name;
    private BmobFile icon;
    private String gearbox;
    private int cid;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice_tag() {
        return price_tag;
    }

    public void setPrice_tag(String price_tag) {
        this.price_tag = price_tag;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
