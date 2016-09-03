package com.chenhong.android.carsdoor.fragment.newsfragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.NewsImportantAdapter;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.exception.DataNotFoundException;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.view.MyScrollPager;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 
 */
public class NewsImpFragment extends BaseFragment{

	private NewsImportantAdapter adapter;
	@ViewInject(R.id.news_important_lv)
//	private PullToRefreshListView lv_news_imp;
    private int currentPage;
	private int curPage = 0;		// 当前页的编号，从0开始
	private MyScrollPager myScrollPager ;
	private List<news_important> newslist = new ArrayList<news_important>();
	private boolean isRefresh=false;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_important_main;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //布局参数对象
		AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		myScrollPager=new MyScrollPager(getActivity());
		myScrollPager.setLayoutParams(params);
		currentPage=0;
	}

	@Override
	protected void initParams() {

       if(!isRefresh){
		   //首次来页面自动加载数据 来到界面延时加载
		   new Handler(new Handler.Callback() {
			   @Override
			   public boolean handleMessage(Message message) {
//				   lv_news_imp.setRefreshing();//设置刷新操作
				   return true;
			   }
		   }).sendEmptyMessageDelayed(0,100);
		   isRefresh=true;
	   }


//
//		ListView lv=lv_news_imp.getRefreshableView();
//	   if(myScrollPager!=null){
//		   lv.addHeaderView(myScrollPager);
//		   myScrollPager=null;
//	   }
//
//
//
//		lv_news_imp.setMode(PullToRefreshBase.Mode.BOTH);
//        //2.刷新时不允许滚动
//		lv_news_imp.setScrollingWhileRefreshingEnabled(false);
//		lv_news_imp.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//			@Override
//			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//              // 下拉刷新(从第一页开始装载数据)
//				try {
//					List<news_important> news_importants= newsBiz.queryFirstPageData();
//					    currentPage=0;
//					    newslist.addAll(news_importants);
//					if(adapter!=null){
//						adapter.refreshDatas(newslist);
//						adapter.notifyDataSetChanged();
//					}else {
//						adapter = new NewsImportantAdapter(getActivity(), newslist);
//						lv_news_imp.setAdapter(adapter);
//					}
//					    lv_news_imp.onRefreshComplete();
//					showToast("第"+(currentPage+1)+"页数据加载完成");
//				} catch (DataNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//				// 上拉加载更多(加载下一页数据)
//				try {
//					List<news_important> news_importants = newsBiz.queryNextPageData(currentPage);
//					currentPage++;
//					newslist.addAll(news_importants);
//					if(adapter!=null){
//						adapter.refreshDatas(newslist);
//						adapter.notifyDataSetChanged();
//					}else {
//						adapter = new NewsImportantAdapter(getActivity(), newslist);
//						lv_news_imp.setAdapter(adapter);
//					}
//					lv_news_imp.onRefreshComplete();
//					showToast("第"+(currentPage+1)+"页数据加载完成");
//				} catch (DataNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//		});

	}

	@Override
	protected void initLoading() {

	}


	private void showToast(String msg){
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}








	  }
















