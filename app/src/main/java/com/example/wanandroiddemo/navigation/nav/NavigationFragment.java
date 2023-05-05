package com.example.wanandroiddemo.navigation.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.R;
import com.google.android.flexbox.FlexboxLayoutManager;


public class NavigationFragment extends Fragment {
    private RecyclerView navigation_rv_shortcut , navigation_rv;
    private NavigationViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected void initView(){
        viewModel = new ViewModelProvider(this).get(NavigationViewModel.class);
        navigation_rv = getView().findViewById(R.id.navigation_rv);
        navigation_rv_shortcut = getView().findViewById(R.id.navigation_rv_shortcut);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());//左边快捷栏的
        FlexboxLayoutManager flexLayoutManager = new FlexboxLayoutManager(getContext());//右边导航
        navigation_rv_shortcut.setLayoutManager(layoutManager);
        navigation_rv.setLayoutManager(flexLayoutManager);
        viewModel.getIsLoadingSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    viewModel.getShortcutAdapter()
                            .setShortcuts(viewModel.generateShortCuts(viewModel.getNavigationBean()));
                    navigation_rv_shortcut.setAdapter(viewModel.getShortcutAdapter());
                    viewModel.getNavigationAdapter()
                            .setArticles(viewModel.getNavigationBean().getData().get(viewModel.getCurrentChapter().getValue()).getArticles());
                    navigation_rv.setAdapter(viewModel.getNavigationAdapter());
                }
            }
        });
        viewModel.getCurrentChapter().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (viewModel.getNavigationBean() != null){
                    viewModel.getNavigationAdapter()
                            .setArticles(viewModel.getNavigationBean().getData().get(integer).getArticles());
                    navigation_rv.setAdapter(viewModel.getNavigationAdapter());
                }
            }
        });
        viewModel.refresh();
    }


}