package com.example.wanandroiddemo.login.bean;

public class LoginBean {
    private static volatile LoginBean loginBean;
    private LoginBean(){}
    public static LoginBean getInstance(){
        if (loginBean == null){
            synchronized (LoginBean.class){
                if (loginBean == null){
                    loginBean = new LoginBean();
                }
            }
        }
        return loginBean;
    }
    private String nickname , publicName , username , errorMsg;
    private int id , coinCount , errorCode;

    public String getNickname() {
        return nickname;
    }

    public String getPublicName() {
        return publicName;
    }

    public String getUsername() {
        return username;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getId() {
        return id;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
