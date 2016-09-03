package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Android on 2016/9/2.
 */
public class Version {


    private String Latest;

    private String description;


    private BmobFile ApkFile;


    public String getLatest() {
        return Latest;
    }

    public void setLatest(String latest) {
        Latest = latest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BmobFile getApkFile() {
        return ApkFile;
    }

    public void setApkFile(BmobFile apkFile) {
        ApkFile = apkFile;
    }
}
