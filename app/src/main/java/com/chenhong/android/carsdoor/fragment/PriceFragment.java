package com.chenhong.android.carsdoor.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.MapActivity;
import com.chenhong.android.carsdoor.adapter.MyRecyclerAdapter;
import com.chenhong.android.carsdoor.adapter.QuickAdapter;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 降价
 * 
 * @author blue
 */
public class PriceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
{

	@ViewInject(R.id.app_recyclerview)
	private RecyclerView recyclerView;
	private List<String> mDatas;
	@ViewInject(R.id.srlayout)
	private SwipeRefreshLayout swipeRefreshLayout;



    private QuickAdapter mQuickAdapter;
	@Override
	protected int getLayoutId()
	{
		return R.layout.framgent;
	}

	@Override
	protected void initParams()
	{

		swipeRefreshLayout.post(new Runnable(){
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
			}
		});




		swipeRefreshLayout.setOnRefreshListener(this);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




	}

	private void initAdapter() {
		if(mQuickAdapter!=null){
			mQuickAdapter.notifyDataSetChanged();
		}else {
			mQuickAdapter = new QuickAdapter(mDatas);
			recyclerView.setAdapter(mQuickAdapter);
		}
		mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int i) {
				Intent intent=new Intent(getActivity(), MapActivity.class);
				startActivity(intent);
			}
		});
		swipeRefreshLayout.setRefreshing(false);
		mQuickAdapter.openLoadAnimation();
	}

	@Override
	protected void initLoading() {
		swipeRefreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				onRefresh();
			}
		}, 200);
	}

	private void initData() {
		mDatas = new ArrayList<String>();
		for ( int i=0; i < 1; i++) {
			mDatas.add("快来看看附近减价的商家吧！");
		}
	}

	@Override
	public void onRefresh() {
		initData();
		initAdapter();
	}




}
