package com.example.wanandroiddemo.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.project.bean.ChapterBean;

import java.util.List;

public class ProjectItemAdapter extends RecyclerView.Adapter<ProjectItemAdapter.ViewHolder> {
    private List<ChapterBean.ProjectData> chapters;
    private Context context;

    public ProjectItemAdapter(Context context) {
        this.context = context;
    }

    public void setChapters(List<ChapterBean.ProjectData> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent , false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.project_title.setText(chapters.get(position).getTitle());
        String author = chapters.get(position).getAuthor();
        String shareUser = chapters.get(position).getShareUser();
        if (author.isEmpty()){
            holder.project_author.setText("分享人:" + shareUser);
            holder.project_update.setText("分享时间:" + chapters.get(position).getNiceShareDate());
        }else if (shareUser.isEmpty()){
            holder.project_author.setText("分享人:" + author);
            holder.project_update.setText("上传时间:" + chapters.get(position).getNiceDate());
        }
        Glide.with(context).load(chapters.get(position).getEnvelopePic())
                .placeholder(R.drawable.ic_baseline_photo_24)
                .error(R.drawable.ic_baseline_photo_24)
                .into(holder.project_picture);
        holder.project_describe.setText(chapters.get(position).getDesc());
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
        private TextView project_title , project_describe , project_author , project_update;
        private ImageView project_picture;
        private ImageButton project_star;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project_title = itemView.findViewById(R.id.project_title);
            project_describe = itemView.findViewById(R.id.project_describe);
            project_author = itemView.findViewById(R.id.project_author);
            project_update = itemView.findViewById(R.id.project_update);
            project_picture = itemView.findViewById(R.id.project_picture);
            project_star = itemView.findViewById(R.id.project_star);
        }
    }
}
