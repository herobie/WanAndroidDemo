package com.example.wanandroiddemo.home.bean;

import java.util.List;

public class HomeBean {
    private static volatile HomeBean homeBean;
    private HomeBean(){}
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

    public class HomeData{
        private boolean adminAdd , collect;
        private String author , link , niceDate , niceShareDate , shareUser , superChapterName , title;
        private int chapterId , courseId , id , realSuperChapterId , superChapterId , userId;
        private long publishTime;
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
    }

    public class Tags{
        private String name , url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}



