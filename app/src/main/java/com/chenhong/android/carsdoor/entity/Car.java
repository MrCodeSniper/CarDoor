package com.chenhong.android.carsdoor.entity;

public class Car {
    private String name;

    private String url;

    private String trans;

    private String guide_price;

    private String price;

    private String link_name;

    private String link_url;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setTrans(String trans){
        this.trans = trans;
    }
    public String getTrans(){
        return this.trans;
    }
    public void setGuide_price(String guide_price){
        this.guide_price = guide_price;
    }
    public String getGuide_price(){
        return this.guide_price;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return this.price;
    }
    public void setLink_name(String link_name){
        this.link_name = link_name;
    }
    public String getLink_name(){
        return this.link_name;
    }
    public void setLink_url(String link_url){
        this.link_url = link_url;
    }
    public String getLink_url(){
        return this.link_url;
    }

}