//package com.example.wanandroiddemo.home.adapter;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
//import com.bumptech.glide.request.RequestOptions;
//import com.example.wanandroiddemo.Constant;
//import com.example.wanandroiddemo.R;
//import com.example.wanandroiddemo.home.bean.BannerBean;
//import com.youth.banner.adapter.BannerAdapter;
//
//import java.util.List;
//
//public class HomeBannerAdapter extends BannerAdapter<BannerBean.Data , HomeBannerAdapter.BannerViewHolder> {
//    private Context context;
//    private List<BannerBean.Data> datas;
//    public HomeBannerAdapter(List<BannerBean.Data> datas , Context context) {
//        super(datas);
//        this.datas = datas;
//        this.context = context;
//        Log.w("MainActivity" , "BannerAdapter:" + String.valueOf(datas.size()));
//    }
//
//    @Override
//    public void setDatas(List<BannerBean.Data> datas) {
//        this.datas = datas;
//    }
//
//    @Override
//    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
//        ImageView imageView = new ImageView(parent.getContext());
//        //注意，必须设置为match_parent，这个是viewpager2强制要求的
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Log.w("MainActivity" , "OnCreateHolder");
//        return new BannerViewHolder(imageView);
//    }
//
//    @Override
//    public void onBindView(BannerViewHolder holder, BannerBean.Data data, int position, int size) {
//        Log.w("MainActivity" , datas.get(position).getUrl());
//        Glide.with(context)
//                .load(datas.get(position).getImagePath())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                .placeholder(R.drawable.ic_baseline_photo_24)
//                .error(R.drawable.ic_baseline_error_24)
//                .into(holder.imageView);
//    }
//
//    public List<BannerBean.Data> getDatas() {
//        return datas;
//    }
//
//    class BannerViewHolder extends RecyclerView.ViewHolder {
//        private ImageView imageView;
//        public BannerViewHolder(@NonNull ImageView view) {
//            super(view);
//            this.imageView = view;
//        }
//    }
//}
