package com.example.wanandroiddemo;

import android.app.Application;

import androidx.fragment.app.Fragment;
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
    private Fragment currentFragment;

    public MainActivityViewModel(Application application){
        super(application);
        mainRepository = new MainRepository(application , this);
        isLogin = new MutableLiveData<>();//是否登录，默认为Null,如果登录成功为true，否则为false
        isLogin.setValue(null);
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public Fragment getCurrentFragment() {
        if (currentFragment == null){
            currentFragment = getHomeFragment();
        }
        return currentFragment;
    }

    public MainRepository getMainRepository() {
        return mainRepository;
    }

    public MutableLiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public HomeFragment getHomeFragment() {
        if (homeFragment == null){
            homeFragment = HomeFragment.getInstance("Home");
        }
        return homeFragment;
    }

    public ProjectFragment getProjectFragment() {
        if (projectFragment == null){
            projectFragment = ProjectFragment.getInstance("Project");
        }
        return projectFragment;
    }

    public NavFragment getNavFragment() {
        if (navFragment == null){
            navFragment = NavFragment.getInstance("Nav");
        }
        return navFragment;
    }

    public BaseLoginFragment getBaseLoginFragment() {
        if (baseLoginFragment == null){
            baseLoginFragment = BaseLoginFragment.getInstance("BaseLogin");
        }
        return baseLoginFragment;
    }

    public CollectionsBaseFragment getCollectionsBaseFragment() {
        if (collectionsBaseFragment == null){
            collectionsBaseFragment = CollectionsBaseFragment.getInstance("BaseCollections");
        }
        return collectionsBaseFragment;
    }

    public WebFragment getWebFragment(String url) {
        if (webFragment == null){
            webFragment = WebFragment.getInstance(url , "Web");
        }else {
            webFragment.setUrl(url);
        }
        return webFragment;
    }
}
