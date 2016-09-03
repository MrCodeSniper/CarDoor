package com.chenhong.android.carsdoor.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.SearchActivity;
import com.chenhong.android.carsdoor.fragment.findfragments.FindBrandFragment;
import com.chenhong.android.carsdoor.fragment.findfragments.FindFilterFragment;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


/**
 * 找车
 * 
 * @author blue
 */
public class FindFragment extends BaseFragment
{


   private  boolean isBrand=true;

	// 选择开关
	@ViewInject(R.id.fragment_find_llyt_switch)
	LinearLayout fragment_find_llyt_switch;
	// 品牌找车
	@ViewInject(R.id.fragment_find_tv_brand)
	TextView fragment_find_tv_brand;
	// 精准找车
	@ViewInject(R.id.fragment_find_tv_filter)
	TextView fragment_find_tv_filter;
	@ViewInject(R.id.fragment_find_iv_search)
	ImageView iv_search;


	private FragmentTransaction ft;

	private FindBrandFragment findBrandFragment;
	private FindFilterFragment findFilterFragment;
	private Fragment mContent;

	@Override
	protected int getLayoutId()
	{
		return  R.layout.fragment_find;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void initParams()
	{
		findBrandFragment = new FindBrandFragment();
		findFilterFragment=new FindFilterFragment();
         if(isBrand){
			 ft = getChildFragmentManager().beginTransaction();
//			 find_click();
			 ft.replace(R.id.fragment_find_flyt_content, findBrandFragment);
			 ft.commit();
			 isBrand=false;
		 }
	}

	@Override
	protected void initLoading() {


	}



	// 控件点击事件
	@OnClick({ R.id.fragment_find_tv_brand, R.id.fragment_find_tv_filter,R.id.fragment_find_iv_search })
	public void viewOnClick(View view)
	{
		ft = getChildFragmentManager().beginTransaction();

		switch (view.getId())
		{
			// 品牌找车
			case R.id.fragment_find_tv_brand:
				find_click();
				switchContent(findFilterFragment,findBrandFragment);
				break;
			// 精准找车
			case R.id.fragment_find_tv_filter:
				filter_click();
				switchContent(findBrandFragment,findFilterFragment);
				break;
			case R.id.fragment_find_iv_search:
				Intent intent=new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
				break;


			default:
				break;
		}
	}

	private void find_click() {
		fragment_find_llyt_switch.setBackgroundResource(R.drawable.fragment_find_ic_left);
		fragment_find_tv_brand.setTextColor(getResources().getColor(R.color.find_cl_choose));
		fragment_find_tv_filter.setTextColor(getResources().getColor(R.color.find_cl_unchoose));
		fragment_find_tv_brand.setClickable(false);
		fragment_find_tv_filter.setClickable(true);
	}

	private void filter_click() {
		fragment_find_llyt_switch.setBackgroundResource(R.drawable.fragment_find_ic_right);
		fragment_find_tv_filter.setTextColor(getResources().getColor(R.color.find_cl_choose));
		fragment_find_tv_brand.setTextColor(getResources().getColor(R.color.find_cl_unchoose));
		fragment_find_tv_filter.setClickable(false);
		fragment_find_tv_brand.setClickable(true);
	}


	public void switchContent(Fragment from, Fragment to) {
		if (mContent != to) {
			mContent = to;
//			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
			if (!to.isAdded()) {    // 先判断是否被add过
				ft.hide(from).add(R.id.fragment_find_flyt_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

}
