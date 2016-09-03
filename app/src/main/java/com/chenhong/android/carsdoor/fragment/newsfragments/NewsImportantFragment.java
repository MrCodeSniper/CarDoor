package com.chenhong.android.carsdoor.fragment.newsfragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.MainActivity;
import com.chenhong.android.carsdoor.activity.PictureActivity;
import com.chenhong.android.carsdoor.adapter.NewsImportantAdapter;
import com.chenhong.android.carsdoor.entity.NewsTitle;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.exception.DataNotFoundException;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.fragment.NewsFragment;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.interf.OnScrollYListener;

import com.chenhong.android.carsdoor.utils.CacheUtils;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.utils.NetWorkUtils;
import com.chenhong.android.carsdoor.view.CustomPtrHeader;
import com.chenhong.android.carsdoor.view.MyScrollPager;
import com.chenhong.android.carsdoor.view.cycleview.ImageCycleView;
import com.lidroid.xutils.db.annotation.Check;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

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
 * 
 */
public class NewsImportantFragment extends BaseFragment{

	private NewsImportantAdapter adapter;
	@ViewInject(R.id.news_important_lv)
	private ListView lv_news_imp;
	@ViewInject(R.id.load_more_list_view_ptr_frame)
	private PtrFrameLayout mPtrFrameLayout;
	@ViewInject(R.id.load_more_list_view_container)
	private LoadMoreListViewContainer loadMoreListViewContainer;
	@ViewInject(R.id.layout_no)
	private LinearLayout layout_nodata;


	private CustomPtrHeader header;
	private int count=10;
	private int curPage = 0;		// 当前页的编号，从0开始
	public static final int STATE_REFRESH = 0;// 下拉刷新
	public static final int STATE_MORE = 1;// 加载更多
	private FrameLayout head_view_layout;
	private List<news_important> newslist = new ArrayList<news_important>();
	private MyScrollPager myScrollPager;
	private ImageCycleView imageCycleView;
	private ArrayList<NewsTitle> newsTitleslist=new ArrayList<NewsTitle>();
	private String[] bmobFileurl=new String[5];
	private String[] bmobFileString=new String[5];
	private  boolean isUpdate=false;
	private  static boolean isAddHeader;
	private int height;
	private boolean ischange=false;
	private boolean isadd=false;

