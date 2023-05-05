package com.example.wanandroiddemo.navigation;

import androidx.lifecycle.ViewModel;

import com.example.wanandroiddemo.navigation.nav.NavigationFragment;
import com.example.wanandroiddemo.navigation.tree.TreeFragment;

public class NavViewModel extends ViewModel {
    private TreeFragment treeFragment;
    private NavigationFragment navigationFragment;


    public TreeFragment getTreeFragment() {
        if (treeFragment == null){
            treeFragment = new TreeFragment();
        }
        return treeFragment;
    }

    public NavigationFragment getNavigationFragment() {
        if (navigationFragment == null){
            navigationFragment = new NavigationFragment();
        }
        return navigationFragment;
    }
}
