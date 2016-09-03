package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Android on 2016/8/11.
 */
public class NewCar extends BmobObject{


    private String NewCarTitle;
    private int ncid;
    private String from;
    private String WebViewUrl;
    private BmobFile TitlePic;
    private BmobFile TitlePic2;
    private BmobFile TitlePic3;
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getNewCarTitle() {
        return NewCarTitle;
    }

    public void setNewCarTitle(String newCarTitle) {
        NewCarTitle = newCarTitle;
    }

    public int getNcid() {
        return ncid;
    }

    public void setNcid(int ncid) {
        this.ncid = ncid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getWebViewUrl() {
        return WebViewUrl;
    }

    public void setWebViewUrl(String webViewUrl) {
        WebViewUrl = webViewUrl;
    }

    public BmobFile getTitlePic() {
        return TitlePic;
    }

    public void setTitlePic(BmobFile titlePic) {
        TitlePic = titlePic;
    }

    public BmobFile getTitlePic2() {
        return TitlePic2;
    }

    public void setTitlePic2(BmobFile titlePic2) {
        TitlePic2 = titlePic2;
    }

    public BmobFile getTitlePic3() {
        return TitlePic3;
    }

    public void setTitlePic3(BmobFile titlePic3) {
        TitlePic3 = titlePic3;
    }
}
