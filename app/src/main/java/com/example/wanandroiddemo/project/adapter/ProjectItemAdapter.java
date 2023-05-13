package com.example.wanandroiddemo.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.ItemProjectBinding;
import com.example.wanandroiddemo.project.ProjectFragmentViewModel;
import com.example.wanandroiddemo.project.bean.ChapterBean;

import java.util.List;

public class ProjectItemAdapter extends RecyclerView.Adapter<ProjectItemAdapter.ViewHolder> {
    private List<ChapterBean.ProjectData> chapters;
    private final Context context;
    private ItemProjectBinding binding;
    private final ProjectFragmentViewModel viewModel;
    public ProjectItemAdapter(Context context , ProjectFragmentViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setChapters(List<ChapterBean.ProjectData> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_project , parent , false);
        return new ViewHolder(binding.getRoot());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setProjectViewModel(viewModel);
        binding.setChapterData(viewModel.getChapterBean().getDatas().get(position));
        Glide.with(context).load(chapters.get(position).getEnvelopePic())
                .placeholder(R.drawable.ic_baseline_photo_24)
                .error(R.drawable.ic_baseline_photo_24)
                .into(binding.projectPicture);
        if (chapters.get(holder.getAdapterPosition()).isCollect()){
            binding.projectStar.setImageResource(R.drawable.ic_baseline_star_24_yellow);
        }else {
            binding.projectStar.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        binding.projectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        binding.projectStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chapters.get(holder.getAdapterPosition()).isCollect()){//取消收藏
                    viewModel.getRepository().requestUncollect(chapters.get(holder.getAdapterPosition()).getId());
                    binding.projectStar.setImageResource(R.drawable.ic_baseline_star_border_24);
                }else {//收藏
                    viewModel.getRepository().requestCollect(chapters.get(holder.getAdapterPosition()).getId());
                    binding.projectStar.setImageResource(R.drawable.ic_baseline_star_24_yellow);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (chapters != null){
            size = chapters.size();
        }
        return size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
