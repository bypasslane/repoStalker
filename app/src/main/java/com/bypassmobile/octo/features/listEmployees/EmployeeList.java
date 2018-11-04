package com.bypassmobile.octo.features.listEmployees;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bypassmobile.octo.R;
import com.bypassmobile.octo.features.BaseActivity;
import com.bypassmobile.octo.model.User;
import com.bypassmobile.octo.rest.GithubEndpoint;
import com.bypassmobile.octo.utils.AppInfo;
import com.bypassmobile.octo.utils.IntentKeys;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeList extends BaseActivity {

    @BindView(R.id.employeeList) RecyclerView mEmpRecView;

    Unbinder mButKnifeUnbinder;

    EmployeeListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout
        setContentView(R.layout.list_employees_main);
        //bind buterknife
        mButKnifeUnbinder = ButterKnife.bind(this);
        //get the github data and show
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButKnifeUnbinder.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //method to show the data on the layout
    private void showEmployees(List<User> users) {
        //reset the adapter with new data
        mListAdapter = new EmployeeListAdapter(this, users);
        mListAdapter.setOnItemClickListener(new EmployeeListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, Context c, View v) {
                Intent intent = new Intent(c, EmployeeFollowing.class);
                intent.putExtra(IntentKeys.USER_ID, users.get(position).getUserId());
                c.startActivity(new Intent());
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
