package com.example.wanandroiddemo.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentHomeBinding;
import com.example.wanandroiddemo.home.adapter.HomeBannerAdapter;
import com.example.wanandroiddemo.home.adapter.HomeItemAdapter;
import com.lm.piccolo.Piccolo;
import com.lm.piccolo.view.ConductorForAdapter;

public class HomeFragment extends Fragment {
    private HomeFragmentViewModel viewModel;
    private HomeItemAdapter homeItemAdapter;
    private HomeBannerAdapter homeBannerAdapter;
    private RecyclerView rv_home;
    private SwipeRefreshLayout home_swipe_refresh;
    private FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_home , container , false);
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected void initView(){
        home_swipe_refresh = getView().findViewById(R.id.home_swipe_refresh);
        rv_home = getView().findViewById(R.id.rv_home);
        home_swipe_refresh = getView().findViewById(R.id.home_swipe_refresh);
        home_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh(getContext());
            }
        });
        homeItemAdapter = new HomeItemAdapter(getContext() , viewModel.getHomeBean() , viewModel , getViewLifecycleOwner());
        viewModel.setHomeItemAdapter(homeItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_home.setLayoutManager(layoutManager);
        //加载动画
        ConductorForAdapter conductorForAdapter = Piccolo.createForList(rv_home);
        viewModel.refresh(getContext());
        viewModel.getIsLoadingFinished().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean){
                    conductorForAdapter
                            .items(new int[]{R.layout.item_home_banner , R.layout.item_home , R.layout.item_home})
                            .visible(true)
                            .adapter(homeItemAdapter)//结束加载后的Adapter
                            .play();
                }else {
                    conductorForAdapter.visible(false)
                            .adapter(homeItemAdapter)
                            .play();
                    home_swipe_refresh.setRefreshing(false);
                }
            }
        });
    }


}