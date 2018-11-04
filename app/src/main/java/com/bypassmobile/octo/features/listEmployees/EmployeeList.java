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
import com.bypassmobile.octo.utils.AppInfo;
import com.bypassmobile.octo.utils.IntentKeys;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set the layout
        setContentView(R.layout.list_employees_main);
        //set base code
        super.onCreate(savedInstanceState);
        //get the github employee list and show
        GithubEndpoint endpoint = getEndpoint();
        endpoint.getOrganizationMember(AppInfo.GITHUB_SERVICE_NAME).enqueue(
                new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful()) {
                            showEmployees(response.body());
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
    private void showEmployees(List<User> users) {
        //see if there was a query to filter by
        String filter = getIntent().getStringExtra(IntentKeys.USER_QUERY);
        if (filter!=null && !filter.isEmpty()) {
            for (Iterator<User> it = users.iterator(); it.hasNext();) {
                if (!it.next().getName().toLowerCase().contains(filter.toLowerCase()))
                    it.remove(); // NOTE: Iterator's remove method, not ArrayList's, is used.
            }
        }
        if (users.size()==0) {
            //add no results message
            User noUser = new User("No Users Found", "", "");
            users.add(noUser);
        }
        //reset the adapter with new data
        mListAdapter = new EmployeeListAdapter(this, users);
        ((EmployeeListAdapter) mListAdapter).setOnItemClickListener(new EmployeeListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, Context c, View v) {
                Intent intent = new Intent(c, EmployeeFollowing.class);
                intent.putExtra(IntentKeys.USER_NAME, users.get(position).getName());
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
