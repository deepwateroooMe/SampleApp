package com.me.sample.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.me.sample.R;
import com.me.sample.databinding.ItemEmpBinding;
import com.me.sample.db.bean.Employee;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 传递过来的数据
     */
    private List<Employee> mEmpList;
    
    public RecyclerAdapter(List<Employee> mEmpList) {
        this.mEmpList = mEmpList;
    }

    @NonNull
        @Override
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
    public void updateEmpList(List<Employee> items) {
        mEmpList = items;
        notifyDataSetChanged();
    }

    public static class ViewHolderItemEmp extends RecyclerView.ViewHolder { 
        
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
    
//     // 这个功能是：从RecycleView 中点击某张图片时，时入观看该小图的大图片模式，实现点击回调绑定, 这个应用里没有添加这个页面
//     public static class ClickBinding {
//         public void itemClick(EmployeeResponse.EmployeesBean verticalBean, View view) {
// // <<<<<<<<<< 需要定义一个特定的Activity这里我没有实现，没有要求
//             Intent intent = new Intent(view.getContext(), PictureViewActivity.class); 
//             intent.putExtra("img", verticalBean.getImg());
//             view.getContext().startActivity(intent);
//         }
//     }
}
