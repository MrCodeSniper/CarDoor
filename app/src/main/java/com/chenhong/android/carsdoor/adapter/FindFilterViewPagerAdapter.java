package com.chenhong.android.carsdoor.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 筛选条件ViewPager适配器
 * 
 * @author blue
 *
 */
public class FindFilterViewPagerAdapter extends PagerAdapter
{
	public List<View> datas;
	public String[] title;

	public FindFilterViewPagerAdapter(List<View> datas,String[] title)
	{
		this.title=title;
		this.datas = datas;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		((ViewPager) container).addView(datas.get(position), 0);
		return datas.get(position);
	}

	@Override
	public int getCount()
	{
		return datas.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == (arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		((ViewPager) container).removeView(datas.get(position));
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return title[position];
	}
}
