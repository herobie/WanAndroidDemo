package com.example.wanandroiddemo.collections;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.collections.article.ArticleViewModel;
import com.example.wanandroiddemo.databinding.FragmentCollectionsBaseBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CollectionsBaseFragment extends Fragment {
    private CollectionsBaseFragmentViewModel viewModel;
    private ArticleViewModel articleViewModel;
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
        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        CollectionsBaseAdapter collectionsBaseAdapter = new CollectionsBaseAdapter(getActivity() , viewModel.getFragments());
        TabLayoutMediator mediator = new TabLayoutMediator(binding.collectionsTab, binding.collectionsViewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("收藏文章");
                }else if (position == 1){
                    tab.setText("收藏网站");
                }
            }
        });
        articleViewModel.getIsSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && aBoolean && articleViewModel.getRepository().getCollectedArticles().getErrorCode() == -1001){
                    binding.collectionWarning.setVisibility(View.VISIBLE);
                }else {
                    binding.collectionWarning.setVisibility(View.GONE);
                    binding.collectionsViewPage.setAdapter(collectionsBaseAdapter);
                    if (!mediator.isAttached()){
                        mediator.attach();
                    }
                }
            }
        });
        articleViewModel.getRepository().requestCollectedArticle();
//        binding.collectionWarning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(Constant.TAG , "onClicked");
//                //点击跳转登录界面
//                Intent intent = new Intent("MainReceiver");
//                intent.putExtra("MainIntent","toLogin");
//                getContext().getApplicationContext().sendBroadcast(intent);
//            }
//        });
    }
}