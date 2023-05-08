package com.example.wanandroiddemo.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentHomeBinding;
import com.example.wanandroiddemo.home.adapter.HomeItemAdapter;
import com.lm.piccolo.Piccolo;
import com.lm.piccolo.view.ConductorForAdapter;

public class HomeFragment extends Fragment {
    private HomeFragmentViewModel viewModel;
    private HomeItemAdapter homeItemAdapter;
    private FragmentHomeBinding binding;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_home , container , false);
        viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected void initView(){
        binding.homeSwipeRefresh.setOnRefreshListener(() -> {
            viewModel.getRepository().refresh();//刷新，重新请求一遍
        });
        homeItemAdapter = new HomeItemAdapter(getContext() , viewModel.getRepository().getHomeBean() , getViewLifecycleOwner());
        viewModel.setHomeItemAdapter(homeItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvHome.setLayoutManager(layoutManager);
        //加载动画
        ConductorForAdapter conductorForAdapter = Piccolo.createForList(binding.rvHome);
        viewModel.getRepository().refresh();//发送请求
        //当结束加载后调用
        viewModel.getRepository().getIsLoadingSuccess().observe(requireActivity(), aBoolean -> {
            if (!aBoolean){
                conductorForAdapter
                        .items(new int[]{R.layout.item_home_banner , R.layout.item_home , R.layout.item_home})
                        .visible(true)
                        .adapter(homeItemAdapter)//结束加载后的Adapter
                        .play();
            }else {
                homeItemAdapter.setHomeBean(viewModel.getRepository().getHomeBean());//将item的数据源传递给adapter
                homeItemAdapter.setBannerBean(viewModel.getRepository().getBannerBean());//给adapter设置banner的数据源
                conductorForAdapter.visible(false)
                        .adapter(homeItemAdapter)
                        .play();
                binding.homeSwipeRefresh.setRefreshing(false);
            }
        });
    }


}