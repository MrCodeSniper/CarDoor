package com.chenhong.android.carsdoor.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.NewsItem;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import cn.bmob.v3.datatype.BmobFile;


public class NewsImportantAdapter extends SimpleBaseAdapter<news_important>{
	
	private Context context;
	private List<news_important> list=new ArrayList<news_important>();
	

	public NewsImportantAdapter(Context context, List<news_important> datalist) {
		super(context, datalist);
		this.context=context;
		this.list=datalist;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(convertView==null){
	        convertView=View.inflate(context, R.layout.news_item,null);
	    }
	    final ViewHolder viewHolder=ViewHolder.getHolder(convertView);
	    viewHolder.tv_title.setText(list.get(position).getTitle());

		if(position>=0&&position<=30){
			String url = list.get(position).getImage_list().getFileUrl();
			ImageLoader.getInstance().displayImage(url, viewHolder.iv_title, Constant.options);
		}else {
			viewHolder.iv_title.setImageResource(R.drawable.empty_photo);
		}


		return convertView;
	}
		
	
	
	
	static class ViewHolder{
	    TextView tv_title;
		ImageView iv_title;
	public ViewHolder(View convertview){
	tv_title= (TextView) convertview.findViewById(R.id.item_tv_title);
		iv_title= (ImageView) convertview.findViewById(R.id.item_iv_img);
	     }
	public  static ViewHolder getHolder(View convertview){
	        ViewHolder viewHolder= (ViewHolder) convertview.getTag();
	if(viewHolder==null){
	            viewHolder=new ViewHolder(convertview);
	            convertview.setTag(viewHolder);
	        }
	return  viewHolder;
	    }
	}
	
	
	
	
	
	
	
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	


