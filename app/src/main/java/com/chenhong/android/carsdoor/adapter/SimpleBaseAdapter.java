package com.chenhong.android.carsdoor.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class 	SimpleBaseAdapter<T> extends BaseAdapter{
	
	private Context context=null;
    private LayoutInflater inflater=null;
	private List<T> datalist=null;
	
    //��ʼ��
	public SimpleBaseAdapter(Context context,List<T> datalist) {
		super();
		this.context = context;
		this.datalist=datalist;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist!=null?datalist.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datalist!=null?datalist.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public abstract View getView(int arg0, View arg1, ViewGroup arg2);


	public void refreshDatas(List<T> datas)
	{
		this.datalist = datas;
		this.notifyDataSetChanged();
	}
	
	
}
