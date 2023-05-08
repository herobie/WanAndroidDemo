package com.example.wanandroiddemo.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.home.HomeFragmentViewModel;
import com.example.wanandroiddemo.home.bean.BannerBean;
import com.example.wanandroiddemo.home.bean.HomeBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {
    private Context context;
//    private List<HomeBean> homeBeans;
    private HomeBean homeBean;
    private BannerBean bannerBean;
    private LifecycleOwner lifecycleOwner;
    public HomeItemAdapter(Context context, HomeBean homeBean , LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.homeBean = homeBean;
//        this.homeFragmentViewModel = homeFragmentViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setBannerBean(BannerBean bannerBean) {
        this.bannerBean = bannerBean;
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
                holder.banner.setAdapter(new BannerImageAdapter<BannerBean.Data>(bannerBean.getData()) {
                    @Override
                    public void onBindView(BannerImageHolder holder, BannerBean.Data data, int position, int size) {
                        Glide.with(context)
                                .load(data.getImagePath())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .placeholder(R.drawable.ic_baseline_photo_24)
                                .error(R.drawable.ic_baseline_error_24)
                                .into(holder.imageView);
                    }
                });
//                holder.banner.setAdapter(bannerAdapter)
//                        .addBannerLifecycleObserver(lifecycleOwner)
//                        .setIndicator(new CircleIndicator(context))
//                        .setOnBannerListener(new OnBannerListener() {
//                            @Override
//                            public void OnBannerClick(Object data, int position) {
//
//                            }
//                        });
            }catch (Exception e){
                Log.w(Constant.TAG , e.getMessage());
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
