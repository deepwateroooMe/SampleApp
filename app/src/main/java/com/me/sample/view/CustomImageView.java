package com.me.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.me.sample.BaseApplication;
import com.me.sample.network.utils.KLog;

import io.reactivex.annotations.Nullable;

// 这里要思考一个问题，那就是图片能不能通过DataBinding的方式进行数据绑定，
// 是可以的，不过需要我们自定义一个ImageView，用于绑定网络地址
public class CustomImageView extends AppCompatImageView {
    
    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 员工头像：url地址是完整的，可能需要裁剪
     * @param imageView 图片视图
     * @param url 网络url
     */
    @BindingAdapter(value = {"imgUrl"}, requireAll = false)
        public static void setImgUrl(ImageView imageView, String url) {
        KLog.d(url);
// 没懂：这里没弄明白Glide加载网络图片的原理，流程，以及缓存与否相关原理等的逻辑；明天补看一下        
        Glide.with(BaseApplication.getContext()).load(url).into(imageView);
    }

    /**
     * 普通网络地址图片
     * @param imageView 图片视图
     * @param url 网络url
     */
    @BindingAdapter(value = {"networkUrl"}, requireAll = false)
        public static void setNetworkUrl(ImageView imageView, String url) {
        Glide.with(BaseApplication.getContext()).load(url).into(imageView);
    }
}
