package com.example.wanandroiddemo.project;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroiddemo.project.bean.ChapterBean;
import com.example.wanandroiddemo.project.bean.IDBean;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragmentViewModel extends AndroidViewModel {
    private final String idUrl , projectDataUrl;
    private MutableLiveData<Boolean> isItemParseFinished , isIDAcquired , isFailed;
    private MutableLiveData<Integer> lastPosition;
    private final ProjectRepository repository;

    public ProjectFragmentViewModel(Application application){
        super(application);
        idUrl = "https://www.wanandroid.com/project/tree/json";
        projectDataUrl = "https://www.wanandroid.com/project/list/1/json";
        repository = new ProjectRepository(application , this);
        isIDAcquired = new MutableLiveData<>();
        isItemParseFinished = new MutableLiveData<>();
        isFailed = new MutableLiveData<>();
        isFailed.setValue(false);
        lastPosition = new MutableLiveData<>();
        lastPosition.setValue(0);
    }

    public MutableLiveData<Boolean> getIsIDAcquired() {
        return isIDAcquired;
    }

    public MutableLiveData<Boolean> getIsItemParseFinished() {
        return isItemParseFinished;
    }

    public MutableLiveData<Boolean> getIsFailed() {
        return isFailed;
    }

    public MutableLiveData<Integer> getLastPosition() {
        return lastPosition;
    }

    public ProjectRepository getRepository() {
        return repository;
    }
}
