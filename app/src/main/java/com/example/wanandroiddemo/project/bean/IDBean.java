package com.example.wanandroiddemo.project.bean;

import java.util.List;

public class IDBean {
    private static volatile IDBean idBean;
    private IDBean(){}
    public static IDBean getInstance(){
        if (idBean == null){
            synchronized (IDBean.class){
                if (idBean == null){
                    idBean = new IDBean();
                }
            }
        }
        return idBean;
    }
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public class DataBean{
        private String name;
        private int id;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
