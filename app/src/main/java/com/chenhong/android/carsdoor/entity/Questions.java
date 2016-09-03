package com.chenhong.android.carsdoor.entity;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Android on 2016/8/5.
 */
public class Questions extends BmobObject implements Serializable{

    private boolean isDone;
    private BmobFile UserIcon;
    private String UserName;
    private String City;
    private String QuestionTitle;
    private String QuestionContent;
    private int AnswerCount;
    private String SendTime;
    private BmobFile Theme;
    private String UserImage;

    private String UserTheme;


    public String getUserTheme() {
        return UserTheme;
    }

    public void setUserTheme(String userTheme) {
        UserTheme = userTheme;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public BmobFile getTheme() {
        return Theme;
    }

    public void setTheme(BmobFile theme) {
        Theme = theme;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }


    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public BmobFile getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(BmobFile userIcon) {
        UserIcon = userIcon;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getQuestionTitle() {
        return QuestionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        QuestionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return QuestionContent;
    }

    public void setQuestionContent(String questionContent) {
        QuestionContent = questionContent;
    }

    public int getAnswerCount() {
        return AnswerCount;
    }

    public void setAnswerCount(int answerCount) {
        AnswerCount = answerCount;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "isDone=" + isDone +
                ", UserIcon=" + UserIcon +
                ", UserName='" + UserName + '\'' +
                ", City='" + City + '\'' +
                ", QuestionTitle='" + QuestionTitle + '\'' +
                ", QuestionContent='" + QuestionContent + '\'' +
                ", AnswerCount=" + AnswerCount +
                ", SendTime='" + SendTime + '\'' +
                '}';
    }
}
