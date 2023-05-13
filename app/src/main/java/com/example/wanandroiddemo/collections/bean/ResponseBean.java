package com.example.wanandroiddemo.collections.bean;

/**
 * 执行取消或增加收藏后的返回数据
 */
public class ResponseBean {
    private String data , errorMsg;
    private int errorCode;
    private static volatile ResponseBean responseBean;
    public static ResponseBean getInstance(){
        synchronized (ResponseBean.class){
            if (responseBean == null){
                responseBean = new ResponseBean();
            }
        }
        return responseBean;
    }
    public ResponseBean() {
    }

    public String getData() {
        return data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
