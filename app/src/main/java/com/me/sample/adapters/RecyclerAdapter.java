package com.me.sample.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.me.sample.R;
import com.me.sample.databinding.ItemEmpBinding;
import com.me.sample.model.EmployeeResponse;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 传递过来的数据
     */
    // private final List<WallPaperResponse.ResBean.VerticalBean> verticalBeans;
    private EmployeeResponse mEmpList;

    // private Context context;

    // public WallPaperAdapter(List<WallPaperResponse.ResBean.VerticalBean> verticalBeans) {
    //     this.verticalBeans = verticalBeans;
    // }
    public RecyclerAdapter(EmployeeResponse mEmpList) {
        this.mEmpList = mEmpList;
    }
    // public RecyclerAdapter(Context context, EmployeeResponse mEmpList) {
    //     this.context = context;
    //     this.mEmpList = mEmpList;
    // }
    

    @NonNull
        @Override
        public ViewHolderItemEmp onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ItemEmpBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_emp, parent, false);
        return new ViewHolderItemEmp(binding);   
    }
    @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ItemEmpBinding binding = ((ViewHolderItemEmp)holder).getBinding();
        binding.setEmp(mEmpList.getEmployees().get(position));
        binding.executePendingBindings();
        // // Set the image
        // RequestOptions defaultOptions = new RequestOptions()
        //     .error(R.drawable.ic_launcher_background);
        // Glide.with(context)
        //     .setDefaultRequestOptions(defaultOptions)
        //     .load(mEmpList.getEmployees().get(i).getImgUrlSmall())
        //     .into(((ViewHolder)viewHolder).mImage);
    }
    @Override
        public int getItemCount() {
        // if (mEmpList == null) return 0;
        return mEmpList.getEmployees().size();
    }

    // @NonNull
    //     @NotNull
    //     @Override
    //     public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    //     ItemWallPaperBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_wall_paper, parent, false);
    //     return new ViewHolderWallPaper(binding);
    // }
    // @Override
    //     public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
    //     ItemWallPaperBinding binding = ((ViewHolderWallPaper) holder).getBinding();
    //     binding.setWallPaper(verticalBeans.get(position));
    //     binding.executePendingBindings();
    // }
    // @Override
    //     public int getItemCount() {
    //     return verticalBeans.size();
    // }
    public static class ViewHolderItemEmp extends RecyclerView.ViewHolder { // <<<<<<<<<< static

        private ItemEmpBinding binding;
        public ItemEmpBinding getBinding() {
            return binding;
        }
        public void setBinding(ItemEmpBinding binding) {
            this.binding = binding;
        }

        public ViewHolderItemEmp(ItemEmpBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        // private CircleImageView mImage;
        // private TextView mName;
        // public ViewHolder(@NonNull View itemView) {
        //     super(itemView);
        //     mImage = itemView.findViewById(R.id.img);
        //     mName = itemView.findViewById(R.id.name);
        // }
    }

    // private static class ViewHolderWallPaper extends RecyclerView.ViewHolder {
    //     private ItemWallPaperBinding binding;
    //     public ItemWallPaperBinding getBinding() {
    //         return binding;
    //     }
    //     public void setBinding(ItemWallPaperBinding binding) {
    //         this.binding = binding;
    //     }
    //     public ViewHolderWallPaper(ItemWallPaperBinding inflate) {
    //         super(inflate.getRoot());
    //         this.binding = inflate;
    //     }
    // }
}
