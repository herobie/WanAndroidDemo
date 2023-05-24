package com.example.wanandroiddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wanandroiddemo.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private MainActivityViewModel mainActivityViewModel;
    private FragmentManager fragmentManager;
    private ActivityMainBinding binding;
    private MainReceiver mainReceiver;
    private SkipToWebsite skipToWebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        acquirePermissions();
        initView();
        initReceiver();
    }

    protected void initView(){
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.setLifecycleOwner(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        }
        binding.sideNavigation.setNavigationItemSelectedListener(this);
        View headerLayout = binding.sideNavigation.inflateHeaderView(R.layout.navigation_header);
        fragmentManager = getSupportFragmentManager();
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(mainActivityViewModel.getHomeFragment() ,false);
                    break;
                case R.id.project:
                    replaceFragment(mainActivityViewModel.getProjectFragment(),false);
                    break;
                case R.id.Navigation:
                    replaceFragment(mainActivityViewModel.getNavFragment(),false);
                    break;
                case R.id.collections:
                    replaceFragment(mainActivityViewModel.getCollectionsBaseFragment() , false);
                    break;
            }
            return true;
        });
//        mainActivityViewModel.autoLogin();
    }

    protected void initReceiver(){
        mainReceiver = new MainReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("MainReceiver");
        registerReceiver(mainReceiver , filter);
        skipToWebsite = new SkipToWebsite();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("skipToWebsite");
        registerReceiver(skipToWebsite , filter1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainReceiver);
        unregisterReceiver(skipToWebsite);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar , menu);
//        initSearch(menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                binding.mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

//    protected void initSearch(Menu menu) {
//        SearchView searchView;
//        MenuItem searchItem = menu.findItem(R.id.menu_search_view);
//        searchView = (SearchView) searchItem.getActionView();
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return true;
//            }
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return true;
//            }
//        });
//    }

    protected void replaceFragment(Fragment fragment , boolean isAddToBackStack){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragContainer, fragment);
//        if (!fragment.isAdded()){
//            transaction.hide(mainActivityViewModel.getCurrentFragment()).add(R.id.fragContainer, fragment);
//        }else {
//            transaction.hide(mainActivityViewModel.getCurrentFragment()).show(fragment);
//        }
        if (isAddToBackStack){//判断是否加入返回栈，一般是左侧滑动导航栏进入的页面需要
            transaction.addToBackStack(null);
        }
        transaction.commit();
//        mainActivityViewModel.setCurrentFragment(fragment);
    }

    protected void acquirePermissions(){
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 1);
        }
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , 1);
        }
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.INTERNET} , 1);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.login:
                replaceFragment(mainActivityViewModel.getBaseLoginFragment() , true);
                binding.mDrawerLayout.closeDrawers();
                break;
            case R.id.logout:
                mainActivityViewModel.getMainRepository().sendLogoutRequest();
                Toast.makeText(this, "退出成功!", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    protected class MainReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String function = intent.getStringExtra("MainIntent");
            switch (function){
                case "toLogin":
                    replaceFragment(mainActivityViewModel.getBaseLoginFragment() , true);
                    break;
            }
        }
    }

    protected class SkipToWebsite extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String url = intent.getStringExtra("url");
            replaceFragment(mainActivityViewModel.getWebFragment(url) , true);
        }
    }
}