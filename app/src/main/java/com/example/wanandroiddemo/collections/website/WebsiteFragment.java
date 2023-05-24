package com.example.wanandroiddemo.collections.website;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentWebsiteBinding;
import com.google.android.flexbox.FlexboxLayoutManager;

public class WebsiteFragment extends Fragment {
    private FragmentWebsiteBinding binding;
    private WebsiteViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_website, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WebsiteViewModel.class);
        binding.webRefresh.setOnRefreshListener(() -> viewModel.getRepository().requestCollectedWebList());
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext().getApplicationContext());
        binding.webRv.addItemDecoration(new DividerItemDecoration(getContext().getApplicationContext() , DividerItemDecoration.VERTICAL));
        binding.webRv.setLayoutManager(layoutManager);
        binding.webRv.setAdapter(viewModel.getWebAdapter());
        viewModel.getIsWebAcquired().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    viewModel.getWebAdapter().setDatas(viewModel.getRepository().getWeb().getData());
                    viewModel.getWebAdapter().notifyDataSetChanged();
                    Log.d(Constant.TAG , String.valueOf(viewModel.getWebAdapter().getItemCount()));
                    if (binding.webRefresh.isRefreshing()){
                        binding.webRefresh.setRefreshing(false);
                    }
                }
            }
        });
        viewModel.getIsAdded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    viewModel.getWebAdapter().notifyDataSetChanged();//添加成功后刷新布局
                }
            }
        });
        viewModel.getRepository().requestCollectedWebList();
        binding.webAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDialog(getContext()).show();
            }
        });
    }
}