	private List<news_important> list;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_important_main;
	}

	@Override
	protected void initParams() {













		initPtrHeader();



		loadMoreListViewContainer.useDefaultHeader();
		loadMoreListViewContainer.setAutoLoadMore(true);
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				isUpdate=true;
				queryData(curPage, STATE_MORE);
			}
		});


		loadMoreListViewContainer.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				NewsFragment  parentFragment = (NewsFragment) getParentFragment();
				if(firstVisibleItem!=0){
					if(parentFragment.isEnd){
						parentFragment.showSearch();
						ischange=true;
					}
				}else if(ischange){
					if(parentFragment.isEnd){
						parentFragment.hideSearch();
						ischange=false;
					}
				}
			}
		});


	}


	private void initPtrHeader() {
		header = new CustomPtrHeader(getActivity());
		mPtrFrameLayout.setHeaderView(header);
		mPtrFrameLayout.addPtrUIHandler(header);
		mPtrFrameLayout.setLoadingMinTime(500);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_news_imp, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {

				if(!isadd){
					imageCycleView = new ImageCycleView(getActivity());
					AbsListView.LayoutParams layoutParams=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					imageCycleView.setLayoutParams(layoutParams);
					queryHead();
					ViewTreeObserver vto = imageCycleView.getViewTreeObserver();
					vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							imageCycleView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
							height=imageCycleView.getHeight();
						}
					});
				}
				isUpdate=true;
				queryData(0, STATE_REFRESH);
			}
		});
	}






	@Override
	protected void initLoading() {
		Log.e("tazzz","net"+NetWorkUtils.isNetworkAvailable(getActivity()));
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mPtrFrameLayout.postDelayed(new Runnable() {
				@Override
				public void run() {
					mPtrFrameLayout.autoRefresh();
				}
			}, 200);
			mPtrFrameLayout.setInterceptEventWhileWorking(true);
		}
	}

	private void queryData(int page, final int actionType){

		BmobQuery<news_important> query = new BmobQuery<>();
		// 按nid升序查询
		query.order("-nid");
		// 如果是加载更多
		if(actionType == STATE_MORE){
			// 只查询大于等于nid0的数据
			query.addWhereLessThanOrEqualTo("nid", 158);
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
		CacheUtils.setCachePolicy(query,news_important.class);

		query.findObjects(new FindListener<news_important>() {
			@Override
			public void done(List<news_important> list, BmobException e) {

				if(e==null){
					if(list.size()>0){
						if(actionType == STATE_REFRESH){
							// 当是下拉刷新操作时，将当前页的编号重置为0，并把集合清空，重新添加
							curPage = 0;
							newslist.clear();
							lv_news_imp.setVisibility(View.VISIBLE);
							layout_nodata.setVisibility(View.GONE);
						}
						newslist.addAll(list);
						if(adapter!=null){
							if (newslist != null) {
								loadMoreListViewContainer.loadMoreFinish(false, true);
							} else {
								loadMoreListViewContainer.loadMoreFinish(true, false);
							}
							adapter.refreshDatas(newslist);
							adapter.notifyDataSetChanged();
						}else {
							adapter = new NewsImportantAdapter(getActivity().getApplicationContext(), newslist);
							lv_news_imp.setAdapter(adapter);
						}
						// 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
						curPage++;
						mPtrFrameLayout.refreshComplete();
						isUpdate=false;
						MyUtils.showToast(getActivity(),("第" + (finalPage + 1) + "页数据加载完成"));
					}else if(actionType == STATE_MORE){
						if(isUpdate){
							loadMoreListViewContainer.loadMoreFinish(true,false);
							isUpdate=false;
						}
					}else if(actionType == STATE_REFRESH){
						if(isUpdate){
							mPtrFrameLayout.refreshComplete();
							isUpdate=false;
						}
						MyUtils.showToast(getActivity(), "没有数据");
					}
				}else {
					if(actionType == STATE_REFRESH){
						mPtrFrameLayout.refreshComplete();
						lv_news_imp.setVisibility(View.GONE);
						layout_nodata.setVisibility(View.VISIBLE);
					}else if(actionType == STATE_MORE) {
						if (isUpdate) {
							loadMoreListViewContainer.loadMoreFinish(true,false);
							isUpdate = false;
							MyUtils.showToast(getActivity(), "没有更多数据了");
						}
					}



				}
			}
		});




	}


	private String[] objects=new String[5];
	private void queryHead(){
		BmobQuery<NewsTitle> query = new BmobQuery<>();
		query.order("id");
		query.addWhereGreaterThanOrEqualTo("id", 0);
		CacheUtils.setCachePolicy(query,NewsTitle.class);
		query.findObjects(new FindListener<NewsTitle>() {
			@Override
			public void done(List<NewsTitle> list, BmobException e) {
				Log.e("tazzz","执行了done");
				if(e==null){
					Log.e("tazzz","执行了e==null");
					isadd=true;

					if(list!=null){
						newsTitleslist.addAll(list);
						for(int i=0;i<5;i++){
							bmobFileurl[i]=list.get(i).getImage().getFileUrl();
							bmobFileString[i]=list.get(i).getUrl();
							objects[i]=list.get(i).getObjectId();
						}
						imageCycleView.setImageResources(newsTitleslist, new ImageCycleView.ImageCycleViewListener() {
							@Override
							public void displayImage(String imageURL, ImageView imageView) {
								ImageLoader.getInstance().displayImage(imageURL, imageView, Constant.options);
							}
							@Override
							public void onImageClick(NewsTitle info, int postion, View imageView) {
								Bundle bundle=new Bundle();
								bundle.putStringArray("objects",objects);//图片地址
								bundle.putStringArray("bmobFileurl",bmobFileurl);//图片地址
								bundle.putStringArray("bmobFileString",bmobFileString);//内容地址
								bundle.putInt("currentposition",postion);
								PictureActivity.startActivity(getActivity(),bundle);
							}
						});
						lv_news_imp.addHeaderView(imageCycleView);
					}else {
						Log.e("tazzz","list为空");
						imageCycleView=null;
						isadd=false;
					}
				}else {
					isadd=false;
					imageCycleView=null;
					Log.e("tazzz","执行了e!=null");
					Log.e("tazzz",e.getMessage());
				}
			}
		});

	}






}









