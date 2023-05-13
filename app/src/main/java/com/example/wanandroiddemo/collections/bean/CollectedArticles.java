package com.example.wanandroiddemo.collections.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * 收藏文章列表
 */
public class CollectedArticles {
    private String errorMsg;
    private int errorCode;//-1001请先登录
    private Data data;
    private static volatile CollectedArticles collectedArticles;

    public CollectedArticles(){
    }

    public static CollectedArticles getInstance(){
        if (collectedArticles == null){
            synchronized (CollectedArticles.class){
                if (collectedArticles == null){
                    collectedArticles = new CollectedArticles();
                }
            }
        }
        return collectedArticles;
    }

    public Data getData() {
        return data;
    }

    public class Data{
        private List<CollectedData> datas;

        public List<CollectedData> getDatas() {
            return datas;
        }
        @Entity
        public class CollectedData{
            @ColumnInfo
            private String title;
            @ColumnInfo
            private String author , link , niceDate;
            @ColumnInfo
            private int userId;
            @ColumnInfo
            private int courseId , id , originId;
            @PrimaryKey(autoGenerate = true)
            private int primaryKeyId;

            public int getPrimaryKeyId() {
                return primaryKeyId;
            }

            public void setPrimaryKeyId(int primaryKeyId) {
                this.primaryKeyId = primaryKeyId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOriginId() {
                return originId;
            }

            public void setOriginId(int originId) {
                this.originId = originId;
            }
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }


}
