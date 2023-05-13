package com.example.wanandroiddemo.cache.project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.wanandroiddemo.project.bean.IDBean;

@Dao
public interface IDDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertId(IDBean.DataBean...dataBeans);
    @Delete
    void clearId(IDBean.DataBean...dataBeans);
}
