package com.chenhong.android.carsdoor.fragment.newsfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.adapter.CarVideoAdapter;
import com.chenhong.android.carsdoor.entity.NewsVideo;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.view.CustomPtrHeader;
import com.chenhong.android.carsdoor.view.InGridView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 资讯-试车
 * 
 * @author blue
 */
public class NewsTryFragment extends BaseFragment
{
	@ViewInject(R.id.load_more_list_view_ptr_frame)
	private PtrFrameLayout mPtrFrameLayout;


	@ViewInject(R.id.lv_content)
	private ListView lv_content;
	private  boolean isUpdate=false;
	private CustomPtrHeader header;

	private CarVideoGridViewAdapter adapter;

	private String[] titles=new String[]{"原创节目","新车视频","媒体试驾"};


	private List<NewsVideo> onevideos = new ArrayList<>();
	private List<NewsVideo> threevideos=new ArrayList<>();
	private List<NewsVideo> twovideos = new ArrayList<>();
	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_try_main;
	}

	@Override
	protected void initParams()
	{
		initPtrHeader();


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



	private void initPtrHeader() {
		header = new CustomPtrHeader(getActivity());
		mPtrFrameLayout.setHeaderView(header);
		mPtrFrameLayout.addPtrUIHandler(header);
		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_content, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				isUpdate=true;
				queryData();
			}
		});
	}

	private void queryData() {

		BmobQuery<NewsVideo> query = new BmobQuery<>();
		query.order("vid");
		query.addWhereGreaterThanOrEqualTo("vid", 0);
		query.findObjects(new FindListener<NewsVideo>() {
			@Override
			public void done(List<NewsVideo> list, BmobException e) {
				if(e==null){

			for(int i=0;i<list.size();i++){
				if(list.get(i).getType()==0){
					onevideos.add(list.get(i));
				}else if(list.get(i).getType()==1){
					twovideos.add(list.get(i));
				}else {
					threevideos.add(list.get(i));
				}
			}
					if(adapter==null){
						adapter=new CarVideoGridViewAdapter();
						lv_content.setAdapter(adapter);
					}else {
						adapter.notifyDataSetChanged();
					}
					mPtrFrameLayout.refreshComplete();
					isUpdate=false;
				}else {
					if(isUpdate){
						mPtrFrameLayout.refreshComplete();
						isUpdate=false;
					}
					Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
				}
			}
		});

	}







	class CarVideoGridViewAdapter extends BaseAdapter{


		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=View.inflate(getActivity(), R.layout.listview_gridview,null);
			}
			final ViewHolder viewHolder=ViewHolder.getHolder(convertView);
			viewHolder.tv_title.setText(titles[position]);
//			realvideos.clear();
//			for(int i=0;i<videos.size();i++){
//				if(videos.get(i).getType()==position){
//					realvideos.add(videos.get(i));
//				}
//			}
			if(position==0){
				viewHolder.gv.setAdapter(new CarVideoAdapter(getActivity(),onevideos));
			}else if(position==1){
				viewHolder.gv.setAdapter(new CarVideoAdapter(getActivity(),twovideos));
			}else {
				viewHolder.gv.setAdapter(new CarVideoAdapter(getActivity(),threevideos));
			}

//			viewHolder.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//					Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
//					Log.e("tazzz",realvideos.get(position).getTitle());
//					intent.putExtra("url",realvideos.get(position).getWebsite());
//					intent.putExtra("trytitle",realvideos.get(position).getTitle());
//					startActivity(intent);
//				}
//			});
			return convertView;
		}

	}




	static class ViewHolder{
		TextView tv_title;
		InGridView  gv;
		public ViewHolder(View convertview){
			tv_title= (TextView) convertview.findViewById(R.id.tv_lv_title);
			gv= (InGridView) convertview.findViewById(R.id.gv);
		}
		public   static ViewHolder getHolder(View convertview){
			ViewHolder viewHolder= (ViewHolder) convertview.getTag();
			if(viewHolder==null){
				viewHolder=new ViewHolder(convertview);
				convertview.setTag(viewHolder);
			}
			return  viewHolder;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (getUserVisibleHint() && lv_content.getVisibility() != View.VISIBLE) {

		}
		super.onActivityCreated(savedInstanceState);
	}

}
