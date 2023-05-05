package com.example.wanandroiddemo.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseViewModel extends ViewModel {
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private List<Fragment> fragments;
    private MutableLiveData<Integer> currentPage;

    public BaseViewModel(){
        fragments = new ArrayList<>();
        fragments.add(getLoginFragment());
        fragments.add(getRegisterFragment());
        currentPage = new MutableLiveData<>();
        currentPage.setValue(0);

    }

    public LoginFragment getLoginFragment() {
        if (loginFragment == null){
            loginFragment = new LoginFragment();
        }
        return loginFragment;
    }

    public RegisterFragment getRegisterFragment() {
        if (registerFragment == null){
            registerFragment = new RegisterFragment();
        }
        return registerFragment;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }
}
