package com.example.wanandroiddemo.home.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

public class HomeBean {
    private static volatile HomeBean homeBean;
    public HomeBean(){}
    public static HomeBean getInstance(){
        if (homeBean == null){
            synchronized (HomeBean.class){
                if (homeBean == null){
                    homeBean = new HomeBean();
                }
            }
        }
        return homeBean;
    }

    private List<HomeData> datas;

    private int curPage;

    public List<HomeData> getDatas() {
        return datas;
    }

    @Entity
    public class HomeData{
        @PrimaryKey(autoGenerate = true)
        private int homeId;
        @ColumnInfo
        private boolean adminAdd , collect;
        @ColumnInfo
        private String author , link , niceDate , niceShareDate , shareUser , superChapterName , title;
        @ColumnInfo
        private int chapterId , courseId , id , realSuperChapterId , superChapterId , userId;
        @ColumnInfo
        private long publishTime;
        @Ignore
        private List<Tags> tags;

        public boolean isAdminAdd() {
            return adminAdd;
        }

        public boolean isCollect() {
            return collect;
        }

        public String getAuthor() {
            return author;
        }

        public String getLink() {
            return link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public String getNiceShareDate() {
            return niceShareDate;
        }

        public String getShareUser() {
            return shareUser;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public int getChapterId() {
            return chapterId;
        }

        public int getCourseId() {
            return courseId;
        }

        public int getId() {
            return id;
        }

        public int getRealSuperChapterId() {
            return realSuperChapterId;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public int getUserId() {
            return userId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public List<Tags> getTags() {
            return tags;
        }

        public void setAdminAdd(boolean adminAdd) {
            this.adminAdd = adminAdd;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public void setNiceShareDate(String niceShareDate) {
            this.niceShareDate = niceShareDate;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public int getHomeId() {
            return homeId;
        }

        public void setHomeId(int homeId) {
            this.homeId = homeId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setRealSuperChapterId(int realSuperChapterId) {
            this.realSuperChapterId = realSuperChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public void setTags(List<Tags> tags) {
            this.tags = tags;
        }
    }

    public class Tags{
        private String name , url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}



