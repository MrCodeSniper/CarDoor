package com.chenhong.android.carsdoor.fragment.newsfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.activity.NewsDetailActivity;
import com.chenhong.android.carsdoor.activity.PictureActivity;
import com.chenhong.android.carsdoor.adapter.NewCarAdapter;
import com.chenhong.android.carsdoor.adapter.NewsImportantAdapter;
import com.chenhong.android.carsdoor.entity.NewCar;
import com.chenhong.android.carsdoor.entity.NewsTitle;
import com.chenhong.android.carsdoor.entity.news_important;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.fragment.NewsFragment;
import com.chenhong.android.carsdoor.global.Constant;
import com.chenhong.android.carsdoor.interf.OnScrollYListener;
import com.chenhong.android.carsdoor.utils.CacheUtils;
import com.chenhong.android.carsdoor.utils.DisplayUtil;
import com.chenhong.android.carsdoor.utils.MyUtils;
import com.chenhong.android.carsdoor.view.CustomPtrHeader;
import com.chenhong.android.carsdoor.view.cycleview.ImageCycleView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
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
 * 资讯-新车
 * 
 * @author blue
 */
public class NewsCarFragment extends BaseFragment implements AdapterView.OnItemClickListener,ViewPager.OnPageChangeListener, OnItemClickListener
{
	@ViewInject(R.id.lv_newcar)
	private ListView lv_newcar;
    private List<NewCar> newCarList=new ArrayList<>();
	private NewCarAdapter adapter;
	private ArrayList<Integer> localImages = new ArrayList<Integer>();
	private ArrayList<NewsTitle> newsTitleslist=new ArrayList<NewsTitle>();
	@ViewInject(R.id.load_more_list_view_ptr_frame)
	private PtrFrameLayout mPtrFrameLayout;
	@ViewInject(R.id.load_more_list_view_container)
	private LoadMoreListViewContainer loadMoreListViewContainer;
	@ViewInject(R.id.layout_no)
	private LinearLayout layout_nodata;
	private CustomPtrHeader header;
	private List<String> networkImages=new ArrayList<>();
	private  boolean isUpdate=false;
	private int count=10;
	private boolean isadd=false;
	private int curPage = 0;		// 当前页的编号，从0开始
	public static final int STATE_REFRESH = 0;// 下拉刷新
	public static final int STATE_MORE = 1;// 加载更多
	private ConvenientBanner mConvenientBanner;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_car_main;
	}

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
     lv_newcar.setOnItemClickListener(this);


	}


	private void initPtrHeader() {
		header = new CustomPtrHeader(getActivity());
		mPtrFrameLayout.setHeaderView(header);
		mPtrFrameLayout.addPtrUIHandler(header);
		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_newcar, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if(!isadd) {
				    queryHead();
				}
				isUpdate=true;
				queryData(0, STATE_REFRESH);
			}
		});
	}



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
							networkImages.add(list.get(i).getImage().getFileUrl());
						}

						//手动New并且添加到ListView Header的例子
						mConvenientBanner = new ConvenientBanner(getActivity());
                        mConvenientBanner.setMinimumHeight(DisplayUtil.dip2px(getActivity(),200));

//						AbsListView.LayoutParams layoutParams=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//						mConvenientBanner.setLayoutParams(layoutParams);
        mConvenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
					@Override
					public NetworkImageHolderView createHolder() {
						return new NetworkImageHolderView();
					}
				}, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.saturn__tag_indicator_normal, R.drawable.saturn__tag_indicator_select})
                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(NewsCarFragment.this);
						lv_newcar.addHeaderView(mConvenientBanner);
					}else {
						Log.e("tazzz","list为空");
					}
				}else {
					Log.e("tazzz","执行了e!=null");
					Log.e("tazzz",e.getMessage());
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(position>=1){
			Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
			intent.putExtra("url",newCarList.get(position-1).getWebViewUrl());
			intent.putExtra("title",newCarList.get(position-1).getNewCarTitle());
			startActivity(intent);
		}
	}


	class NetworkImageHolderView implements Holder<String> {
		private ImageView imageView;

		@Override
		public View createView(Context context) {
			//你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
			imageView = new ImageView(context);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			return imageView;
		}

		@Override
		public void UpdateUI(Context context, int position, String data) {
			imageView.setImageResource(R.drawable.empty_photo);
			ImageLoader.getInstance().displayImage(data, imageView);
		}
	}


	private void queryData(int page, final int actionType) {

		BmobQuery<NewCar> query = new BmobQuery<>();
		// 如果是加载更多
		if (actionType == STATE_MORE) {
			// 只查询大于等于nid0的数据
			query.addWhereGreaterThanOrEqualTo("ncid", 0);
			// 跳过之前页数并去掉重复数据
			query.setSkip(page * count + 1);
		} else {
			page = 0;
			//跳过第多少条数据，分页时用到，获取下一页数据
			query.setSkip(page);
		}
		// 设置每页数据个数
		query.setLimit(count);

		// 查找数据
		final int finalPage = page;

		//缓存
		CacheUtils.setCachePolicy(query, NewCar.class);

		query.findObjects(new FindListener<NewCar>() {
			@Override
			public void done(List<NewCar> list, BmobException e) {

				if (e == null) {
					if (list.size() > 0) {
						if (actionType == STATE_REFRESH) {
							// 当是下拉刷新操作时，将当前页的编号重置为0，并把集合清空，重新添加
							curPage = 0;
							newCarList.clear();
							lv_newcar.setVisibility(View.VISIBLE);
							layout_nodata.setVisibility(View.GONE);
						}
						newCarList.addAll(list);
						if (adapter != null) {
							if (newCarList != null) {
								loadMoreListViewContainer.loadMoreFinish(false, true);
							} else {
								loadMoreListViewContainer.loadMoreFinish(true, false);
							}
							adapter.refreshDatas(newCarList);
							adapter.notifyDataSetChanged();
						} else {
							adapter = new NewCarAdapter(getActivity().getApplicationContext(), newCarList);
							lv_newcar.setAdapter(adapter);
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
				} else {
					if (actionType == STATE_REFRESH) {
						mPtrFrameLayout.refreshComplete();
						lv_newcar.setVisibility(View.GONE);
						layout_nodata.setVisibility(View.VISIBLE);
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




	// 开始自动翻页
	@Override
	public void onResume() {
		super.onResume();
		//开始自动翻页
		if(mConvenientBanner!=null){
			mConvenientBanner.startTurning(5000);
		}

	}

	// 停止自动翻页
	@Override
	public void onPause() {
		super.onPause();
		//停止翻页
		if(mConvenientBanner!=null){
			mConvenientBanner.stopTurning();
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		Toast.makeText(getActivity(),"监听到翻到第"+position+"了",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onItemClick(int position) {
		Toast.makeText(getActivity(),"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint() && lv_newcar.getVisibility() != View.VISIBLE) {

		}
		super.onActivityCreated(savedInstanceState);
	}
}
