package com.chenhong.android.carsdoor.entity;

import java.util.List;
public class Auto_series_info {
    private List<Cars> cars ;

    public void setCars(List<Cars> cars){
        this.cars = cars;
    }
    public List<Cars> getCars(){
        return this.cars;
    }

}
