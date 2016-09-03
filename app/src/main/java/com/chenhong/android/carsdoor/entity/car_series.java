package com.chenhong.android.carsdoor.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 车系找车
 * 
 * @author blue
 */
public class car_series extends BmobObject
{
	private int cid;
	private int id;
	private BmobFile imagelist;
	private String dealer_price;
	private String name;
	private int alias_id;
	private String alias_name;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BmobFile getImagelist() {
		return imagelist;
	}

	public void setImagelist(BmobFile imagelist) {
		this.imagelist = imagelist;
	}

	public String getDealer_price() {
		return dealer_price;
	}

	public void setDealer_price(String dealer_price) {
		this.dealer_price = dealer_price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAlias_id() {
		return alias_id;
	}

	public void setAlias_id(int alias_id) {
		this.alias_id = alias_id;
	}

	public String getAlias_name() {
		return alias_name;
	}

	public void setAlias_name(String alias_name) {
		this.alias_name = alias_name;
	}
}
