package com.example.wanandroiddemo.login;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.MainActivityViewModel;
import com.example.wanandroiddemo.login.bean.LoginBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginViewModel extends ViewModel {
    private String username , password;
    private String url = "https://www.wanandroid.com/user/login";
    private LoginBean loginBean;
    private final int LOGIN_SUCCEED = 1;
    private final int LOGIN_FAILED = -1;
    private MutableLiveData<Boolean> isFailed;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOGIN_SUCCEED:
                    MainActivityViewModel.isLogin.setValue(true);
                    break;
                case LOGIN_FAILED:
                    isFailed.setValue(true);
                    break;
            }
        }
    };

    public LoginViewModel(){
        isFailed = new MutableLiveData<>();
        isFailed.setValue(false);
    }

    public void sendLoginRequest(){
        isFailed.setValue(false);
        RequestBody requestBody = new FormBody.Builder()
                .add("username" , getUsername())
                .add("password" , getPassword())
                .build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseLoginData(response.body().string());
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    public void parseLoginData(String responseData){
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String data = jsonObject.toString();
            Gson gson = new Gson();
            loginBean = gson.fromJson(data , LoginBean.class);
            Message message = new Message();
            if (loginBean.getErrorCode() == -1){
                message.what = LOGIN_FAILED;
            }else {
                message.what = LOGIN_SUCCEED;
            }
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MutableLiveData<Boolean> getIsFailed() {
        return isFailed;
    }
}
