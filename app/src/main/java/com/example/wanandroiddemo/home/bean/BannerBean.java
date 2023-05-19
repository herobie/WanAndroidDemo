package com.example.wanandroiddemo.home.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Entity
    public static class Data{
        @ColumnInfo
        private String desc , imagePath , url , title;
        @ColumnInfo
        private int id , isVisible;
        @PrimaryKey(autoGenerate = true)
        private int bannerId;

        public int getBannerId() {
            return bannerId;
        }

        public void setBannerId(int bannerId) {
            this.bannerId = bannerId;
        }

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

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIsVisible(int isVisible) {
            this.isVisible = isVisible;
        }
    }
}
