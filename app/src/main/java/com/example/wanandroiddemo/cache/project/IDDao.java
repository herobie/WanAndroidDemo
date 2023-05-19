package com.example.wanandroiddemo.cache.project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wanandroiddemo.project.bean.IDBean;

import java.util.List;

@Dao
public interface IDDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertId(IDBean.DataBean...dataBeans);
    @Query("DELETE FROM DataBean")
    void clearId();
    @Query("SELECT * FROM DataBean")
    List<IDBean.DataBean> queryAllId();
}
