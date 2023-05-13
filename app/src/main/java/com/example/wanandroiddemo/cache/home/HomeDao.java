package com.example.wanandroiddemo.cache.home;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wanandroiddemo.home.bean.HomeBean;

@Dao
public interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHome(HomeBean.HomeData...homeData);
    @Query("DELETE FROM HomeData")
    void deleteHome();
}
