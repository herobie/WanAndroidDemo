package com.example.wanandroiddemo.navigation.nav;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.navigation.nav.adapter.NavigationAdapter;
import com.example.wanandroiddemo.navigation.nav.adapter.ShortcutAdapter;
import com.example.wanandroiddemo.navigation.nav.bean.NavigationBean;
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

public class NavigationViewModel extends ViewModel {
    private NavigationBean navigationBean;
    private MutableLiveData<Boolean> isLoadingSuccess;
    private ShortcutAdapter shortcutAdapter;
    private NavigationAdapter navigationAdapter;
    private String navigationUrl = "https://www.wanandroid.com/navi/json";
    private MutableLiveData<Integer> currentChapter;
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
    public NavigationViewModel(){
        currentChapter = new MutableLiveData<>();
        currentChapter.setValue(0);
        isLoadingSuccess = new MutableLiveData<>();
        isLoadingSuccess.setValue(false);
        shortcutAdapter = new ShortcutAdapter(this);
        navigationAdapter = new NavigationAdapter();
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

    public void parseNavigation(String responseData){
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String data = jsonObject.toString();
            Gson gson = new Gson();
            navigationBean = gson.fromJson(data , NavigationBean.class);
            Message message = new Message();
            message.what = LOADING_SUCCESS;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成快捷栏
     * @param navigationBean
     * @return
     */
    public List<String> generateShortCuts(NavigationBean navigationBean){
        List<String> shortcuts = new ArrayList<>();
        for (int i = 0 ; i < navigationBean.getData().size() ; i++){
            String chapterName;
            try {
                chapterName = navigationBean.getData()
                        .get(i)
                        .getArticles()
                        .get(0)
                        .getChapterName();
            }catch (IndexOutOfBoundsException e){
                chapterName = null;
            }
            boolean isExist = false;
            for (int j = 0 ; j < shortcuts.size() ; j++){
                if (shortcuts.get(j) == null || shortcuts.get(j).equals(chapterName) || shortcuts.get(j).isEmpty()){
                    isExist = true;
                    break;
                }
            }
            if (!isExist){
                shortcuts.add(chapterName);
            }
        }
        return shortcuts;
    }

    public void refresh(){
        isLoadingSuccess.setValue(false);
        sendRequest(navigationUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseNavigation(response.body().string());
            }
        });
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public String getNavigationUrl() {
        return navigationUrl;
    }

    public MutableLiveData<Boolean> getIsLoadingSuccess() {
        return isLoadingSuccess;
    }

    public ShortcutAdapter getShortcutAdapter() {
        return shortcutAdapter;
    }

    public NavigationAdapter getNavigationAdapter() {
        return navigationAdapter;
    }

    public MutableLiveData<Integer> getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(MutableLiveData<Integer> currentChapter) {
        this.currentChapter = currentChapter;
    }
}
