package com.example.wanandroiddemo.collections;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.collections.article.ArticleFragment;
import com.example.wanandroiddemo.collections.website.WebsiteFragment;

import java.util.ArrayList;
import java.util.List;

public class CollectionsBaseFragmentViewModel extends ViewModel {
    private ArticleFragment articleFragment;
    private WebsiteFragment websiteFragment;
    private List<Fragment> fragments;

    public CollectionsBaseFragmentViewModel(){
        fragments = new ArrayList<>();
        fragments.add(getArticleFragment());
        fragments.add(getWebsiteFragment());
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public ArticleFragment getArticleFragment() {
        if (articleFragment == null){
            articleFragment = new ArticleFragment();
        }
        return articleFragment;
    }

    public WebsiteFragment getWebsiteFragment() {
        if (websiteFragment == null){
            websiteFragment = new WebsiteFragment();
        }
        return websiteFragment;
    }
}
