package com.example.wanandroiddemo.home;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.home.adapter.HomeBannerAdapter;
import com.example.wanandroiddemo.home.adapter.HomeItemAdapter;
import com.example.wanandroiddemo.home.bean.BannerBean;
import com.example.wanandroiddemo.home.bean.HomeBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragmentViewModel extends ViewModel {
    private List<HomeBean> homeBeans;
    private MutableLiveData<Boolean> isLoadingFinished;
    private String homeUrl , bannerUrl;
    private boolean isItemReady , isBannerReady;
    private HomeBean homeBean;
    private BannerBean bannerBean;
    private HomeBannerAdapter homeBannerAdapter;
    private HomeItemAdapter homeItemAdapter;
    private final int PHRASE_ITEM_SUCCEED = 1;
    private final int PHRASE_BANNER_SUCCEED = 2;
    private final int AWAITING = 3;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PHRASE_ITEM_SUCCEED:
                    isItemReady = true;
                    if (isBannerReady){
                        isLoadingFinished.setValue(true);
                    }else {
                        sendEmptyMessageDelayed(AWAITING , 500);
                    }
                    break;
                case PHRASE_BANNER_SUCCEED:
                    isBannerReady = true;
                    if (isItemReady){
                        isLoadingFinished.setValue(true);
                    }else {
                        sendEmptyMessageDelayed(AWAITING , 500);
                    }
                    break;
                case AWAITING:
                    if (isItemReady && isBannerReady){
                        isLoadingFinished.setValue(true);
                    }else {
                        sendEmptyMessageDelayed(AWAITING , 500);
                    }
                    break;
            }
        }
    };
    public HomeFragmentViewModel(){
        homeBeans = new ArrayList<>();
        isLoadingFinished = new MutableLiveData<>();
        isLoadingFinished.setValue(false);
        homeUrl = "https://www.wanandroid.com/article/list/0/json";
        bannerUrl = "https://www.wanandroid.com/banner/json";
    }

    /**
     * 发送请求
     * @param url 目标url
     * @param callback 回调
     */
    public void sendRequest(String url , Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
        isLoadingFinished.setValue(false);
    }

    /**
     * 解析item的数据
     * @param responseData
     */
    public void parseItemData(String responseData){
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String content = jsonObject.getString("data");
            Log.d("MainActivity" , content);
            Gson gson = new Gson();
            homeBean = gson.fromJson(content , HomeBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        isLoadingFinished.setValue(true);
    }

    /**
     * 解析banner的数据
     * @param responseData
     */
    public void parseBannerData(String responseData){
        Gson gson = new Gson();
        bannerBean = gson.fromJson(responseData , BannerBean.class);
    }

    public void refresh(Context context){
        isBannerReady = false;
        isItemReady = false;
        isLoadingFinished.setValue(false);
        sendRequest(homeUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseItemData(response.body().string());
                homeItemAdapter.setHomeBean(homeBean);
                Message message = new Message();
                message.what = PHRASE_ITEM_SUCCEED;
                handler.sendMessage(message);
            }
        });
        sendRequest(bannerUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseBannerData(response.body().string());
                if (homeBannerAdapter == null){
                    homeBannerAdapter = new HomeBannerAdapter(bannerBean.getData() , context);
                }else {
                    homeBannerAdapter.setDatas(bannerBean.getData());
                }
                Message message = new Message();
                message.what = PHRASE_BANNER_SUCCEED;
                handler.sendMessage(message);
            }
        });
    }

    public List<HomeBean> getHomeBeans() {
        return homeBeans;
    }

    public MutableLiveData<Boolean> getIsLoadingFinished() {
        return isLoadingFinished;
    }

    public HomeBean getHomeBean() {
        return homeBean;
    }

    public BannerBean getBannerBean() {
        return bannerBean;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setHomeBannerAdapter(HomeBannerAdapter homeBannerAdapter) {
        this.homeBannerAdapter = homeBannerAdapter;
    }

    public void setHomeItemAdapter(HomeItemAdapter homeItemAdapter) {
        this.homeItemAdapter = homeItemAdapter;
    }

    public HomeItemAdapter getHomeItemAdapter() {
        return homeItemAdapter;
    }

    public HomeBannerAdapter getHomeBannerAdapter() {
        return homeBannerAdapter;
    }
}
