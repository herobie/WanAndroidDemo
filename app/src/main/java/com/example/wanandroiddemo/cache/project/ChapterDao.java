package com.example.wanandroiddemo.cache.project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.wanandroiddemo.project.bean.ChapterBean;

import java.util.List;

@Dao
public interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChapter(ChapterBean.ProjectData...projectData);
    @Query("DELETE FROM ProjectData")
    void clearChapter();
    @Query("SELECT * FROM ProjectData")
    List<ChapterBean.ProjectData> queryAllChapter();
}
