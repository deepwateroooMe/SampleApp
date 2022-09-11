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
import com.me.sample.model.Employee;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 传递过来的数据
     */
    private List<Employee> mEmpList;

    // 现在的实现是乱作一团，没有区别MVVM的功能模块，但是先把它弄出来再说
    private Context context;
    private PostItemListener mItemListener; 
    
    public RecyclerAdapter(List<Employee> mEmpList) {
        this.mEmpList = mEmpList;
    }

    @NonNull
        @Override
        // public ViewHolderItemEmp onCreateViewHolder(@NonNull ViewGroup parent, int i) { // 这里 返回类型 可能是写错了
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmpBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_emp, parent, false);
        return new ViewHolderItemEmp(binding);   
    }
    @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ItemEmpBinding binding = ((ViewHolderItemEmp)holder).getBinding();
        binding.setEmp(mEmpList.get(position));
        binding.executePendingBindings();
    }

    @Override
        public int getItemCount() {
        return mEmpList.size();
    }
    public void updateAnswers(List<Employee> items) {
        mEmpList = items;
        notifyDataSetChanged();
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
    
    public static class ViewHolderItemEmp extends RecyclerView.ViewHolder
    { // <<<<<<<<<< static
        // implements View.OnClickListener { // <<<<<<<<<< static

        // PostItemListener mItemListener;
        
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
    }
    
//     // 这个功能是：从RecycleView 中点击某张图片时，时入观看该小图的大图片模式，实现点击回调绑定
//     public static class ClickBinding {
//         public void itemClick(EmployeeResponse.EmployeesBean verticalBean, View view) {
// // <<<<<<<<<< 需要定义一个特定的Activity这里我没有实现，没有要求
//             Intent intent = new Intent(view.getContext(), PictureViewActivity.class); 
//             intent.putExtra("img", verticalBean.getImg());
//             view.getContext().startActivity(intent);
//         }
//     }
}
