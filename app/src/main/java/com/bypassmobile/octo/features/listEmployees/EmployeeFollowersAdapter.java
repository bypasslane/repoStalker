package com.bypassmobile.octo.features.listEmployees;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bypassmobile.octo.R;
import com.bypassmobile.octo.image.CircularImageTransformer;
import com.bypassmobile.octo.image.ImageLoader;
import com.bypassmobile.octo.model.User;

import java.util.List;

public class EmployeeFollowersAdapter extends RecyclerView.Adapter {

    private List<User> mData;
    private Context mContext;

    private ClickListener clickListener;

    EmployeeFollowersAdapter(Context context, List<User> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((EmpViewHolder) viewHolder).mEmpName.setText(mData.get(i).getName());
        ImageLoader.createImageLoader(mContext)
                .load(mData.get(i).getProfileURL())
                .resize(40,40)
                .transform(new CircularImageTransformer())
                .into(((EmpViewHolder) viewHolder).mEmpImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_employees_following_listadapter, parent, false);
        return new EmpViewHolder(view);
    }

    public void setOnItemClickListener(ClickListener listener) {
        clickListener = listener;
    }




    class EmpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView mEmpName;
        public ImageView mEmpImage;
        public EmpViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mEmpName = itemView.findViewById(R.id.empName);
            mEmpImage = itemView.findViewById(R.id.empImage);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), mContext, v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), mContext, v);
            return false;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, Context c, View v);
        void onItemLongClick(int position, Context c, View v);
    }
}
