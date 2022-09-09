package com.me.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.me.sample.application.BaseApplication;
import com.me.sample.network.utils.KLog;

import io.reactivex.annotations.Nullable;

/**
 * 自定义View
 * @author llw
 * @description CustomImageVIew
 */
public class CustomImageView extends ShapeableImageView {

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