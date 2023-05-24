package com.example.wanandroiddemo.collections.website;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.collections.bean.CollectedWebs;
import com.example.wanandroiddemo.databinding.ItemWebBinding;

import java.util.List;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.ViewHolder> {
    private List<CollectedWebs.WebDatas> data;
    private ItemWebBinding binding;
    private WebsiteViewModel websiteViewModel;
    private Context context;

    public WebAdapter(WebsiteViewModel websiteViewModel , Context context) {
        this.websiteViewModel = websiteViewModel;
        this.context = context;
    }

    public void setDatas(List<CollectedWebs.WebDatas> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_web , parent , false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setData(data.get(position));
        binding.itemWebView.setOnLongClickListener(v -> {//长按切换删除按钮显示状态
            if (binding.itemWebDelete.getVisibility() == View.VISIBLE){
                binding.itemWebDelete.setVisibility(View.GONE);
                Log.d(Constant.TAG, String.valueOf(position));
            }else {
                binding.itemWebDelete.setVisibility(View.VISIBLE);
            }
            return true;
        });
        //删除网址
        binding.itemWebDelete.setOnClickListener(v -> {
            websiteViewModel.getRepository()
                    .requestRemoveWeb(data.get(position).getId());
            data.remove(position);
            notifyDataSetChanged();
            binding.itemWebDelete.setVisibility(View.GONE);
        });
        //点击跳转
        binding.itemWebView.setOnClickListener(v -> {
            Intent intent = new Intent("skipToWebsite");
            intent.putExtra("url" , websiteViewModel.getRepository().getWeb().getData().get(position).getLink());
            context.sendBroadcast(intent);
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (data != null){
            size = data.size();
        }
        return size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
