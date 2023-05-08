package com.example.wanandroiddemo.home.cache;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroiddemo.home.bean.BannerBean;
import com.example.wanandroiddemo.home.bean.HomeBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeRepository {
    private Context context;
    private HomeDao homeDao;
    private BannerDao bannerDao;
    private HomeBean homeBean;
    private BannerBean bannerBean;
    private MutableLiveData<Boolean> isLoadingSuccess;
    private String homeUrl = "https://www.wanandroid.com/article/list/0/json";
    private String bannerUrl = "https://www.wanandroid.com/banner/json";
    private final int PHRASE_SUCCEED = 1;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PHRASE_SUCCEED:
                    isLoadingSuccess.setValue(true);
                    new InsertCacheData().start();//加入缓存
                    break;
            }
        }
    };
    public HomeRepository(Context context) {
        this.context = context;
        HomeCache homeCache = HomeCache.getInstance(context.getApplicationContext());
        homeDao = homeCache.getHomeDao();
        bannerDao = homeCache.getBannerDao();
        isLoadingSuccess = new MutableLiveData<>();
        isLoadingSuccess.setValue(false);
    }

    /**
     * 请求主页面的数据,先获取item数据，获取成功后再获取banner,最后将这些加入room缓存
     */
    public void refresh(){
        new ClearCacheData().start();//先清空所有缓存
        isLoadingSuccess.setValue(false);//将是否加载完毕设置为否
        Callback bannerCallback = new Callback() {//banner的
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseBannerData(response.body().string());
                Message message = new Message();
                message.what = PHRASE_SUCCEED;
                handler.sendMessage(message);
            }
        };
        sendRequest(homeUrl, new Callback() {//请求item的数据
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                sendRequest(bannerUrl , bannerCallback);//在主页面数据成功获得后请求banner数据
                parseItemData(response.body().string());
            }
        });
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
    }

    /**
     * 解析banner的数据
     * @param responseData
     */
    public void parseBannerData(String responseData){
        Gson gson = new Gson();
        bannerBean = gson.fromJson(responseData , BannerBean.class);
    }

    //发送请求
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
    }

    public void insertHome(HomeBean.HomeData ... homeData){
        new InsertHomeBean().execute(homeData);
    }

    public void insertBanner(BannerBean.Data...data){
        new InsertBannerBean().execute(data);
    }

    public void deleteHome(){
        new DeleteHomeBean().execute();
    }

    public MutableLiveData<Boolean> getIsLoadingSuccess() {
        return isLoadingSuccess;
    }

    public BannerBean getBannerBean() {
        return bannerBean;
    }

    public HomeBean getHomeBean() {
        return homeBean;
    }

    class InsertCacheData extends Thread{
        @Override
        public void run() {
            super.run();
            for (int i = 0 ; i < homeBean.getDatas().size() ; i++){
                insertHome(homeBean.getDatas().get(i));
            }
        }
    }

    class ClearCacheData extends Thread{
        @Override
        public void run() {
            super.run();
            deleteHome();
        }
    }

    class InsertHomeBean extends AsyncTask<HomeBean.HomeData , Void , Void>{

        @Override
        protected Void doInBackground(HomeBean.HomeData... homeData) {
            homeDao.insertHome(homeData);
            return null;
        }
    }

    class InsertBannerBean extends AsyncTask<BannerBean.Data , Void , Void>{

        @Override
        protected Void doInBackground(BannerBean.Data... data) {
            bannerDao.insertBanner(data);
            return null;
        }
    }

    class DeleteHomeBean extends AsyncTask<HomeBean , Void , Void>{

        @Override
        protected Void doInBackground(HomeBean... homeBeans) {
            homeDao.deleteHome();
            return null;
        }
    }


}
