package com.example.wanandroiddemo.navigation.tree;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.navigation.tree.adapter.TreeAdapter;
import com.example.wanandroiddemo.navigation.tree.adapter.TreeShortcutsAdapter;
import com.example.wanandroiddemo.navigation.tree.bean.TreeBean;
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

public class TreeViewModel extends ViewModel {
    private TreeBean treeBean;
    private MutableLiveData<Boolean> isLoadingSuccess;
    private String url = "https://www.wanandroid.com/tree/json";
    private TreeShortcutsAdapter shortcutsAdapter;
    private TreeAdapter treeAdapter;
    private MutableLiveData<Integer> lastPosition;
    private final int LOADING_SUCCESS = 1;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOADING_SUCCESS:
                    isLoadingSuccess.setValue(true);
                    break;
            }
        }
    };
    public TreeViewModel(){
        isLoadingSuccess = new MutableLiveData<>();
        lastPosition = new MutableLiveData<>();
        lastPosition.setValue(0);
    }
    public void sendRequest(String url , Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    public void parseTreeData(String responseData){
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String data = jsonObject.toString();
            Gson gson = new Gson();
            treeBean = gson.fromJson(data , TreeBean.class);
            Message message = new Message();
            message.what = LOADING_SUCCESS;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<String> generateShortcuts(){
        List<String> shortcuts = new ArrayList<>();
        for (int i = 0 ; i < treeBean.getData().size() ; i++){
            shortcuts.add(treeBean.getData().get(i).getName());
        }
        return shortcuts;
    }

    public void refresh(){
        isLoadingSuccess.setValue(false);
        sendRequest(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseTreeData(response.body().string());
            }
        });
    }

    public MutableLiveData<Boolean> getIsLoadingSuccess() {
        return isLoadingSuccess;
    }

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public MutableLiveData<Integer> getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(MutableLiveData<Integer> lastPosition) {
        this.lastPosition = lastPosition;
    }

    public TreeAdapter getTreeAdapter() {
        if (treeAdapter == null){
            treeAdapter = new TreeAdapter();
        }
        return treeAdapter;
    }

    public TreeShortcutsAdapter getShortcutsAdapter() {
        if (shortcutsAdapter == null){
            shortcutsAdapter = new TreeShortcutsAdapter();
        }
        return shortcutsAdapter;
    }
}
