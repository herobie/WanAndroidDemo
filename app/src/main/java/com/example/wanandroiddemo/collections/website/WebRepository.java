package com.example.wanandroiddemo.collections.website;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.wanandroiddemo.cache.Cache;
import com.example.wanandroiddemo.cache.collections.WebDao;
import com.example.wanandroiddemo.collections.bean.CollectedWebs;
import com.example.wanandroiddemo.collections.bean.ResponseBean;
import com.example.wanandroiddemo.cookieStore.CookieJarImpl;
import com.example.wanandroiddemo.cookieStore.PersistentCookieStore;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebRepository {
    private final String collectedWebUrl = "https://www.wanandroid.com/lg/collect/usertools/json";
    private final String removeWebUrl = "https://www.wanandroid.com/lg/collect/deletetool/json?id=";
    private final String addWebUrl = "https://www.wanandroid.com/lg/collect/addtool/json";
    private Application application;
    private CollectedWebs web;
    private WebsiteViewModel viewModel;
    private WebDao webDao;

    public WebRepository(Application application , WebsiteViewModel viewModel) {
        this.application = application;
        this.viewModel =viewModel;
        Cache cache = Cache.getInstance(application.getApplicationContext());
        webDao = cache.getWebDao();
    }

    //发送请求
    private void sendRequest(String url , Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJarImpl(new PersistentCookieStore(application)))
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    private void sendPostRequest(String url , RequestBody requestBody , Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJarImpl(new PersistentCookieStore(application)))
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    /**
     * 获取收藏网站列表
     */
    public void requestCollectedWebList(){
        viewModel.getIsWebAcquired().postValue(false);
        sendRequest(collectedWebUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                queryAll();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                clearWeb();//先清除缓存
                parseCollectedData(response.body().string());
                viewModel.getIsWebAcquired().postValue(true);
                insertWeb(getWeb().getData().toArray(new CollectedWebs.WebDatas[0]));//再重新刷新缓存
            }
        });
    }

    /**
     * 移除收藏的网站
     * @param id 网站id
     */
    public void requestRemoveWeb(int id){
        deleteWeb(id);
        String url = removeWebUrl + String.valueOf(id);
        RequestBody requestBody = new FormBody.Builder()
                .build();
        sendPostRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseResponse(response.body().string());
            }
        });
    }

    /**
     * 添加网址
     * @param name
     * @param link
     */
    public void requestNewWeb(String name , String link){
        RequestBody requestBody = new FormBody.Builder()
                .add("name" , name)
                .add("link" , link)
                .build();
        sendPostRequest(addWebUrl, requestBody, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson = new Gson();
                CollectedWebs.WebDatas data = gson.fromJson(response.body().string() , CollectedWebs.WebDatas.class);
                web.getData().add(data);
            }
        });
    }

    private void parseResponse(String responseData){
        Gson gson = new Gson();
        ResponseBean responseBean = gson.fromJson(responseData , ResponseBean.class);
    }

    private void parseCollectedData(String responseData){
        Gson gson = new Gson();
        web = gson.fromJson(responseData , CollectedWebs.class);
    }

    private void insertWeb(CollectedWebs.WebDatas... webDatas){
        new InsertWeb().execute(webDatas);
    }

    private void clearWeb(){
        new ClearWeb().execute();
    }

    private void deleteWeb(int id){
        new DeleteWeb(id).execute();
    }

    private void queryAll(){
        new QueryAll().execute();//加载缓存
    }

    public CollectedWebs getWeb() {
        return web;
    }

    protected class InsertWeb extends AsyncTask<CollectedWebs.WebDatas , Void , Void>{

        @Override
        protected Void doInBackground(CollectedWebs.WebDatas... webDatas) {
            webDao.insertWeb(webDatas);
            return null;
        }
    }

    protected class ClearWeb extends AsyncTask<Void , Void , Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            webDao.clearWeb();
            return null;
        }
    }

    protected class DeleteWeb extends AsyncTask<Void , Void , Void>{
        private int id;

        public DeleteWeb(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            webDao.deleteWeb(id);
            return null;
        }
    }

    protected class QueryAll extends AsyncTask<Void , Void , List<CollectedWebs.WebDatas>>{

        @Override
        protected List<CollectedWebs.WebDatas> doInBackground(Void... voids) {
            return webDao.queryAll();
        }

        @Override
        protected void onPostExecute(List<CollectedWebs.WebDatas> webDatas) {
            super.onPostExecute(webDatas);
            web = new CollectedWebs();
            web.setData(webDatas);
            viewModel.getIsWebAcquired().postValue(true);
        }
    }
}
