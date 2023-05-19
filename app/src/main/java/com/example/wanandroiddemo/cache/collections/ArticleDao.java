package com.example.wanandroiddemo.cache.collections;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.wanandroiddemo.collections.bean.CollectedArticles;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert
    void insertArticle(CollectedArticles.Data.CollectedData...collectedData);

    @Query("DELETE FROM CollectedData")//暂时先用这个DELETE FROM
    void clearAllArticle();//清空数据，刷新时进行清空重新录入

    @Query("DELETE FROM CollectedData WHERE id =:id")
    void deleteArticle(int id);//删除指定数据，取消收藏时调用

    @Query("SELECT EXISTS(SELECT link FROM CollectedData WHERE link=:link)")
    boolean querySameLink(String link);

    @Query("SELECT * FROM CollectedData")
    List<CollectedArticles.Data.CollectedData> queryAll();
}
