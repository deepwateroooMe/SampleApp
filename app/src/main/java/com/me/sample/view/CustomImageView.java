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

public class CustomImageView extends ShapeableImageView {

    private static final RequestOptions OPTIONS = new RequestOptions()
        .placeholder(R.drawable.wallpaper_bg)
        .fallback(R.drawable.wallpaper_bg)   
        .error(R.mipmap.ic_loading_failed)   
        .diskCacheStrategy(DiskCacheStrategy.ALL) 
        .skipMemoryCache(false);

    private static final RequestOptions OPTIONS_LOCAL = new RequestOptions()
        .placeholder(R.drawable.logo)
        .fallback(R.drawable.logo)   
        .error(R.mipmap.ic_loading_failed) 
        .diskCacheStrategy(DiskCacheStrategy.ALL) 
        .skipMemoryCache(false);

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    
    @BindingAdapter(value = {"imgUrl"}, requireAll = false)
        public static void setImgUrl(ImageView imageView, String url) {
        KLog.d(url);
        Glide.with(BaseApplication.getContext()).load(url).into(imageView);
    }
}