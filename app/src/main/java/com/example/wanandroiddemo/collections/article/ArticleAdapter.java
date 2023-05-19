package com.example.wanandroiddemo.collections.article;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.collections.bean.CollectedArticles;
import com.example.wanandroiddemo.databinding.ItemArticlesBinding;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<CollectedArticles.Data.CollectedData> articles;
    private ArticleViewModel viewModel;
    private ItemArticlesBinding binding;
    private Context context;

    public ArticleAdapter(List<CollectedArticles.Data.CollectedData> articles , ArticleViewModel viewModel) {
        this.articles = articles;
        this.viewModel = viewModel;
    }

    public ArticleAdapter(ArticleViewModel viewModel , Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    public void setArticles(List<CollectedArticles.Data.CollectedData> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_articles , parent , false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setViewModel(viewModel);
        binding.setArticle(articles.get(position));
        //取消收藏
        binding.artCollect.setOnClickListener(v -> {
            viewModel.getRepository().requestRemoveArticle(position);//请求取消收藏
            viewModel.getRepository()
                    .deleteArticle(articles.get(position).getId());//数据库删除缓存
            articles.remove(position);//集合中移除
            notifyDataSetChanged();
        });
        binding.artView.setOnClickListener(v -> {
            Intent intent = new Intent("skipToWebsite");
            intent.putExtra("url" , viewModel.getRepository().getCollectedArticles().getData().getDatas().get(position).getLink());
            context.sendBroadcast(intent);
        });
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
