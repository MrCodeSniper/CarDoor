package com.chenhong.android.carsdoor.fragment.newsfragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.SimpleRecyclerCardAdapter;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.utils.CacheUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 资讯-图解
 * 
 * @author blue
 */
public class NewsPictureFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
{
	@ViewInject(R.id.app_recyclerview)
	private RecyclerView mRecyclerView;
	private List<String> mDatas = new ArrayList<String>();
	private List<news_important> newslist = new ArrayList<news_important>();
	private SimpleRecyclerCardAdapter mSimpleRecyclerAdapter;
	private Handler handler=new Handler(){

	};



	@ViewInject(R.id.srlayout)
	private SwipeRefreshLayout refreshLayout;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_picture_main;
	}

	@Override
	protected void initParams()
	{

		refreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshLayout.setRefreshing(true);
			}
		},500);

		onRefresh();

		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorSchemeResources(R.color.qq_color, R.color.weixin_color, R.color.qq_back_color);
//		loadpictrue();
		mRecyclerView.setHasFixedSize(true);
      final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
		//设置layoutmanager,设置为纵向一行两个item的列表
	//设置网格布局管理器
	mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				staggeredGridLayoutManager.invalidateSpanAssignments();//解决瀑布流重绘的
			}
		});

}

	private void loadpictrue() {
		final BmobQuery<news_important> query = new BmobQuery<>();
		// 按nid升序查询
		query.order("-nid");
		query.addWhereLessThanOrEqualTo("nid", 157);
		query.setLimit(30);
		CacheUtils.setCachePolicy(query,news_important.class);
		query.findObjects(new FindListener<news_important>() {
			@Override
			public void done(List<news_important> list, BmobException e) {
				if (e == null) {
					for (int i = 0; i < list.size(); i++) {
						String url = list.get(i).getImage_list().getFileUrl();
						mDatas.add(url);
					}
					if (mSimpleRecyclerAdapter == null) {
						mSimpleRecyclerAdapter = new SimpleRecyclerCardAdapter(getActivity(), mDatas);
						mRecyclerView.setAdapter(mSimpleRecyclerAdapter);
					}else{
						mSimpleRecyclerAdapter.notifyDataSetChanged();
					}
					refreshLayout.setRefreshing(false);
					mRecyclerView.setVisibility(View.VISIBLE);
					handler.post(new Runnable() {
						@Override
						public void run() {
							refreshLayout.setRefreshing(false);
						}
					});
					mSimpleRecyclerAdapter.setOnItemActionListener(new SimpleRecyclerCardAdapter.OnItemActionListener() {
																	   @Override
																	   public boolean onItemLongClickListener(View v, int pos) {
																		   Toast.makeText(getActivity(), "-长按-" + pos, Toast.LENGTH_SHORT).show();
																		   return false;
																	   }

																	   @Override
																	   public void onItemClickListener(View v, int pos) {
																		   Toast.makeText(getActivity(), "-单击-" + pos, Toast.LENGTH_SHORT).show();
																	   }
																   }
					);
				} else {
					mRecyclerView.setVisibility(View.GONE);

					refreshLayout.setRefreshing(false);
					Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
				}
			}
		});
	}

	@Override
	protected void initLoading() {
       onRefresh();
	}


	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				loadpictrue();
//				Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
//				refreshLayout.setRefreshing(false);
			}
		}, 500);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint() && mRecyclerView.getVisibility() != View.VISIBLE) {

		}
		super.onActivityCreated(savedInstanceState);
	}
}
