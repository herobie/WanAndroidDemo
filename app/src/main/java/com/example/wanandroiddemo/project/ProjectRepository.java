package com.example.wanandroiddemo.project;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.cache.Cache;
import com.example.wanandroiddemo.cache.collections.ArticleDao;
import com.example.wanandroiddemo.cache.project.ChapterDao;
import com.example.wanandroiddemo.cache.project.IDDao;
import com.example.wanandroiddemo.collections.bean.ResponseBean;
import com.example.wanandroiddemo.cookieStore.CookieJarImpl;
import com.example.wanandroiddemo.cookieStore.PersistentCookieStore;
import com.example.wanandroiddemo.project.bean.ChapterBean;
import com.example.wanandroiddemo.project.bean.IDBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProjectRepository {
    private Context context;
    private ChapterDao chapterDao;
    private IDDao idDao;
    private Cache cache;
    private ArticleDao articleDao;
    private IDBean idBean;
    private ResponseBean responseBean;
    private ChapterBean chapterBean;
    private List<IDBean.DataBean> projectIdList;
    private final String tagsUrl = "https://www.wanandroid.com/project/tree/json";
    private final String projectDataUrl = "https://www.wanandroid.com/project/list/1/json";
    private final String uncollectUrl = "https://www.wanandroid.com/lg/uncollect_originId/";//id拼接上去
    private final String collectUrl = "https://www.wanandroid.com/lg/collect/";//id拼接上去
    private ProjectFragmentViewModel viewModel;
    public ProjectRepository(Context context , ProjectFragmentViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        Cache cache = Cache.getInstance(context.getApplicationContext());
        idDao = cache.getIDDao();
        chapterDao = cache.getChapterDao();
        articleDao = cache.getArticleDao();
        projectIdList = new ArrayList<>();
    }

    /**
     * 发送get请求
     * @param url 目标url
     * @param callback 回调
     */
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
     * 发送post请求
     * @param url
     * @param requestBody
     * @param callback
     */
    public void sendPostRequest(String url , RequestBody requestBody , Callback callback){
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
     * 请求tag数据
     */
    public void requestID(){
        viewModel.getIsIDAcquired().postValue(false);
        sendRequest(tagsUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseId(response.body().string());
                requestItemData(viewModel.getLastPosition().getValue());//获取tag后自动请求当前tag的页面数据
                viewModel.getIsIDAcquired().postValue(true);//通知id加载完毕刷新UI
            }
        });
    }

    /**
     * 发送取消收藏请求
     */
    public void requestUncollect(int id){
        String url = uncollectUrl + String.valueOf(id) + "/json";
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseResponse(response.body().string());
            }
        };
        sendPostRequest(url , requestBody , callback);
    }

    /**
     * 收藏文章
     * @param id 文章id
     */
    public void requestCollect(int id){
        String url = collectUrl + String.valueOf(id) + "/json";
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

    protected void parseResponse(String responseData){
        Gson gson = new Gson();
        responseBean = gson.fromJson(responseData , ResponseBean.class);
    }

    /**
     * 解析id(tag)
     * @param responseData
     */
    protected void parseId(String responseData){
        Gson gson = new Gson();
        idBean = gson.fromJson(responseData , IDBean.class);
        projectIdList = idBean.getData();
        //将解析完毕的tag数据返回给viewModel
        viewModel.setIdBean(idBean);
        viewModel.setProjectIdList(projectIdList);
    }

    /**
     * 获取页面数据
     * @param position
     */
    public void requestItemData(int position){
        viewModel.getIsItemParseFinished().postValue(false);
        String url = projectDataUrl
                + "?cid="
                + String.valueOf(getProjectIdList().get(position).getId());
        sendRequest(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //判断当前等待解析的数据是否与按钮描述一致,如果是，才能显示
                if (position == viewModel.getLastPosition().getValue()){
                    parseProjectData(response.body().string());
                    viewModel.getIsItemParseFinished().postValue(true);
                }
            }
        });
    }

    /**
     * 解析页面数据
     * @param responseData
     */
    public void parseProjectData(String responseData){
        try {
            Log.d(Constant.TAG , responseData);
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject data = jsonObject.getJSONObject("data");
            String content = data.toString();
            Gson gson = new Gson();
            chapterBean = gson.fromJson(content , ChapterBean.class);
            //给viewModel设置页面数据
            viewModel.setChapterBean(chapterBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<IDBean.DataBean> getProjectIdList() {
        return projectIdList;
    }

    public IDBean getIdBean() {
        return idBean;
    }

    public ChapterBean getChapterBean() {
        return chapterBean;
    }
}
