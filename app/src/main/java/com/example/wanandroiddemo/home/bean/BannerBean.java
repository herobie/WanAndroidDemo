package com.example.wanandroiddemo.home.bean;

import java.util.List;

public class BannerBean {
    private static volatile BannerBean bannerBean;
    private BannerBean(){}
    public static BannerBean getInstance(){
        if (bannerBean == null){
            synchronized (BannerBean.class){
                if (bannerBean == null){
                    bannerBean = new BannerBean();
                }
            }
        }
        return bannerBean;
    }

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data{
        private String desc , imagePath , url , title;
        private int id , isVisible;

        public String getDesc() {
            return desc;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public int getIsVisible() {
            return isVisible;
        }
    }
}
