package com.example.wanandroiddemo;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.wanandroiddemo.collections.CollectionsBaseFragment;
import com.example.wanandroiddemo.home.HomeFragment;
import com.example.wanandroiddemo.login.BaseLoginFragment;
import com.example.wanandroiddemo.navigation.NavFragment;
import com.example.wanandroiddemo.project.ProjectFragment;

public class MainActivityViewModel extends AndroidViewModel {
    private ProjectFragment projectFragment;
    private HomeFragment homeFragment;
    private NavFragment navFragment;
    private BaseLoginFragment baseLoginFragment;
    private WebFragment webFragment;
    private CollectionsBaseFragment collectionsBaseFragment;
    private MutableLiveData<Boolean> isLogin;
    private MainRepository mainRepository;
    private int currentPage = 0;

    public MainActivityViewModel(Application application){
        super(application);
        mainRepository = new MainRepository(application , this);
        isLogin = new MutableLiveData<>();//是否登录，默认为Null,如果登录成功为true，否则为false
        isLogin.setValue(null);
    }

    public MainRepository getMainRepository() {
        return mainRepository;
    }

    public MutableLiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public HomeFragment getHomeFragment() {
        if (homeFragment == null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    public ProjectFragment getProjectFragment() {
        if (projectFragment == null){
            projectFragment = new ProjectFragment();
        }
        return projectFragment;
    }

    public NavFragment getNavFragment() {
        if (navFragment == null){
            navFragment = new NavFragment();
        }
        return navFragment;
    }

    public BaseLoginFragment getBaseLoginFragment() {
        if (baseLoginFragment == null){
            baseLoginFragment = new BaseLoginFragment();
        }
        return baseLoginFragment;
    }

    public CollectionsBaseFragment getCollectionsBaseFragment() {
        if (collectionsBaseFragment == null){
            collectionsBaseFragment = new CollectionsBaseFragment();
        }
        return collectionsBaseFragment;
    }

    public WebFragment getWebFragment(String url) {
        if (webFragment == null){
            webFragment = new WebFragment(url);
        }else {
            webFragment.setUrl(url);
        }
        return webFragment;
    }
}
