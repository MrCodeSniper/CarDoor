package com.chenhong.android.carsdoor.fragment.newsfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.NewsBuyAdapter;
import com.chenhong.android.carsdoor.adapter.NewsImportantAdapter;
import com.chenhong.android.carsdoor.cache.BmobCache;
import com.chenhong.android.carsdoor.entity.NewCar;
import com.chenhong.android.carsdoor.entity.NewsBuy;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.utils.CacheUtils;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.CustomPtrHeader;
import com.chenhong.android.carsdoor.view.cycleview.ImageCycleView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 资讯-导购
 * 
 * @author blue
 */
public class NewsBuyFragment extends BaseFragment
{
	private NewsBuyAdapter adapter;
	@ViewInject(R.id.news_important_lv)
	private ListView lv_news_imp;
	@ViewInject(R.id.load_more_list_view_ptr_frame)
	private PtrFrameLayout mPtrFrameLayout;
	@ViewInject(R.id.load_more_list_view_container)
	private LoadMoreListViewContainer loadMoreListViewContainer;
	@ViewInject(R.id.layout_no)
	private LinearLayout layout_nodata;
	private CustomPtrHeader header;
	private List<NewsBuy> newslist = new ArrayList<NewsBuy>();
	private  boolean isUpdate=false;
	private boolean isCache;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_buy_main;
	}
	private int count=10;
	private int curPage = 0;		// 当前页的编号，从0开始
	public static final int STATE_REFRESH = 0;// 下拉刷新
	public static final int STATE_MORE = 1;// 加载更多
	@Override
	protected void initParams()
	{

		initPtrHeader();
		loadMoreListViewContainer.useDefaultHeader();
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				isUpdate=true;
				queryData(curPage, STATE_MORE);

			}
		});













	}


	private void initPtrHeader() {
		header = new CustomPtrHeader(getActivity());
		mPtrFrameLayout.setHeaderView(header);
		mPtrFrameLayout.addPtrUIHandler(header);
		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_news_imp, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				isUpdate=true;
				queryData(0, STATE_REFRESH);

			}

		});
	}




	private void queryData(int page, final int actionType){

		BmobQuery<NewsBuy> query = new BmobQuery<>();
		query.order("-bid");
		if(actionType == STATE_MORE){
			// 只查询大于等于nid0的数据
			query.addWhereGreaterThanOrEqualTo("bid", 0);
			// 跳过之前页数并去掉重复数据
			query.setSkip(page * count+1);
		}else{
			page=0;
			//跳过第多少条数据，分页时用到，获取下一页数据
			query.setSkip(page);
		}
		// 设置每页数据个数
		query.setLimit(count);
		// 查找数据
		final int finalPage = page;

		//缓存
		CacheUtils.setCachePolicy(query,NewsBuy.class);


		query.findObjects(new FindListener<NewsBuy>() {
			@Override
			public void done(List<NewsBuy> list, BmobException e) {
				if(e==null) {
					if (list.size() > 0) {
						if (actionType == STATE_REFRESH) {
							// 当是下拉刷新操作时，将当前页的编号重置为0，并把集合清空，重新添加
							curPage = 0;
							newslist.clear();
							lv_news_imp.setVisibility(View.VISIBLE);
							layout_nodata.setVisibility(View.GONE);
						}
						newslist.addAll(list);
						if (adapter != null) {
							if (newslist != null) {
								loadMoreListViewContainer.loadMoreFinish(false, true);
							} else {
								loadMoreListViewContainer.loadMoreFinish(true, false);
							}
							adapter.refreshDatas(newslist);
							adapter.notifyDataSetChanged();
						} else {
							adapter = new NewsBuyAdapter(getActivity().getApplicationContext(), newslist);
							lv_news_imp.setAdapter(adapter);
						}
						// 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
						curPage++;
						mPtrFrameLayout.refreshComplete();
						isUpdate = false;
						MyUtils.showToast(getActivity(), ("第" + (finalPage + 1) + "页数据加载完成"));
					} else if (actionType == STATE_MORE) {
						if (isUpdate) {
							loadMoreListViewContainer.loadMoreFinish(true, false);
							isUpdate = false;
						}
					} else if (actionType == STATE_REFRESH) {
						if (isUpdate) {
							mPtrFrameLayout.refreshComplete();
							isUpdate = false;
						}
						MyUtils.showToast(getActivity(), "没有数据");
					}
				}else {
					if (actionType == STATE_REFRESH) {
						isUpdate=false;
						mPtrFrameLayout.refreshComplete();


					} else if (actionType == STATE_MORE) {
						if (isUpdate) {
							loadMoreListViewContainer.loadMoreFinish(true, false);
							isUpdate = false;
							MyUtils.showToast(getActivity(), "没有更多数据了");
						}
					}
				}
			}
		});



	}


	@Override
	protected void initLoading() {
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh();
			}
		}, 200);
		mPtrFrameLayout.setInterceptEventWhileWorking(true);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint() && lv_news_imp.getVisibility() != View.VISIBLE) {

		}
		super.onActivityCreated(savedInstanceState);
	}













}
