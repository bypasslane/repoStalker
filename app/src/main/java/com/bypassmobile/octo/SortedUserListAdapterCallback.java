package com.bypassmobile.octo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.TextUtils;

import com.bypassmobile.octo.core.model.User;

import java.util.Objects;

/**
 * James (Impactable)
 */
public class SortedUserListAdapterCallback extends SortedListAdapterCallback<User> {
    public SortedUserListAdapterCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public int compare(User user1, User user2) {
        return user1.getName().compareToIgnoreCase(user2.getName());
    }

    @Override
    public boolean areContentsTheSame(User user1, User user2) {
        return TextUtils.equals(user1.getName(), user2.getName());
    }

    @Override
    public boolean areItemsTheSame(User user1, User user2) {
        return Objects.equals(user1.getId(), user2.getId());
    }
}
