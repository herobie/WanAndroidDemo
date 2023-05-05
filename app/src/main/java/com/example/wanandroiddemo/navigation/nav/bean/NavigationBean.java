package com.example.wanandroiddemo.navigation.nav.bean;

import java.util.List;

public class NavigationBean {
    private static volatile NavigationBean navigationBean;
    private NavigationBean(){}

    public static NavigationBean getInstance(){
        if (navigationBean == null){
            synchronized (NavigationBean.class){
                if (navigationBean == null){
                    navigationBean = new NavigationBean();
                }
            }
        }
        return navigationBean;
    }

    private List<NavData> data;

    public List<NavData> getData() {
        return data;
    }

    public class NavData{
        private List<Article> articles;

        public List<Article> getArticles() {
            return articles;
        }

        public class Article{
            private String link , chapterName , title;

            public String getLink() {
                return link;
            }

            public String getChapterName() {
                return chapterName;
            }

            public String getTitle() {
                return title;
            }
        }
    }

}
