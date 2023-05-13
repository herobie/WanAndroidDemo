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
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wanandroiddemo.Constant;
import com.example.wanandroiddemo.R;
import com.example.wanandroiddemo.databinding.ItemHomeBinding;
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
    private ItemHomeBinding binding;
    private HomeFragmentViewModel viewModel;
    public HomeItemAdapter(Context context , HomeFragmentViewModel viewModel) {
        this.context = context;
        this.homeBean = homeBean;
        this.viewModel = viewModel;
        this.homeBean = viewModel.getRepository().getHomeBean();
//        this.homeFragmentViewModel = homeFragmentViewModel;
    }

    public void setBannerBean(BannerBean bannerBean) {
        this.bannerBean = bannerBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0){
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.item_home , parent , false);
            view = binding.getRoot();
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
            binding = DataBindingUtil.getBinding(holder.itemView);
            binding.setHomeData(homeBean.getDatas().get(position));
            boolean isCollect = homeBean.getDatas().get(position).isCollect();
            if (isCollect){
                binding.itemStar.setImageResource(R.drawable.ic_baseline_star_24_yellow);
            }else {
                binding.itemStar.setImageResource(R.drawable.ic_baseline_star_border_24);
            }
            int finalPosition = position;
            binding.itemStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCollect){
                        viewModel.getRepository().requestUncollect(homeBean.getDatas().get(finalPosition).getId());
                        binding.itemStar.setImageResource(R.drawable.ic_baseline_star_border_24);
                    }else {
                        viewModel.getRepository().requestCollect(homeBean.getDatas().get(finalPosition).getId());
                        binding.itemStar.setImageResource(R.drawable.ic_baseline_star_24_yellow);
                    }
                }
            });
        }else if (position == 0){
            initBanner(holder);
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

    protected void initBanner(ViewHolder holder){
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
        }catch (Exception e){
            Log.w(Constant.TAG , e.getMessage());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private Banner banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_home);
        }
    }

    public void setHomeBean(HomeBean homeBean) {
        this.homeBean = homeBean;
    }

}
