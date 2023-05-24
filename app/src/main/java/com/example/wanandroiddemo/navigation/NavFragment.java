package com.example.wanandroiddemo.navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentNavBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class NavFragment extends Fragment {
    private FragmentNavBinding binding;
    private NavViewModel viewModel;

    public static NavFragment getInstance(String param){
        NavFragment navFragment = new NavFragment();
        Bundle args = new Bundle();
        args.putString(Constant.ARG_PARAM1, param);
        navFragment.setArguments(args);
        return navFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_nav, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NavViewModel.class);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(viewModel.getTreeFragment());
        fragmentList.add(viewModel.getNavigationFragment());
        NavAdapter navAdapter = new NavAdapter(getActivity() , fragmentList);
        binding.navViewPager.setAdapter(navAdapter);
        new TabLayoutMediator(binding.navTab, binding.navViewPager, (tab, position) -> {
            if (position == 0){
                tab.setText("导航");
            }else {
                tab.setText("体系");
            }
        }).attach();
    }
}