package com.example.wanandroiddemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.home.HomeFragment;
import com.example.wanandroiddemo.login.BaseLoginFragment;
import com.example.wanandroiddemo.login.LoginFragment;
import com.example.wanandroiddemo.login.bean.LoginBean;
import com.example.wanandroiddemo.navigation.NavFragment;
import com.example.wanandroiddemo.navigation.nav.NavigationFragment;
import com.example.wanandroiddemo.project.ProjectFragment;
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

public class MainActivityViewModel extends ViewModel {
    private ProjectFragment projectFragment;
    private HomeFragment homeFragment;
    private NavFragment navFragment;
    private BaseLoginFragment baseLoginFragment;
    private MutableLiveData<Boolean> isLogin;
    private String loginUrl = "https://www.wanandroid.com/user/login";
    private String username , password , repassword;
    private LoginBean loginBean;
    private int currentPage = 0;
    private final int LOGIN_SUCCEED = 1;
    private final int LOGIN_FAILED = -1;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOGIN_SUCCEED:
                    isLogin.setValue(true);
                    break;
                case LOGIN_FAILED:
                    isLogin.setValue(false);
                    break;
            }
        }
    };

    public MainActivityViewModel(){
        isLogin = new MutableLiveData<>();//是否登录，默认为Null,如果登录成功为true，否则为false
        isLogin.setValue(null);
    }

    /**
     * 生成RequestBody
     * @param isLogin 是否是登录(不是登录不用加repassword)
     */
    public RequestBody generateRequestBody(boolean isLogin){
        RequestBody requestBody;
        if (isLogin){
            requestBody = new FormBody.Builder()
                    .add("username" , getUsername())
                    .add("password" , getPassword())
                    .build();
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
                OkHttpClient client = new OkHttpClient();
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

    public MutableLiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public HomeFragment getHomeFragment() {
        if (homeFragment == null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    public ProjectFragment getProjectFragment() {
        if (projectFragment == null){
            projectFragment = new ProjectFragment();
        }
        return projectFragment;
    }

    public NavFragment getNavFragment() {
        if (navFragment == null){
            navFragment = new NavFragment();
        }
        return navFragment;
    }

    public BaseLoginFragment getBaseLoginFragment() {
        if (baseLoginFragment == null){
            baseLoginFragment = new BaseLoginFragment();
        }
        return baseLoginFragment;
    }
}
