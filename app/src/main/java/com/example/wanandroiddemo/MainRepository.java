package com.example.wanandroiddemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wanandroiddemo.cookieStore.CookieJarImpl;
import com.example.wanandroiddemo.cookieStore.PersistentCookieStore;
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

public class MainRepository {
    private Context context;
    private final String loginUrl = "https://www.wanandroid.com/user/login";
    private String username , password , repassword;
    private LoginBean loginBean;
    private MainActivityViewModel mainActivityViewModel;
    public MainRepository(Context context , MainActivityViewModel mainActivityViewModel) {
        this.context = context;
        this.mainActivityViewModel = mainActivityViewModel;
    }

    /**
     * 生成RequestBody
     * @param isLogin 是否是登录界面(不是登录不用加repassword)
     */
    public RequestBody generateRequestBody(boolean isLogin){
        RequestBody requestBody;
        if (isLogin){
            requestBody = new FormBody.Builder()
                    .add("username" , getUsername())
                    .add("password" , getPassword())
                    .build();
//            requestBody = new FormBody.Builder()
//                    .add("username" , "herobie")
//                    .add("password" , "125612792")
//                    .build();
        }else {
            requestBody = new FormBody.Builder()
                    .add("username" , getUsername())
                    .add("password" , getPassword())
                    .add("repassword" , getRepassword())
                    .build();
        }
        return requestBody;
    }

    public void sendLoginRequest(RequestBody requestBody){
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
                OkHttpClient client = new OkHttpClient.Builder()
                        .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                        .build();
                Request request = new Request.Builder()
                        .url(loginUrl)
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
            Log.d(Constant.TAG , data);
            Gson gson = new Gson();
            loginBean = gson.fromJson(data , LoginBean.class);
            if (loginBean.getErrorCode() == -1){
                mainActivityViewModel.getIsLogin().postValue(false);
            }else {
                mainActivityViewModel.getIsLogin().postValue(true);
            }
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

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
