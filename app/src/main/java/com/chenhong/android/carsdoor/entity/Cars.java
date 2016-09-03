package com.chenhong.android.carsdoor.entity;

import java.util.List;
public class Cars {
    private String title;

    private List<Car> car ;

    private String total;

    private String moreurl;

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setCar(List<Car> car){
        this.car = car;
    }
    public List<Car> getCar(){
        return this.car;
    }
    public void setTotal(String total){
        this.total = total;
    }
    public String getTotal(){
        return this.total;
    }
    public void setMoreurl(String moreurl){
        this.moreurl = moreurl;
    }
    public String getMoreurl(){
        return this.moreurl;
    }

}