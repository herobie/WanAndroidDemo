package com.example.wanandroiddemo.project.bean;

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

    public class ProjectData{
        private String chapterName , desc , envelopePic , link , niceDate , niceShareDate , author , projectLink , title , shareUser;
        private int id , chapterId , courseId;

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
    }
}
