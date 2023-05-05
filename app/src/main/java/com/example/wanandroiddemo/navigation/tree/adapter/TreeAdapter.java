package com.example.wanandroiddemo.navigation.tree.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.ItemNavigationBinding;
import com.example.wanandroiddemo.navigation.tree.bean.TreeBean;

import java.util.List;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {
    private List<TreeBean.TreeData.Children> children;

    public TreeAdapter() {
    }

    public void setChildren(List<TreeBean.TreeData.Children> children) {
        this.children = children;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNavigationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_navigation , parent , false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemNavigationBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.navigationItem.setText(children.get(position).getName());
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (children != null){
            size = children.size();
        }
        return size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
