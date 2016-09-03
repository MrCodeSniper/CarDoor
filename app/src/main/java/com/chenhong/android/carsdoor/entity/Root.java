package com.chenhong.android.carsdoor.entity;

/**
 * Created by Android on 2016/8/12.
 */
public class Root {
    private String reason;

    private Result result;

    private int error_code;

    public void setReason(String reason){
        this.reason = reason;
    }
    public String getReason(){
        return this.reason;
    }
    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }
    public void setError_code(int error_code){
        this.error_code = error_code;
    }
    public int getError_code(){
        return this.error_code;
    }


}


