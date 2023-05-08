package com.example.wanandroiddemo.home.cache;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.wanandroiddemo.home.bean.HomeBean;

@Dao
public interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHome(HomeBean.HomeData ... homeData);
    @Delete
    void deleteHome(HomeBean.HomeData ... homeData);
}
