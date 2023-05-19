package com.example.wanandroiddemo.collections.article;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleAdapter articleAdapter;
    private MutableLiveData<Boolean> isSuccess;
    private MutableLiveData<Boolean> isAdded;
    private ArticleRepository repository;
    private NewArticleDialog newArticleDialog;
    public ArticleViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(this , application);
        isSuccess = new MutableLiveData<>();//是否加载成功
        isAdded = new MutableLiveData<>();//是否添加
        isAdded.setValue(false);
        isSuccess.setValue(false);
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public MutableLiveData<Boolean> getIsAdded() {
        return isAdded;
    }

    public ArticleAdapter getArticleAdapter() {
        if (articleAdapter == null){
            articleAdapter = new ArticleAdapter(this , getApplication());
        }
        return articleAdapter;
    }

    public ArticleRepository getRepository() {
        return repository;
    }

    public NewArticleDialog getNewArticleDialog(Context context) {
        if (newArticleDialog == null){
            newArticleDialog = new NewArticleDialog(context, this);//Dialog这里不能传applicationContext
        }
        return newArticleDialog;
    }
}
