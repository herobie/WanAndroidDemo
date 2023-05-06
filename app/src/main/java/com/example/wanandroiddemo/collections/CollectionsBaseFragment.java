package com.example.wanandroiddemo.collections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentCollectionsBaseBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class CollectionsBaseFragment extends Fragment {
    private CollectionsBaseFragmentViewModel viewModel;
    private FragmentCollectionsBaseBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_collections_base, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView(){
        viewModel = new ViewModelProvider(this).get(CollectionsBaseFragmentViewModel.class);
        CollectionsBaseAdapter collectionsBaseAdapter = new CollectionsBaseAdapter(getActivity() , viewModel.getFragments());
        binding.collectionsViewPage.setAdapter(collectionsBaseAdapter);
        new TabLayoutMediator(binding.collectionsTab, binding.collectionsViewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("收藏文章");
                }else if (position == 1){
                    tab.setText("收藏网站");
                }
            }
        }).attach();
    }
}