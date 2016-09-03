package com.chenhong.android.carsdoor.entity;

import java.util.List;

/**
 * Created by Android on 2016/8/12.
 */
public class Result {
    private String key;

    private String img;

    private List<Carinfo> carinfo ;

    private Auto_series_info auto_series_info;

    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
    public void setImg(String img){
        this.img = img;
    }
    public String getImg(){
        return this.img;
    }
    public void setCarinfo(List<Carinfo> carinfo){
        this.carinfo = carinfo;
    }
    public List<Carinfo> getCarinfo(){
        return this.carinfo;
    }
    public void setAuto_series_info(Auto_series_info auto_series_info){
        this.auto_series_info = auto_series_info;
    }
    public Auto_series_info getAuto_series_info(){
        return this.auto_series_info;
    }


}
