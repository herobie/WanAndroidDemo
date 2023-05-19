package com.example.wanandroiddemo.collections.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

public class CollectedWebs {
    private int errorCode;
    private String errorMsg;
    private List<WebDatas> data;
    private static volatile CollectedWebs collectedWebs;
    public static CollectedWebs getInstance(){
        if (collectedWebs == null){
            synchronized (CollectedWebs.class){
                if (collectedWebs == null){
                    collectedWebs = new CollectedWebs();
                }
            }
        }
        return collectedWebs;
    }

    private CollectedWebs(){}
    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<WebDatas> getData() {
        return data;
    }

    public void setData(List<WebDatas> data) {
        this.data = data;
    }

    @Entity
    public static class WebDatas {
        @PrimaryKey(autoGenerate = true)
        private int itemId;
        @ColumnInfo
        private int id;
        @ColumnInfo
        private String name , link;

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
