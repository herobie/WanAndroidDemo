package com.example.wanandroiddemo.cache.collections;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wanandroiddemo.collections.bean.CollectedWebs;

import java.util.List;

@Dao
public interface WebDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeb(CollectedWebs.WebDatas...webDatas);

    @Query("DELETE FROM WebDatas")
    void clearWeb();//清空收藏的网页

    @Query("DELETE FROM Webdatas WHERE id=:id")
    void deleteWeb(int id);//删除指定网页

    @Query("SELECT * FROM WebDatas")
    List<CollectedWebs.WebDatas> queryAll();//把缓存的数据提取出来
}
