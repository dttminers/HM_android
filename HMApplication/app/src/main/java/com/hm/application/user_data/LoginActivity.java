package com.hm.application.user_data;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.model.AppConstants;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            if (getIntent() != null) {
                switch (getIntent().getStringExtra(AppConstants.USERDATA)) {
                    case AppConstants.LOGIN:
                        replaceMainTabsFragment(new LoginFragment());
                }
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    private void replaceMainTabsFragment(Fragment fragment) {
        try {
            if (getIntent().getBundleExtra("bundle") != null) {
                fragment.setArguments(getIntent().getExtras());
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentrepalce, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out, R.animator.flip_left_in, R.animator.flip_left_out)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (!(getSupportFragmentManager().findFragmentById(R.id.fragmentrepalce) instanceof LoginFragment)) {
                replaceMainTabsFragment(new LoginFragment());
            } else {
                super.onBackPressed();
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}