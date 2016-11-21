package com.bypassmobile.octo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class BypassMemberListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bypass_member_list);

        loadUserListFragment();
    }

    private void loadUserListFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UserListFragment userListFragment = UserListFragment.newInstance(BuildConfig.BYPASS_ORGANIZATION_ID, UserListFragment.TYPE_ORGANIZATION);
        fragmentTransaction.replace(R.id.activity_bypass_member_list_root, userListFragment);
        fragmentTransaction.commit();
    }
}
