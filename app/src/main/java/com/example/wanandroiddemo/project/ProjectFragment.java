package com.example.wanandroiddemo.project;

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

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.project.adapter.ProjectItemAdapter;
import com.google.android.material.tabs.TabLayout;
import com.lm.piccolo.Piccolo;
import com.lm.piccolo.view.ConductorForAdapter;

public class ProjectFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    private ProjectFragmentViewModel viewModel;
    private AlertDialog alertDialog;
    private TabLayout project_tab;
    private RecyclerView project_rv;
    private SwipeRefreshLayout project_swipe_refresh;
    private ProjectItemAdapter projectItemAdapter;
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
//                viewModel.acquireData(viewModel.getLastPosition());
                viewModel.getRepository().requestItemData(viewModel.getLastPosition().getValue());
                project_swipe_refresh.setRefreshing(true);
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
                    for (int i = 0 ; i < viewModel.getProjectIdList().size() ; i++){
                        project_tab.addTab(project_tab.newTab().setText(viewModel.getProjectIdList().get(i).getName()));
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
                        projectItemAdapter.setChapters(viewModel.getChapterBean().getDatas());
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
//        viewModel.acquireID();
        viewModel.getRepository().requestID();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        viewModel.acquireData(tab.getPosition());
        viewModel.getLastPosition().setValue(tab.getPosition());
        viewModel.getRepository().requestItemData(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}