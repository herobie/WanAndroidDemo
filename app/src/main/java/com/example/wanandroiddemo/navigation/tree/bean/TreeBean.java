package com.example.wanandroiddemo.navigation.tree.bean;

import java.util.List;

public class TreeBean {
    private static volatile TreeBean treeBean;

    private TreeBean(){}
    public static TreeBean getInstance(){
        if (treeBean == null){
            synchronized (TreeBean.class){
                if (treeBean == null){
                    treeBean = new TreeBean();
                }
            }
        }
        return treeBean;
    }

    private List<TreeData> data;

    public List<TreeData> getData() {
        return data;
    }

    public class TreeData{
        private List<Children> children;
        private String author , name , cover;
        private int courseId , id , parentChapterId;

        public String getAuthor() {
            return author;
        }

        public String getName() {
            return name;
        }

        public String getCover() {
            return cover;
        }

        public int getCourseId() {
            return courseId;
        }

        public int getId() {
            return id;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public List<Children> getChildren() {
            return children;
        }

        public class Children{
            private String name , author;
            private int id , courseId;

            public String getName() {
                return name;
            }

            public String getAuthor() {
                return author;
            }

            public int getId() {
                return id;
            }

            public int getCourseId() {
                return courseId;
            }
        }
    }
}
