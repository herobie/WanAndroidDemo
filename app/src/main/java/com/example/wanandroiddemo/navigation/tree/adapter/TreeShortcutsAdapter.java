package com.example.wanandroiddemo.navigation.tree.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.ItemNavigationBinding;
import com.example.wanandroiddemo.databinding.ItemShortcutsBinding;
import com.example.wanandroiddemo.navigation.tree.TreeViewModel;

import java.util.List;

public class TreeShortcutsAdapter extends RecyclerView.Adapter<TreeShortcutsAdapter.ViewHolder> {
    private List<String> shortcuts;
    private TreeViewModel treeViewModel;
    public TreeShortcutsAdapter() {
        treeViewModel = new TreeViewModel();
    }

    public void setShortcuts(List<String> shortcuts) {
        this.shortcuts = shortcuts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShortcutsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_shortcuts , parent , false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemShortcutsBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.shortcutsTitle.setText(shortcuts.get(position));
        binding.shortcutsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treeViewModel.getLastPosition().setValue(position);
                Log.d(Constant.TAG , "Clicked:" + String.valueOf(treeViewModel.getLastPosition().getValue()));
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (shortcuts != null){
            size = shortcuts.size();
        }
        return size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
