package com.example.wanandroiddemo.cache;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wanandroiddemo.cache.collections.ArticleDao;
import com.example.wanandroiddemo.collections.bean.CollectedArticles;
import com.example.wanandroiddemo.home.bean.BannerBean;
import com.example.wanandroiddemo.home.bean.HomeBean;
import com.example.wanandroiddemo.cache.home.BannerDao;
import com.example.wanandroiddemo.cache.home.HomeDao;
import com.example.wanandroiddemo.project.bean.ChapterBean;
import com.example.wanandroiddemo.project.bean.IDBean;
import com.example.wanandroiddemo.cache.project.ChapterDao;
import com.example.wanandroiddemo.cache.project.IDDao;

@Database(entities = {HomeBean.HomeData.class , BannerBean.Data.class , IDBean.DataBean.class , ChapterBean.ProjectData.class , CollectedArticles.Data.CollectedData.class} , version = 3 , exportSchema = false)
public abstract class Cache extends RoomDatabase {
    private static Cache INSTANCE;
    public static synchronized Cache getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext() , Cache.class , "WanAndroidCache")
                    .build();
        }
        return INSTANCE;
    }

    public abstract HomeDao getHomeDao();

    public abstract BannerDao getBannerDao();

    public abstract IDDao getIDDao();

    public abstract ChapterDao getChapterDao();

    public abstract ArticleDao getArticleDao();
}
