package com.example.wanandroiddemo.cache.home;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wanandroiddemo.home.bean.BannerBean;

import java.util.List;

@Dao
public interface BannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBanner(BannerBean.Data ... bannerData);
    @Query("DELETE FROM Data")
    void clearBanner();
    @Query("SELECT * FROM Data")
    List<BannerBean.Data> queryAllBanner();
}
