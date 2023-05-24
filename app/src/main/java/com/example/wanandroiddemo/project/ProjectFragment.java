package com.example.wanandroiddemo.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.project.adapter.ProjectItemAdapter;
import com.google.android.material.tabs.TabLayout;
import com.lm.piccolo.Piccolo;
import com.lm.piccolo.view.ConductorForAdapter;

import java.util.Objects;

public class ProjectFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    private ProjectFragmentViewModel viewModel;
    private AlertDialog alertDialog;
    private TabLayout project_tab;
    private RecyclerView project_rv;
    private SwipeRefreshLayout project_swipe_refresh;
    private ProjectItemAdapter projectItemAdapter;
    public static ProjectFragment getInstance(String param){
        ProjectFragment projectFragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(Constant.ARG_PARAM1, param);
        projectFragment.setArguments(args);
        return projectFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected void initView(){
        viewModel = new ViewModelProvider(this).get(ProjectFragmentViewModel.class);
        project_swipe_refresh = getView().findViewById(R.id.project_swipe_refresh);
        project_tab = getView().findViewById(R.id.project_tab);
        project_tab.addOnTabSelectedListener(this);
        project_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Boolean.TRUE.equals(viewModel.getIsIDAcquired().getValue())){
                    viewModel.getRepository().requestItemData(viewModel.getLastPosition().getValue());
                    project_swipe_refresh.setRefreshing(true);
                }
            }
        });
        project_rv = getView().findViewById(R.id.project_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        project_rv.setLayoutManager(layoutManager);
        projectItemAdapter = new ProjectItemAdapter(getContext() , viewModel);
        ConductorForAdapter conductorForAdapter = Piccolo.createForList(project_rv);
        viewModel.getIsIDAcquired().setValue(false);
        //观察标签是否加载完毕
        viewModel.getIsIDAcquired().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    for (int i = 0 ; i < viewModel.getRepository().getIdBean().getData().size() ; i++){
                        project_tab.addTab(project_tab.newTab().setText(viewModel.getRepository().getIdBean().getData().get(i).getName()));
                    }
                }
            }
        });
        //观察页面是否加载完毕
        viewModel.getIsItemParseFinished().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //获取id之后才能加载item页面
                if (Boolean.TRUE.equals(viewModel.getIsIDAcquired().getValue())){
                    if (!aBoolean){
                        conductorForAdapter
                                .items(new int[]{R.layout.item_project , R.layout.item_project , R.layout.item_project})
                                .visible(true)
                                .adapter(projectItemAdapter)//结束加载后的Adapter
                                .play();
                    }else {//获取id后调用这个
                        projectItemAdapter.setChapters(viewModel.getRepository().getChapterBean().getDatas());
                        conductorForAdapter.visible(false)
                                .adapter(projectItemAdapter)
                                .play();
                        if (project_swipe_refresh.isRefreshing()){
                            project_swipe_refresh.setRefreshing(false);
                        }
                    }
                }
            }
        });
        viewModel.getIsFailed().observe(getViewLifecycleOwner(), new Observer<Boolean>() {//如果请求失败调用此方法，获取item对应的id
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    //找到和缓存页面相符合的tab并选中
                    for (int i = 0 ; i < viewModel.getRepository().getIdBean().getData().size();i++){
                        project_tab.addTab(project_tab.newTab().setText(viewModel.getRepository().getIdBean().getData().get(i).getName()));
                        for (int j = 0 ; j < viewModel.getRepository().getChapterBean().getDatas().size() ; j++){
                            if (viewModel.getRepository().getChapterBean().getDatas().get(j).getChapterId() == viewModel.getRepository().getIdBean().getData().get(i).getId()){
                                Objects.requireNonNull(project_tab.getTabAt(i)).select();
                                break;
                            }
                        }
                    }
                    projectItemAdapter.setChapters(viewModel.getRepository().getChapterBean().getDatas());
                    project_rv.setAdapter(projectItemAdapter);
                    if (project_swipe_refresh.isRefreshing()){
                        project_swipe_refresh.setRefreshing(false);
                    }
                }
            }
        });
//        viewModel.acquireID();
        viewModel.getRepository().requestID();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        viewModel.acquireData(tab.getPosition());
        viewModel.getLastPosition().setValue(tab.getPosition());
        viewModel.getRepository().requestItemData(tab.getPosition());
        if (project_swipe_refresh.isRefreshing()){
            project_swipe_refresh.setRefreshing(false);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}