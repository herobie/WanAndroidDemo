package com.example.wanandroiddemo.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.MainActivityViewModel;
import com.example.wanandroiddemo.R;

import java.util.List;


public class BaseLoginFragment extends Fragment {
    private ViewPager2 login_viewPage;
    private BaseViewModel viewModel;
    private BaseReceiver baseReceiver;
    private IntentFilter intentFilter;
    public static BaseLoginFragment getInstance(String param){
        BaseLoginFragment baseLoginFragment = new BaseLoginFragment();
        Bundle args = new Bundle();
        args.putString(Constant.ARG_PARAM1, param);
        baseLoginFragment.setArguments(args);
        return baseLoginFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login_viewPage = view.findViewById(R.id.login_viewPage);
        viewModel = new ViewModelProvider(this).get(BaseViewModel.class);
        baseReceiver = new BaseReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("toRegister");
        getContext().getApplicationContext().registerReceiver(baseReceiver , intentFilter);
        login_viewPage.setAdapter(new ScreenSlidePagerAdapter(getActivity() , viewModel.getFragments()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().getApplicationContext().unregisterReceiver(baseReceiver);
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private List<Fragment> fragments;
        public ScreenSlidePagerAdapter(FragmentActivity fa , List<Fragment> fragments) {
            super(fa);
            this.fragments = fragments;
        }

        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }

    private class BaseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("changeCurrentItem" , 0);
            login_viewPage.setCurrentItem(position);//切换至注册页面
        }
    }
}