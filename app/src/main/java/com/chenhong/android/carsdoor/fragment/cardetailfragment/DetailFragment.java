package com.chenhong.android.carsdoor.fragment.cardetailfragment;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chenhong.android.carsdoor.R;
import com.chenhong.android.carsdoor.entity.Result;
import com.chenhong.android.carsdoor.entity.Root;
import com.chenhong.android.carsdoor.fragment.BaseFragment;
import com.chenhong.android.carsdoor.global.Constant;
import com.google.gson.Gson;
import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.Text;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import cn.smssdk.gui.layout.Res;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android on 2016/8/12.
 */
public class DetailFragment extends BaseFragment {

   @ViewInject(R.id.iv_title)
   private ImageView iv_title;
    @ViewInject(R.id.tv_name)
   private TextView tv_name;
    @ViewInject(R.id.tv_typeandpailiang)
    private TextView tv_typeandpailiang;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initParams() {
        Log.e("tazzz",getActivity().getIntent().getStringExtra("car"));
       getRequest2(getActivity().getIntent().getStringExtra("car"));

    }

    @Override
    protected void initLoading() {

    }

    //2.车系信息查询
    public  void getRequest2(String car){
        String result =null;
        final String url ="http://op.juhe.cn/onebox/car/query";//请求接口地址
        final Map params = new HashMap();//请求参数
        params.put("car",car);//车系名称，如:途观
        params.put("key", Constant.APPKEY);//应用APPKEY(应用详细页查询)
         //RXJAVA异步
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Log.e("tazzz","call");
                subscriber.onNext(net(url, params, "GET"));
                subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e); // 任务异常
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                Log.e("tazzz","onnext");
                Gson gson=new Gson();
                Root object = gson.fromJson(s,Root.class);
                if(object.getError_code()==0){
                    ImageLoader.getInstance().displayImage(object.getResult().getImg(),iv_title);
                    tv_price.setText(object.getResult().getCarinfo().get(0).getValue());
                    tv_name.setText(object.getResult().getKey());
                    tv_typeandpailiang.setText(object.getResult().getCarinfo().get(5).getValue()+"|"+object.getResult().getCarinfo().get(4).getValue());
                }else{
                    System.out.println(object.getError_code()+":"+object.getReason());
                }
            }
        });



//            new AsyncTask<Void,Void,String>(){
//                @Override
//                protected String doInBackground(Void... param) {
//                    return net(url, params, "GET");
//                }
//                @Override
//                protected void onPostExecute(String s) {
//                    Gson gson=new Gson();
//                    Root object = gson.fromJson(s,Root.class);
//                    if(object.getError_code()==0){
//                        ImageLoader.getInstance().displayImage(object.getResult().getImg(),iv_title);
//                        tv_price.setText(object.getResult().getCarinfo().get(0).getValue());
//                        tv_name.setText(object.getResult().getKey());
//                        tv_typeandpailiang.setText(object.getResult().getCarinfo().get(5).getValue()+"|"+object.getResult().getCarinfo().get(4).getValue());
//                    }else{
//                        System.out.println(object.getError_code()+":"+object.getReason());
//                    }
//                }
//            }.execute();

    }





    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) {

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", Constant.userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(Constant.DEF_CONN_TIMEOUT);
            conn.setReadTimeout(Constant.DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, Constant.DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }


    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }



}
