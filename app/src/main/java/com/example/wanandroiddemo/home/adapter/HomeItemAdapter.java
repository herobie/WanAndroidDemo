package com.example.wanandroiddemo.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.home.HomeFragmentViewModel;
import com.example.wanandroiddemo.home.bean.HomeBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {
    private Context context;
//    private List<HomeBean> homeBeans;
    private HomeBean homeBean;
    private HomeFragmentViewModel homeFragmentViewModel;
    private LifecycleOwner lifecycleOwner;
    public HomeItemAdapter(Context context, HomeBean homeBean , HomeFragmentViewModel homeFragmentViewModel , LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.homeBean = homeBean;
        this.homeFragmentViewModel = homeFragmentViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent , false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_banner, parent , false);
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //position = 0 是Banner，之后才是各个item
        if (position > 0){
            position += -1;
            holder.item_home_title.setText(homeBean.getDatas().get(position).getTitle());
            String author = homeBean.getDatas().get(position).getAuthor();
            String shareUser = homeBean.getDatas().get(position).getShareUser();
            if (shareUser.isEmpty()){//判空，判断是分享还是原创
                holder.item_author.setText("作者:" + author);
            }else if(author.isEmpty()){
                holder.item_author.setText("分享人:" + shareUser);
            }else {
                holder.item_author.setText("作者:未知");
            }
            String niceDate = homeBean.getDatas().get(position).getNiceDate();
            String niceShareDate = homeBean.getDatas().get(position).getNiceShareDate();
            if (niceDate == null){
                holder.item_update.setText("分享时间:" + niceShareDate);
            }else if (niceShareDate == null){
                holder.item_update.setText("更新时间:" + niceDate);
            }else {
                holder.item_update.setText("更新时间:未知");
            }
            holder.item_chapter.setText(homeBean.getDatas().get(position).getSuperChapterName());
            holder.item_update.setText(homeBean.getDatas().get(position).getNiceDate());
        }else if (position == 0){
            try{
                holder.banner.setAdapter(homeFragmentViewModel.getHomeBannerAdapter())
                        .addBannerLifecycleObserver(lifecycleOwner)
                        .setIndicator(new CircleIndicator(context))
                        .setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(Object data, int position) {

                            }
                        });
            }catch (Exception e){
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //Banner在最上面
        int viewType = 0;
        if (position == 0){
            viewType = 1;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return homeBean.getDatas().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView item_home_view;
        private TextView item_home_title , item_author , item_update , item_chapter;
        private ImageButton item_star;
        private Banner banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_home_view = itemView.findViewById(R.id.item_home_view);
            item_home_title = itemView.findViewById(R.id.item_home_title);
            item_author = itemView.findViewById(R.id.item_author);
            item_update = itemView.findViewById(R.id.item_update);
            item_chapter = itemView.findViewById(R.id.item_chapter);
            item_star = itemView.findViewById(R.id.item_star);
            banner = itemView.findViewById(R.id.banner_home);
        }
    }

    public void setHomeBean(HomeBean homeBean) {
        this.homeBean = homeBean;
    }

}
