package com.example.wanandroiddemo.project;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.project.bean.ChapterBean;
import com.example.wanandroiddemo.project.bean.IDBean;
import com.google.gson.Gson;

import org.json.JSONArray;
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

public class ProjectFragmentViewModel extends ViewModel {
    private String idUrl , projectDataUrl;
    private IDBean idBean;
    private ChapterBean chapterBean;
    private MutableLiveData<Boolean> isIDAcquired;
    private MutableLiveData<Boolean> isItemParseFinished;
    private List<IDBean.DataBean> projectIdList;
    private int lastPosition = 0;
    private final int ID_ACQUIRED = 1;
    private final int ITEM_FINISHED = 2;
    private Handler handler = new android.os.Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ID_ACQUIRED:
                    isIDAcquired.setValue(true);
                    break;
                case ITEM_FINISHED:
                    isItemParseFinished.setValue(true);
                    Log.d("MainActivity" , "Item Finished!");
                    break;
            }
        }
    };
    public ProjectFragmentViewModel(){
        idUrl = "https://www.wanandroid.com/project/tree/json";
        projectDataUrl = "https://www.wanandroid.com/project/list/1/json";
        isIDAcquired = new MutableLiveData<>();
        isItemParseFinished = new MutableLiveData<>();
        projectIdList = new ArrayList<>();
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
    }

    /**
     * 解析id
     * @param responseData
     */
    public void parseId(String responseData){
        Gson gson = new Gson();
        idBean = gson.fromJson(responseData , IDBean.class);
        projectIdList = idBean.getData();
        Message message = new Message();
        message.what = ID_ACQUIRED;
        handler.sendMessage(message);
    }

    public void parseProjectData(String responseData){
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject data = jsonObject.getJSONObject("data");
            String content = data.toString();
            Gson gson = new Gson();
            chapterBean = gson.fromJson(content , ChapterBean.class);
            Message message = new Message();
            message.what = ITEM_FINISHED;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void acquireID(){
        isIDAcquired.setValue(false);
        sendRequest(getIdUrl(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseId(response.body().string());
            }
        });
    }

    public void acquireData(int position){
        isItemParseFinished.setValue(false);
        lastPosition = position;
        String url = getProjectDataUrl()
                + "?cid="
                + String.valueOf(getProjectIdList().get(position).getId());
        sendRequest(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //判断当前等待解析的数据是否与按钮描述一致
                if (position == getLastPosition()){
                    parseProjectData(response.body().string());
                }
            }
        });
    }

    public String getIdUrl() {
        return idUrl;
    }

    public String getProjectDataUrl() {
        return projectDataUrl;
    }

    public IDBean getIdBean() {
        return idBean;
    }

    public ChapterBean getChapterBean() {
        return chapterBean;
    }

    public MutableLiveData<Boolean> getIsIDAcquired() {
        return isIDAcquired;
    }

    public MutableLiveData<Boolean> getIsItemParseFinished() {
        return isItemParseFinished;
    }

    public List<IDBean.DataBean> getProjectIdList() {
        return projectIdList;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }
}
