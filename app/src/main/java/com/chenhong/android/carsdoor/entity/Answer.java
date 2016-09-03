package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by Android on 2016/8/29.
 */
public class Answer extends BmobObject {

    private String answer;//评论内容

    private BmobPointer user;

    private BmobPointer question;

    private int aid;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public BmobPointer getUser() {
        return user;
    }

    public void setUser(BmobPointer user) {
        this.user = user;
    }

    public BmobPointer getQuestion() {
        return question;
    }

    public void setQuestion(BmobPointer question) {
        this.question = question;
    }
}
