package com.example.wanandroiddemo.project.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    @Entity
    public class DataBean{
        @ColumnInfo
        private String name;
        @PrimaryKey
        private int id;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
