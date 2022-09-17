package com.me.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.me.sample.R;
import com.me.sample.application.BaseApplication;
import com.me.sample.network.utils.KLog;

import io.reactivex.annotations.Nullable;

/**
 * 自定义View
 * @author llw
 * @description CustomImageVIew
 */
public class CustomImageView extends ShapeableImageView {

    private static final RequestOptions OPTIONS = new RequestOptions()
        .placeholder(R.drawable.wallpaper_bg)// 图片加载出来前，显示的图片
        .fallback(R.drawable.wallpaper_bg)   // url为空的时候,显示的图片
        .error(R.mipmap.ic_loading_failed)   // 图片加载失败后，显示的图片
        .diskCacheStrategy(DiskCacheStrategy.ALL) // 不做磁盘缓存
        .skipMemoryCache(false);

    private static final RequestOptions OPTIONS_LOCAL = new RequestOptions()
        .placeholder(R.drawable.logo) // 图片加载出来前，显示的图片
        .fallback(R.drawable.logo)    // url为空的时候,显示的图片
        .error(R.mipmap.ic_loading_failed) // 图片加载失败后，显示的图片
        .diskCacheStrategy(DiskCacheStrategy.ALL) // 不做磁盘缓存
        .skipMemoryCache(false);

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    
    /**
     * 员工头像  因为拿到的url是完整的，因此不需要对url作任何的改动
     * @param imageView 图片视图
     * @param url 网络url
     */
    @BindingAdapter(value = {"imgUrl"}, requireAll = false)
        public static void setImgUrl(ImageView imageView, String url) {
        KLog.d(url);
        Glide.with(BaseApplication.getContext()).load(url).into(imageView);
    }
}