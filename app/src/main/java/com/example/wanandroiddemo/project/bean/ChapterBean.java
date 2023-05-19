package com.example.wanandroiddemo.project.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

public class ChapterBean {
    private static volatile ChapterBean chapterBean;

    private ChapterBean(){}

    public static ChapterBean getInstance(){
        if (chapterBean == null){
            synchronized (ChapterBean.class){
                if (chapterBean == null){
                    chapterBean = new ChapterBean();
                }
            }
        }
        return chapterBean;
    }
    private List<ProjectData> datas;

    public List<ProjectData> getDatas() {
        return datas;
    }

    public void setDatas(List<ProjectData> datas) {
        this.datas = datas;
    }

    @Entity
    public static class ProjectData{
        @ColumnInfo
        private String chapterName , desc , envelopePic , link , niceDate , niceShareDate , author , projectLink , title , shareUser;
        @ColumnInfo
        private int id , chapterId , courseId;
        @PrimaryKey
        private int projectId;
        @ColumnInfo
        private boolean collect;

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public int getProjectId() {
            return projectId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public String getDesc() {
            return desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
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

        public String getAuthor() {
            return author;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public int getChapterId() {
            return chapterId;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getShareUser() {
            return shareUser;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
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

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }
    }
}
