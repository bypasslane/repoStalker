package com.bypassmobile.octo.features.listEmployees;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bypassmobile.octo.R;
import com.bypassmobile.octo.features.BaseActivity;
import com.bypassmobile.octo.model.User;
import com.bypassmobile.octo.rest.GithubEndpoint;
import com.bypassmobile.octo.utils.IntentKeys;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeFollowing extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set the layout
        setContentView(R.layout.list_employees_main);
        //set base code
        super.onCreate(savedInstanceState);
        //get the github employee list and show
        GithubEndpoint endpoint = getEndpoint();
        endpoint.getFollowingUser(getIntent().getStringExtra(IntentKeys.USER_NAME)).enqueue(
                new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful()) {
                            showFollowers(response.body());
                        } else {
                            Log.d("OCTO", "Error: users call: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d("OCTO", "Error: users call: " + t.getMessage());
                    }
                }
        );
    }



    //method to show the data on the layout
    private void showFollowers(List<User> users) {
        //sort the list
        if (users.size() > 0) {
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(final User object1, final User object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
        }
        //reset the adapter with new data
        mListAdapter = new EmployeeFollowersAdapter(this, users);
        ((EmployeeFollowersAdapter) mListAdapter).setOnItemClickListener(new EmployeeFollowersAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, Context c, View v) {
                Intent intent = new Intent(c, EmployeeFollowing.class);
                intent.putExtra(IntentKeys.USER_NAME, users.get(position).getUserId());
                c.startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, Context c, View v) {
                //do nothing right now
            }
        });
        mEmpRecView.setAdapter(mListAdapter);
        //position the employees
        mEmpRecView.setLayoutManager(new LinearLayoutManager(this));
    }

}
