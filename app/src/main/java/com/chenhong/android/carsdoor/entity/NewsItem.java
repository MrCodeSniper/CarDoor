package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.BmobObject;

public class NewsItem extends BmobObject {

	private String title;
	private String date;
    private long comments;
    


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getComments() {
		return comments;
	}

	public void setComments(long comments) {
		this.comments = comments;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
