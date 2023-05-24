package com.example.wanandroiddemo.collections.article;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.cache.Cache;
import com.example.wanandroiddemo.cache.collections.ArticleDao;
import com.example.wanandroiddemo.collections.bean.CollectedArticles;
import com.example.wanandroiddemo.collections.bean.ResponseBean;
import com.example.wanandroiddemo.cookieStore.CookieJarImpl;
import com.example.wanandroiddemo.cookieStore.PersistentCookieStore;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ArticleRepository {
    private final String requestArticleUrl = "https://www.wanandroid.com/lg/collect/list/0/json";
    private final String removeArticleUrl = "https://www.wanandroid.com/lg/uncollect/";//删除文章，post，把2805换成id , 后面接/json，然后是?origin= , 一般是-1
    private final String collectCasualArticleUrl = "https://www.wanandroid.com/lg/collect/add/json";//post 参数 title author link
    private CollectedArticles collectedArticles;
    private ArticleViewModel viewModel;
    private Context context;
    private ArticleDao articleDao;
    private CollectedArticles.Data.CollectedData collectedData;
    public ArticleRepository(ArticleViewModel viewModel , Context context) {
        this.viewModel = viewModel;
        this.context = context;
        Cache cache = Cache.getInstance(context.getApplicationContext());
        articleDao = cache.getArticleDao();
    }

    protected void sendRequest(String url , Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    /**
     * 获取收藏文章信息
     */
    public void requestCollectedArticle(){
        viewModel.getIsSuccess().setValue(false);
        sendRequest(requestArticleUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                queryAll();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseArticleData(response.body().string());
                viewModel.getIsSuccess().postValue(true);
            }
        });
    }

    /**
     * 取消收藏
     * @param position
     */
    public void requestRemoveArticle(int position){
        String url = removeArticleUrl
                + String.valueOf(collectedArticles.getData().getDatas().get(position).getId())
                + "/json?originId="
                + String.valueOf(collectedArticles.getData().getDatas().get(position).getOriginId());
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson = new Gson();//获得操作结果
                ResponseBean responseBean = gson.fromJson(response.body().string() , ResponseBean.class);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
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
     * 添加新文章
     * @param title 标题
     * @param author 作者
     * @param link 链接
     */
    public void requestNewArticle(String title , String author , String link){
        viewModel.getIsAdded().postValue(false);
        RequestBody requestBody = new FormBody.Builder()
                .add("title" , title)
                .add("author" , author)
                .add("link" , link)
                .build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseCollectData(response.body().string());
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                        .build();
                Request request = new Request.Builder()
                        .url(collectCasualArticleUrl)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    /**
     * 添加文章
     * @param responseData
     * @return 新的文章数据
     */
    protected void parseCollectData(String responseData){
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            int errorCode = jsonObject.getInt("errorCode");
            if (errorCode != -1){//检查错误代码
                String data = jsonObject.getString("data");
                Gson gson = new Gson();
                collectedData = gson.fromJson(data , CollectedArticles.Data.CollectedData.class);
                viewModel.getRepository()
                        .getCollectedArticles()
                        .getData()
                        .getDatas()
                        .add(collectedData);//将添加的文章加入集合
                viewModel.getIsAdded().postValue(true);
            }else {
                Intent intent = new Intent("ArticleDialog");//表明文章已经存在
                context.sendBroadcast(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void parseArticleData(String responseData){
        clearArticle();//先清除缓存
        //解析数据
        Gson gson = new Gson();
        collectedArticles = gson.fromJson(responseData , CollectedArticles.class);
        //再刷新缓存
        if(collectedArticles.getData() != null){
            for (int i = 0 ; i < collectedArticles.getData().getDatas().size(); i++){
                insertArticle(collectedArticles.getData().getDatas().get(i));
            }
        }
    }

    public CollectedArticles getCollectedArticles() {
        return collectedArticles;
    }

    protected void insertArticle(CollectedArticles.Data.CollectedData collectedData){
        new InsertArticle().execute(collectedData);
    }

    protected void clearArticle(){
        new ClearAllArticle().execute();
    }

    protected void queryAll(){
        new QueryAll().execute();
    }

    /**
     * 删除指定文章
     * @param id 文章id
     */
    public void deleteArticle(int id){
        new DeleteArticle(id).execute();
    }


    protected class InsertArticle extends AsyncTask<CollectedArticles.Data.CollectedData , Void ,Void>{

        @Override
        protected Void doInBackground(CollectedArticles.Data.CollectedData... collectedData) {
            articleDao.insertArticle(collectedData);
            return null;
        }
    }

    protected class ClearAllArticle extends AsyncTask<Void , Void ,Void>{

        @Override
        protected Void doInBackground(Void...voids) {
            articleDao.clearAllArticle();
            return null;
        }
    }

    protected class DeleteArticle extends AsyncTask<Void , Void ,Void>{
        private int id;

        public DeleteArticle(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articleDao.deleteArticle(id);
            return null;
        }
    }

    protected class QueryAll extends AsyncTask<Void , Void , List<CollectedArticles.Data.CollectedData>>{

        @Override
        protected List<CollectedArticles.Data.CollectedData> doInBackground(Void... voids) {
            return articleDao.queryAll();
        }

        @Override
        protected void onPostExecute(List<CollectedArticles.Data.CollectedData> data) {
            super.onPostExecute(data);
            collectedArticles = CollectedArticles.getInstance();
            collectedArticles.setNewData();
            collectedArticles.getData().setDatas(data);
            viewModel.getIsSuccess().postValue(true);
        }
    }
}
