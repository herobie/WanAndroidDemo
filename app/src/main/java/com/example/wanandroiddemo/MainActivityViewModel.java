package com.example.wanandroiddemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.home.HomeFragment;
import com.example.wanandroiddemo.login.BaseLoginFragment;
import com.example.wanandroiddemo.login.LoginFragment;
import com.example.wanandroiddemo.navigation.NavFragment;
import com.example.wanandroiddemo.navigation.nav.NavigationFragment;
import com.example.wanandroiddemo.project.ProjectFragment;

public class MainActivityViewModel extends ViewModel {
    private ProjectFragment projectFragment;
    private HomeFragment homeFragment;
    private NavFragment navFragment;
    private BaseLoginFragment baseLoginFragment;
    public static MutableLiveData<Boolean> isLogin;
    public MainActivityViewModel(){
        isLogin = new MutableLiveData<>();
        isLogin.setValue(false);
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
}
