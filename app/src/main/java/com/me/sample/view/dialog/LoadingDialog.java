package com.me.sample.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.sample.R;

public class LoadingDialog extends Dialog {

    TextView tvLoadingTx;
    ImageView ivLoading;

    public LoadingDialog(Context context) {
        this(context, R.style.loading_dialog, "Loading...");

    }

    public LoadingDialog(Context context, String string) {
        this(context, R.style.loading_dialog, string);
    }

    public LoadingDialog(Context context, boolean close) {
        this(context, R.style.loading_dialog, "Loading...",close);
    }

    protected LoadingDialog(Context context, int theme, String string) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.loading_dialog);
        tvLoadingTx = findViewById(R.id.tv_loading_tx);
        tvLoadingTx.setText(string);
        ivLoading = findViewById(R.id.iv_loading);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, R.anim.loading_animation);
        ivLoading.startAnimation(hyperspaceJumpAnimation);

        getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().dimAmount = 0.5f;


    }

    protected LoadingDialog(Context context, int theme, String string, boolean isOtherOnClickClose) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.loading_dialog);
        tvLoadingTx = findViewById(R.id.tv_loading_tx);
        tvLoadingTx.setText(string);
        ivLoading = findViewById(R.id.iv_loading);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
            context, R.anim.loading_animation);
        ivLoading.startAnimation(hyperspaceJumpAnimation);

        getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().dimAmount = 0.5f;
    }

    @Override
        public void dismiss() {
        super.dismiss();
    }
}

