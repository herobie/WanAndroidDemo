package com.example.wanandroiddemo.collections.article;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentArticleBinding;
import com.lm.piccolo.Piccolo;
import com.lm.piccolo.view.ConductorForAdapter;

/**
 * 收藏的网址
 */
public class ArticleFragment extends Fragment {
    private FragmentArticleBinding binding;
    private ArticleViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_article, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        binding.artSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.artSwipeRefresh.setRefreshing(true);
                viewModel.getRepository().requestCollectedArticle();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        binding.artRv.setLayoutManager(layoutManager);
        binding.artRv.addItemDecoration(new DividerItemDecoration(getContext().getApplicationContext() , DividerItemDecoration.VERTICAL));
        initLoadingObserver();
        viewModel.getRepository().requestCollectedArticle();
        viewModel.getNewArticleDialog(getContext()).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (viewModel.getArticleAdapter() != null){
                    viewModel.getArticleAdapter().notifyDataSetChanged();
                }
            }
        });
        binding.artAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getNewArticleDialog(getContext()).show();
            }
        });
    }

    protected void initLoadingObserver(){
        ConductorForAdapter conductorForAdapter = Piccolo.createForList(binding.artRv);
        viewModel.getIsSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {//是否加载完毕，必须先登录
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean){
                    conductorForAdapter
                            .items(new int[]{R.layout.item_project , R.layout.item_project , R.layout.item_project , R.layout.item_project})
                            .visible(true)
                            .adapter(viewModel.getArticleAdapter())//结束加载后的Adapter
                            .play();
                }else {
                    if (viewModel.getRepository().getCollectedArticles().getData() != null){//没登录时不会有后面这部分数据
                        viewModel.getArticleAdapter()
                                .setArticles(viewModel.getRepository().getCollectedArticles().getData().getDatas());
                    }
                    conductorForAdapter.visible(false)
                            .adapter(viewModel.getArticleAdapter())
                            .play();
                    if (binding.artSwipeRefresh.isRefreshing()){
                        binding.artSwipeRefresh.setRefreshing(false);
                    }
                }
            }
        });
        viewModel.getIsAdded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && viewModel.getArticleAdapter() != null){
                    viewModel.getArticleAdapter().notifyDataSetChanged();
                    viewModel.getNewArticleDialog(getContext()).dismiss();
                }
            }
        });
    }
}