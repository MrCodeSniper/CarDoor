package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Android on 2016/8/4.
 */
public class _User extends BmobUser{


    private Integer age;
    private String nick;
    private String sex;
    private String city;
    private String icon;
    private BmobFile Theme;


    public BmobFile getTheme() {
        return Theme;
    }

    public void setTheme(BmobFile theme) {
        Theme = theme;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
