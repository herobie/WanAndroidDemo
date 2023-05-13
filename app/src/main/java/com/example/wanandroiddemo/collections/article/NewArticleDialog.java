package com.example.wanandroiddemo.collections.article;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.DialogAddLinkBinding;

public class NewArticleDialog extends Dialog implements View.OnClickListener{
    private DialogAddLinkBinding binding;
    private ArticleViewModel viewModel;
    private ArticleDialogReceiver articleDialogReceiver;

    public NewArticleDialog(@NonNull Context context, ArticleViewModel viewModel) {
        super(context, R.style.DialogBaseStyle);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()) , R.layout.dialog_add_link , null , false);
        setContentView(binding.getRoot());
        this.viewModel = viewModel;
        initView();
        initBroadcast();
    }


    protected void initView(){
        binding.addConfirm.setOnClickListener(this);
        binding.addCancel.setOnClickListener(this);
    }

    protected void initBroadcast(){
        articleDialogReceiver = new ArticleDialogReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ArticleDialog");
        getContext().registerReceiver(articleDialogReceiver , intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_cancel:
                dismiss();
                break;
            case R.id.add_confirm:
                if (inputCheck()){//如果通过则清空数据
                    binding.addWarning.setText("");
                    binding.addTitle.setText("");
                    binding.addLink.setText("");
                    binding.addAuthor.setText("");
                    dismiss();
                }
                break;
        }
    }

    /**
     * 检查输入内容是否完整
     * @return
     */
    protected boolean inputCheck(){
        boolean isPass = false;
        String title = binding.addTitle.getText().toString();
        String link = binding.addLink.getText().toString();
        if (!title.isEmpty()){
            if (!link.isEmpty()){
                isPass = true;
                String author = binding.addAuthor.getText().toString();
                viewModel.getRepository().queryLink(title , author , link);
            }else {
                binding.addWarning.setText("请输入链接!");
            }
        }else {
            binding.addWarning.setText("请输入标题!");
        }
        return isPass;
    }

    protected class ArticleDialogReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            binding.addWarning.setText("文章已存在!");
        }
    }
}
