package com.example.wanandroiddemo.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.wanandroiddemo.home.adapter.HomeItemAdapter;
import com.example.wanandroiddemo.home.bean.BannerBean;
import com.example.wanandroiddemo.home.bean.HomeBean;

public class HomeFragmentViewModel extends AndroidViewModel {
    private HomeItemAdapter homeItemAdapter;
    private HomeRepository repository;

    public HomeFragmentViewModel(@NonNull Application application){
        super(application);
        repository = new HomeRepository(application);
    }

    public void insertHome(HomeBean.HomeData...homeData){
        repository.insertHome(homeData);
    }

    public void insertBanner(BannerBean.Data...data){
        repository.insertBanner(data);
    }

    public void deleteHome(){
        repository.deleteHome();
    }

    public void setHomeItemAdapter(HomeItemAdapter homeItemAdapter) {
        this.homeItemAdapter = homeItemAdapter;
    }

    public HomeItemAdapter getHomeItemAdapter() {
        return homeItemAdapter;
    }

    public HomeRepository getRepository() {
        return repository;
    }
}
