package com.example.wanandroiddemo.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.MainActivityViewModel;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment implements View.OnClickListener , TextWatcher {
    private FragmentLoginBinding binding;
    private MainActivityViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView(){
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        binding.loginLogin.setOnClickListener(this);
        binding.loginUsername.addTextChangedListener(this);
        binding.loginPassword.addTextChangedListener(this);
        binding.loginCancel.setOnClickListener(this);
        binding.loginToRegister.setOnClickListener(this);
        viewModel.getIsLogin().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null && !aBoolean){
                    binding.loginWarning.setText(viewModel.getMainRepository().getLoginBean().getErrorMsg());
                }else if (aBoolean != null && aBoolean){
                    getActivity().onBackPressed();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.login_login:
                if (!binding.loginUsername.getText().toString().isEmpty()){
                    if (!binding.loginPassword.getText().toString().isEmpty()){
                        viewModel.getMainRepository().sendLoginRequest(viewModel.getMainRepository().generateRequestBody(true));
                    }else {
                        binding.loginWarning.setText("密码不能为空");
                    }
                }else {
                    binding.loginWarning.setText("用户名不能为空");
                }
                break;
            case R.id.login_toRegister:
                Intent toRegister = new Intent("toRegister");
                toRegister.putExtra("changeCurrentItem" , 1);
                getContext().sendBroadcast(toRegister);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        viewModel.getMainRepository().setUsername(binding.loginUsername.getText().toString());//保存输入
        viewModel.getMainRepository().setPassword(binding.loginPassword.getText().toString());
    }
}