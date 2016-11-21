package com.bypassmobile.octo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.bypassmobile.octo.core.rest.GithubEndpoint;

import retrofit.RestAdapter;

public class BaseActivity extends AppCompatActivity {

    private AlertDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        dismissProgressDialog();
        super.onStop();
    }

    protected void showProgressDialog(@NonNull String message) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mProgressDialog = new ProgressDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
