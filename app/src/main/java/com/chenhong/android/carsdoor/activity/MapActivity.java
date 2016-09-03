package com.chenhong.android.carsdoor.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.utils.ActivityStack;
import com.chenhong.android.carsdoor.view.map.OverlayManager;
import com.chenhong.android.carsdoor.view.map.PoiOverlay;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by Administrator on 2016/8/27.
 */
public class MapActivity extends BaseActivity implements OnGetPoiSearchResultListener{

     @ViewInject(R.id.bmapView)
    MapView mMapView ;

    @ViewInject(R.id.iv_location)
    ImageView iv;


    private SpotsDialog spotsDialog;

    private BaiduMap mBaiduMap;


    private double mCurrentLantitude;
    private double mCurrentLongitude;

    BitmapDescriptor mCurrentMarker;
    MapStatusUpdate u;
     boolean isFirstLoc=true;
    /**Poi搜索对象*/
    private PoiSearch mPoiSearch = null;
    // 定位相关声明
    public LocationClient locationClient = null;
    private LatLng ll;

    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
           mBaiduMap.setMyLocationData(locData);    //设置定位数据


            if (isFirstLoc) {
                isFirstLoc = false;


                ll = new LatLng(location.getLatitude(),
                        location.getLongitude());



                u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);   //设置地图中心点以及缩放级别
//              MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//                mBaiduMap.animateMapStatus(u);



                mPoiSearch.searchNearby(new PoiNearbySearchOption()
                        .keyword("4s店").pageCapacity(20).radius(10000).location(ll));



                spotsDialog.dismiss();
            }
        }
    };
    @Override
    protected void initView() {


        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaiduMap.animateMapStatus(u);
            }
        });


        spotsDialog = new SpotsDialog(this);
        spotsDialog.show();





        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(myListener); // 注册监听函数
        this.setLocationOption();   //设置定位参数
        locationClient.start(); // 开始定位





    }

    @Override
    public int getLayoutID() {
        return R.layout.map;
    }


    @Override
    protected void onDestroy() {
        //退出时销毁定位
        locationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView=null;
        mPoiSearch.destroy();
        ActivityStack.getInstace().removeActivity(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        option.setIsNeedLocationPoiList(true);


        locationClient.setLocOption(option);
    }





    private int radius=5000;
    /**
     * 搜索周边地理位置
     * by hankkin at:2015-11-01 22:54:49
     */
    private void searchNeayBy() {
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword("4S店");
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.location(ll);
        if (radius != 0) {
            option.radius(radius);
        } else {
            option.radius(1000);
        }

        option.pageCapacity(10);
        mPoiSearch.searchNearby(option);

    }
   private PoiResult poiResult;

    @Override
    public void onGetPoiResult(PoiResult result) {
        poiResult=result;
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Log.e("taqqq","没数据或错误");
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            Log.e("taqqq","没错误");
            mBaiduMap.clear();
            //创建PoiOverlay
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);

            //设置overlay可以处理标注点击事件
            mBaiduMap.setOnMarkerClickListener(overlay);
            //设置PoiOverlay数据
            overlay.setData(result);
            //添加PoiOverlay到地图中
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }

    }

    private class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            Log.e("tazzz",poiResult.getAllPoi().get(index).name);
            return true;
        }


    }


    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

        Log.e("tazzz",result.getUid()+"detail");
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
//            Toast.makeText(MapActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
//                    .show();
            Log.e("tazzz",result.getName());
        }

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }




}
