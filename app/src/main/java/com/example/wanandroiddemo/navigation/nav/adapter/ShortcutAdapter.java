package com.example.wanandroiddemo.navigation.nav.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.navigation.nav.NavigationViewModel;

import java.util.List;

public class ShortcutAdapter extends RecyclerView.Adapter<ShortcutAdapter.ViewHolder> {
    private List<String> shortcuts;
    private NavigationViewModel navigationViewModel;
    public ShortcutAdapter(NavigationViewModel navigationViewModel) {
        this.navigationViewModel = navigationViewModel;
    }

    public void setShortcuts(List<String> shortcuts) {
        this.shortcuts = shortcuts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shortcuts, parent , false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.shortcuts_title.setText(shortcuts.get(position));
        if (navigationViewModel.getCurrentChapter().getValue() == position){
            holder.shortcuts_title.setActivated(true);
        }else {
            holder.shortcuts_title.setActivated(false);
        }
        holder.shortcuts_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationViewModel.getCurrentChapter().setValue(position);
                holder.shortcuts_title.setActivated(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shortcuts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private Button shortcuts_title;
        private LinearLayout shortcuts_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shortcuts_title = itemView.findViewById(R.id.shortcuts_title);
            shortcuts_view = itemView.findViewById(R.id.shortcuts_view);
        }
    }
}
