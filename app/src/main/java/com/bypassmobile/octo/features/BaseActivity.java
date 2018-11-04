package com.bypassmobile.octo.features;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bypassmobile.octo.R;
import com.bypassmobile.octo.features.listEmployees.EmployeeList;
import com.bypassmobile.octo.rest.GithubEndpoint;
import com.bypassmobile.octo.utils.AppInfo;
import com.bypassmobile.octo.utils.IntentKeys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.employeeList) protected RecyclerView mEmpRecView;

    private Unbinder mButKnifeUnbinder;
    private GithubEndpoint endpoint;

    protected RecyclerView.Adapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppInfo.GITHUB_API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        endpoint = retrofit.create(GithubEndpoint.class);

        //bind buterknife
        mButKnifeUnbinder = ButterKnife.bind(this);
        //colorize menu
        try {
            getSupportActionBar().setBackgroundDrawable(new
                    ColorDrawable(Color.parseColor("#4c85b6")));
        } catch (NullPointerException e) { e.printStackTrace();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButKnifeUnbinder.unbind();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            EditText searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchEmployees(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    protected GithubEndpoint getEndpoint() {
        return endpoint;
    }

    //this method searches through employees using the text
    //and shows an activity of results
    private void searchEmployees(String query) {
        Intent intent = new Intent(this, EmployeeList.class);
        intent.putExtra(IntentKeys.USER_QUERY, query);
        this.startActivity(intent);
    }


}