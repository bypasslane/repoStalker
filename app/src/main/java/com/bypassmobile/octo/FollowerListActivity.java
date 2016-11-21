package com.bypassmobile.octo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class FollowerListActivity extends AppCompatActivity {

    private static final String KEY_FOLLOWER_USERNAME = "followerlistactivity:name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_list);

        Intent intent = getIntent();
        String name = intent.getStringExtra(KEY_FOLLOWER_USERNAME);

        setupHeader(name);
        loadUserListFragment(name);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }

        return handled;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_to_center, R.anim.slide_center_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_to_center, R.anim.slide_center_to_right);
    }

    private void setupHeader(String name) {
        TextView header = (TextView) findViewById(R.id.activity_follower_list_header_textview);
        String headerText = getString(R.string.header_format_1_arg, name);
        header.setText(headerText);
    }

    private void loadUserListFragment(@NonNull String name) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UserListFragment userListFragment = UserListFragment.newInstance(name, UserListFragment.TYPE_FOLLOWER);
        fragmentTransaction.replace(R.id.activity_follower_list_container, userListFragment);
        fragmentTransaction.commit();
    }

    public static void launchActivity(@NonNull String followerId, @NonNull FragmentActivity activity) {
        Intent intent = new Intent(activity, FollowerListActivity.class);
        intent.putExtra(KEY_FOLLOWER_USERNAME, followerId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_right_to_center, R.anim.slide_center_to_left);
    }
}
