package com.example.wanandroiddemo.home.cache;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.wanandroiddemo.home.bean.BannerBean;

@Dao
public interface BannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBanner(BannerBean.Data ... bannerData);
    @Delete
    void clearBanner(BannerBean.Data ...bannerData);
}
