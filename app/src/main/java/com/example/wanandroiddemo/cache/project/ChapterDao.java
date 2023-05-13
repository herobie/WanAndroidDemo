package com.example.wanandroiddemo.cache.project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.wanandroiddemo.project.bean.ChapterBean;
@Dao
public interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChapter(ChapterBean.ProjectData...projectData);
    @Delete
    void clearChapter(ChapterBean.ProjectData...projectData);
}
