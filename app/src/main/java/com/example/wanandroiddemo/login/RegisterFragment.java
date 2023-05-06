package com.example.wanandroiddemo.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroiddemo.MainActivityViewModel;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment implements View.OnClickListener , TextWatcher {
    private FragmentRegisterBinding binding;
    private MainActivityViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView(){
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        binding.registerConfirm.setOnClickListener(this);
        binding.registerCancel.setOnClickListener(this);
        binding.registerPassword.addTextChangedListener(this);
        binding.registerRepassword.addTextChangedListener(this);
        binding.registerUsername.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_confirm:
                boolean isInfoComplete = true;
                if (binding.registerRepassword.getText().toString().isEmpty()){
                    isInfoComplete = false;
                    binding.registerWarning.setText("请再次输入密码!");
                }else {
                    if (!binding.registerRepassword.getText().toString().equals(binding.registerPassword.getText().toString())){
                        isInfoComplete = false;
                        binding.registerWarning.setText("两次输入密码不一致");
                    }
                }
                if (binding.registerPassword.getText().toString().isEmpty()){
                    isInfoComplete = false;
                    binding.registerWarning.setText("密码不能为空");
                }
                if (binding.registerUsername.getText().toString().isEmpty()){
                    isInfoComplete = false;
                    binding.registerWarning.setText("用户名不能为空");
                }
                if (isInfoComplete){
                    viewModel.sendLoginRequest(viewModel.generateRequestBody(false));
                }
                break;
            case R.id.register_cancel:
                Intent toLogin = new Intent("toRegister");
                toLogin.putExtra("changeCurrentItem" , 0);
                getContext().sendBroadcast(toLogin);
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

    }
}