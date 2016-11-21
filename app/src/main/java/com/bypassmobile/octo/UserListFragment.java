package com.bypassmobile.octo;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bypassmobile.octo.core.model.User;
import com.bypassmobile.octo.core.rest.GithubAPI;
import com.bypassmobile.octo.core.rest.GithubEndpoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserListFragment extends Fragment {

    private UserRecyclerViewAdapter mAdapter;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ TYPE_ORGANIZATION, TYPE_FOLLOWER })
    public @interface ListType {}

    private static final String KEY_NAME = "userlistfragment:name";
    private static final String KEY_ID_TYPE = "userlistfragment:type";
    public static final int TYPE_ORGANIZATION = 0;
    public static final int TYPE_FOLLOWER = 1;

    private String mName;
    private int mType;

    public UserListFragment() {}

    public static UserListFragment newInstance(String id, @ListType int type) {
        UserListFragment fragment = new UserListFragment();

        if (TextUtils.isEmpty(id)) {
            id = BuildConfig.BYPASS_ORGANIZATION_ID;
            type = TYPE_ORGANIZATION;
        }

        Bundle args = new Bundle();
        args.putString(KEY_NAME, id);
        args.putInt(KEY_ID_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle arguments = getArguments();

        if (arguments != null) {
            mName = arguments.getString(KEY_NAME);
            mType = arguments.getInt(KEY_ID_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_user_list_recyclerview);

        mAdapter = new UserRecyclerViewAdapter();
        mAdapter.setListener(new OnUserClickedListener());
        recyclerView.setAdapter(mAdapter);

        setupEmptyView(view);
    }

    private void setupEmptyView(View view) {
        View emptyView = view.findViewById(R.id.fragment_user_list_emptyview);
        TextView messageTextView = (TextView) view.findViewById(R.id.fragment_user_list_empty_message);
        messageTextView.setText(R.string.user_list_empty_message);

        mAdapter.setEmptyView(emptyView);
    }

    @Override
    public void onResume() {
        super.onResume();

        GithubEndpoint endpoint = GithubAPI.getEndpoint();

        switch (mType) {
            case TYPE_ORGANIZATION:
                endpoint.getUsersInOrganization(mName, new UserFetchCallback());
                break;
            case TYPE_FOLLOWER:
                endpoint.getUsersFollowedBy(mName, new UserFetchCallback());
                break;
            default:
                Log.e(UserListFragment.class.getName(), "An unknown type was set.");

                if (isVisible()) {
                    Snackbar.make(getView(), R.string.unexplainable_error_message, Snackbar.LENGTH_INDEFINITE).show();
                }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new OnSearchTextChangedListener());
    }

    private class UserFetchCallback implements Callback<List<User>> {
        @Override
        public void success(List<User> users, Response response) {
            if (mAdapter != null) {
                mAdapter.setUsers(users);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (isVisible()) {
                String errorMessage = "Could not load users.";

                if (error.isNetworkError()) {
                    errorMessage += " Are you connected to the internet?";
                }
                Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_INDEFINITE).show();
            }
        }
    }

    private class OnUserClickedListener implements OnRecyclerViewItemClickListener<User> {
        @Override
        public void onItemClicked(User item) {
            FollowerListActivity.launchActivity(item.getName(), getActivity());
        }
    }

    private class OnSearchTextChangedListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mAdapter.filterUsers(newText);
            return true;
        }
    }
}
