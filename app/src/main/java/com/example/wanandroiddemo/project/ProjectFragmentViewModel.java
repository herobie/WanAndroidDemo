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
    private IDBean idBean;
    private ChapterBean chapterBean;
    private MutableLiveData<Boolean> isIDAcquired;
    private MutableLiveData<Boolean> isItemParseFinished;
    private List<IDBean.DataBean> projectIdList;
    private MutableLiveData<Integer> lastPosition;
    private final ProjectRepository repository;

    public ProjectFragmentViewModel(Application application){
        super(application);
        idUrl = "https://www.wanandroid.com/project/tree/json";
        projectDataUrl = "https://www.wanandroid.com/project/list/1/json";
        repository = new ProjectRepository(application , this);
        isIDAcquired = new MutableLiveData<>();
        isItemParseFinished = new MutableLiveData<>();
        lastPosition = new MutableLiveData<>();
        lastPosition.setValue(0);
        projectIdList = new ArrayList<>();
    }

    public void setChapterBean(ChapterBean chapterBean) {
        this.chapterBean = chapterBean;
    }

    public void setIdBean(IDBean idBean) {
        this.idBean = idBean;
    }

    public ChapterBean getChapterBean() {
        return chapterBean;
    }

    public MutableLiveData<Boolean> getIsIDAcquired() {
        return isIDAcquired;
    }

    public MutableLiveData<Boolean> getIsItemParseFinished() {
        return isItemParseFinished;
    }

    public MutableLiveData<Integer> getLastPosition() {
        return lastPosition;
    }

    public List<IDBean.DataBean> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<IDBean.DataBean> projectIdList) {
        this.projectIdList = projectIdList;
    }

    public ProjectRepository getRepository() {
        return repository;
    }
}
