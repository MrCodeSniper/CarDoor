package com.chenhong.android.carsdoor.entity;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/8/28.
 */
public class City implements Comparable<City> {

    private String CityName;


    private String NameSort;


    public City(String cityName, String nameSort) {
        CityName = cityName;
        NameSort = nameSort;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getNameSort() {
        return NameSort;
    }

    public void setNameSort(String nameSort) {
        NameSort = nameSort;
    }


    @Override
    public int compareTo(City another) {

        return this.NameSort.compareTo(another.getNameSort());
    }
}

