package com.bypassmobile.octo;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bypassmobile.octo.core.image.CircularTransform;
import com.bypassmobile.octo.core.image.ImageLoader;
import com.bypassmobile.octo.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private SortedList<User> mUsers;
    private List<User> mAllUsers;
    private OnRecyclerViewItemClickListener<User> mListener;
    private View mEmptyView;

    public UserRecyclerViewAdapter() {
        mUsers = new SortedList<>(User.class, new SortedUserListAdapterCallback(this));
        mAllUsers = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User user = mUsers.get(position);

        holder.mNameTextView.setText(user.getName());
//        holder.mFollowerCountTextView.setText(user.getFollowerCount());

        ImageLoader.createImageLoader(holder.itemView.getContext())
                    .load(user.getProfileURL())
                    .transform(new CircularTransform())
                    .into(holder.mImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onItemClicked(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setListener(OnRecyclerViewItemClickListener<User> mListener) {
        this.mListener = mListener;
    }

    public void setUsers(List<User> users) {
        mUsers.clear();
        mAllUsers.clear();

        mUsers.addAll(users);
        mAllUsers.addAll(users);

        notifyDataSetChanged();

        updateEmptyView();
    }

    public void filterUsers(String query) {

        if (TextUtils.isEmpty(query)) {
            mUsers.clear();
            mUsers.addAll(mAllUsers);
        } else {
            mUsers.beginBatchedUpdates();

            for (User user : mAllUsers) {
                if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                    mUsers.add(user);
                } else {
                    mUsers.remove(user);
                }
            }

            mUsers.endBatchedUpdates();
        }

        updateEmptyView();
    }

    public void setEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;

        updateEmptyView();
    }

    private void updateEmptyView() {
        if (mEmptyView != null) {
            if (mUsers.size() > 0) {
                mEmptyView.setVisibility(View.GONE);
            } else {
                mEmptyView.setVisibility(View.VISIBLE);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mNameTextView;
        public final TextView mFollowerCountTextView;

        public ViewHolder(View view) {
            super(view);

            mImageView = (ImageView) view.findViewById(R.id.list_item_user_imageview);
            mNameTextView = (TextView) view.findViewById(R.id.list_item_user_name_textview);
            mFollowerCountTextView = (TextView) view.findViewById(R.id.list_item_user_followers_count_textview);
        }
    }
}
