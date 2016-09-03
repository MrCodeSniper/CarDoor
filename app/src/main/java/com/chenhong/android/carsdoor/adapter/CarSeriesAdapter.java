package com.chenhong.android.carsdoor.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.car_series;
import com.chenhong.android.carsdoor.global.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bmob.v3.datatype.BmobFile;


/**
 * 车系找车适配器
 * 
 * @author blue
 * 
 */
public class CarSeriesAdapter extends SimpleBaseAdapter<car_series>
{
	private Context context;
	private List<car_series> datas=new ArrayList<car_series>();

	public CarSeriesAdapter(Context context, List<car_series> datalist) {
		super(context, datalist);
		this.context=context;
		this.datas=datalist;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if(convertView==null){
			convertView=View.inflate(context, R.layout.fragment_find_cover_listview_item, null);
		}
		ViewHolder holder=ViewHolder.getHolder(convertView);
         String name=getSectionForPosition(position);
		//把当前名字的第一个位置取得
		if(getPositionForSection(name)==position){
			holder.cover_list_item_header.setVisibility(View.VISIBLE);
			holder.cover_list_item_header_text.setText(datas.get(position).getName());
		}else {
			holder.cover_list_item_header.setVisibility(View.GONE);
		}
		holder.cover_list_item_content_name.setText(datas.get(position).getAlias_name());
		holder.cover_list_item_content_price.setText(datas.get(position).getDealer_price());
		ImageLoader.getInstance().displayImage(datas.get(position).getImagelist().getFileUrl(), holder.cover_list_item_content_img, Constant.options);
		return convertView;
	}

	static class ViewHolder{
		// 列表头布局
		public LinearLayout cover_list_item_header;
		// 列表头文字
		public TextView cover_list_item_header_text;
		// 列表车系图片
		public ImageView cover_list_item_content_img;
		// 列表内容名字
		public TextView cover_list_item_content_name;
		// 列表内容价格
		public TextView cover_list_item_content_price;
		// 在构造方法中封装findviewByid
		public ViewHolder(View convertView){
			cover_list_item_header = (LinearLayout) convertView.findViewById(R.id.cover_list_item_header);
			cover_list_item_header_text = (TextView) convertView.findViewById(R.id.cover_list_item_header_text);
			cover_list_item_content_img = (ImageView) convertView.findViewById(R.id.cover_list_item_content_img);
			cover_list_item_content_name = (TextView) convertView.findViewById(R.id.cover_list_item_content_name);
			cover_list_item_content_price = (TextView) convertView.findViewById(R.id.cover_list_item_content_price);
		}
		public static ViewHolder getHolder(View convertview){
			ViewHolder holder= (ViewHolder) convertview.getTag();
			if(holder==null){
				holder=new ViewHolder(convertview);
				convertview.setTag(holder);
			}
			return holder;
		}
	}




	public String getSectionForPosition(int position){
		return datas.get(position).getName();
	}
	/**
	 * 返回当前前缀出现的第一个位置
	 */
	public int getPositionForSection(String prefix)
	{
		for (int i = 0; i < getCount(); i++)
		{
			String tmp = datas.get(i).getName();
			if (tmp.equals(prefix))
			{
				return i;
			}
		}
		return -1;
	}










}
