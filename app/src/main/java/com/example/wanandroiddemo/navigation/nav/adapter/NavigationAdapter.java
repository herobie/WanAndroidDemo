package com.example.wanandroiddemo.navigation.nav.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.navigation.nav.bean.NavigationBean;

import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {
    private List<NavigationBean.NavData.Article> articles;

    public NavigationAdapter (){

    }

    public void setArticles(List<NavigationBean.NavData.Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation, parent , false);;
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.navigation_item.setText(articles.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (articles != null){
            size = articles.size();
        }
        return size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView navigation_item;
        private LinearLayout item_navigation_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            navigation_item = itemView.findViewById(R.id.navigation_item);
            item_navigation_view = itemView.findViewById(R.id.item_navigation_view);
        }
    }
}
