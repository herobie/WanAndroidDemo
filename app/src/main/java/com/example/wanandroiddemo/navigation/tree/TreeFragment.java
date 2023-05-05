package com.example.wanandroiddemo.navigation.tree;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentNavigationBinding;
import com.example.wanandroiddemo.navigation.tree.bean.TreeBean;
import com.google.android.flexbox.FlexboxLayoutManager;

public class TreeFragment extends Fragment {
    private TreeViewModel viewModel;
    private FragmentNavigationBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_navigation, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TreeViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        FlexboxLayoutManager flexLayoutManager = new FlexboxLayoutManager(getContext());
        binding.navigationRvShortcut.setLayoutManager(layoutManager);
        binding.navigationRv.setLayoutManager(flexLayoutManager);
        Observer<Integer> observerPositionChanged = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (viewModel.getTreeBean() != null){
                    viewModel.getTreeAdapter()
                            .setChildren(viewModel.getTreeBean().getData().get(integer).getChildren());
                    binding.navigationRv.setAdapter(viewModel.getTreeAdapter());
                    Log.d(Constant.TAG , String.valueOf(integer));
                }
            }
        };
        viewModel.getLastPosition().observe(getViewLifecycleOwner(), observerPositionChanged);
        viewModel.getIsLoadingSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    if (aBoolean){
                        viewModel.getShortcutsAdapter()
                                .setShortcuts(viewModel.generateShortcuts());
                        binding.navigationRvShortcut.setAdapter(viewModel.getShortcutsAdapter());
                        viewModel.getTreeAdapter()
                                .setChildren(viewModel.getTreeBean().getData().get(viewModel.getLastPosition().getValue()).getChildren());
                        binding.navigationRv.setAdapter(viewModel.getTreeAdapter());
                        Log.d(Constant.TAG , "Loading Success");
                    }
                }
            }
        });
        viewModel.refresh();
    }
}