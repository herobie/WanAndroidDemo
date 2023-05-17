package com.example.wanandroiddemo.collections.website;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.DialogAddLinkBinding;

public class NewWebDialog extends Dialog implements View.OnClickListener{
    private DialogAddLinkBinding binding;
    private WebsiteViewModel viewModel;

    public NewWebDialog(@NonNull Context context , WebsiteViewModel viewModel) {
        super(context, R.style.DialogBaseStyle);
        this.viewModel = viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()) , R.layout.dialog_add_link , null , false);
        setContentView(binding.getRoot());
        initView();
    }

    private void initView(){
        binding.addAuthor.setVisibility(View.GONE);
        binding.addConfirm.setOnClickListener(this);
        binding.addCancel.setOnClickListener(this);
    }

    /**
     * 检查是否输入完整
     * @return
     */
    private boolean inputCheck(){
        boolean isPassed = false;
        if (!binding.addLink.getText().toString().isEmpty()){
            if (!binding.addTitle.getText().toString().isEmpty()){
                isPassed = true;
            }else {
                binding.addWarning.setText("请输入标题!");
            }
        }else {
            binding.addWarning.setText("请输入链接!");
        }
        return isPassed;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_cancel:
                dismiss();
                break;
            case R.id.add_confirm:
                if (inputCheck()){
                    viewModel.getRepository()
                            .requestNewWeb(binding.addTitle.getText().toString() , binding.addLink.getText().toString());
                    dismiss();
                }
                break;
        }
    }
}
