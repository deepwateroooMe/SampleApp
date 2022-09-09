package com.me.sample.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.me.sample.R;
import com.me.sample.model.EmployeeResponse;

import de.hdodenhof.circleimageview.CircleImageView;

// public class RecyclerAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private EmployeeResponse mEmpList;
    private Context context;

    public RecyclerAdapter(Context context, EmployeeResponse mEmpList) {
        this.context = context;
        this.mEmpList = mEmpList;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_emp, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;   
    }

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        // Set the name of the 'NicePlace'
        ((ViewHolder)viewHolder).mName.setText(mEmpList.getEmployees().get(i).getName());

        // Set the image
        RequestOptions defaultOptions = new RequestOptions()
            .error(R.drawable.ic_launcher_background);
// 这里的原理和过程还没有弄明白：加载过程到底是怎样的，加载的是什么？        
        Glide.with(context)
            .setDefaultRequestOptions(defaultOptions)
            .load(mEmpList.getEmployees().get(i).getImgUrlSmall())
            .into(((ViewHolder)viewHolder).mImage);
    }

    @Override
    public int getItemCount() {
        return mEmpList.getEmployees().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mImage;
        private TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.img);
            mName = itemView.findViewById(R.id.name);
        }
    }
}
