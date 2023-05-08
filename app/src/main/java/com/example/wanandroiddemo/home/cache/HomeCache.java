package com.example.wanandroiddemo.home.cache;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wanandroiddemo.home.bean.BannerBean;
import com.example.wanandroiddemo.home.bean.HomeBean;

@Database(entities = {HomeBean.HomeData.class , BannerBean.Data.class} , version = 2 , exportSchema = false)
public abstract class HomeCache extends RoomDatabase {
    private static HomeCache INSTANCE;
    static synchronized HomeCache getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext() , HomeCache.class , "HomeCache")
                    .build();
        }
        return INSTANCE;
    }

    public abstract HomeDao getHomeDao();

    public abstract BannerDao getBannerDao();
}
