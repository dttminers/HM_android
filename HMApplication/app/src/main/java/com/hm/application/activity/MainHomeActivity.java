package com.hm.application.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;
import com.hm.application.R;
import com.hm.application.common.UserData;
import com.hm.application.fragments.Main_ChatFragment;
import com.hm.application.fragments.Main_FriendRequestFragment;
import com.hm.application.fragments.Main_HomeFragment;
import com.hm.application.fragments.Main_NotificationFragment;
import com.hm.application.fragments.UserTab1Fragment;
import com.hm.application.model.AppDataStorage;
import com.hm.application.utils.CommonFunctions;

public class MainHomeActivity extends AppCompatActivity {

    private TabLayout mTbHome;
    private Trace mTrace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            mTrace = FirebasePerformance.getInstance().newTrace("MainHomeActivity");
            mTrace.start();
            setContentView(R.layout.activity_main_home);

            toSetTitle(getResources().getString(R.string.app_name));

            UserData.toGetUserData(MainHomeActivity.this, true);
            AppDataStorage.getUserInfo(MainHomeActivity.this);

            mTbHome = findViewById(R.id.tbHome);
            mTbHome.getChildAt(0).setSelected(true);

            toSetTabPage(0);

            mTbHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    toSetTabPage(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    toSetTabPage(tab.getPosition());
                }
            });
            replacePage(new UserTab1Fragment());


        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toSetTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_photo_camera_black_24dp));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.light)));
            if (title != null) {
                if (title.length() > 0) {
                    getSupportActionBar().setTitle(CommonFunctions.firstLetterCaps(title));
                } else {
                    getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                }
            } else {
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            }
        }
    }

    private void toSetTabPage(int position) {
        try {
            switch (position) {
                case 0:
                    toSetTitle(getResources().getString(R.string.app_name));
                    replacePage(new UserTab1Fragment());
                    break;
                case 1:
                    toSetTitle("Follow Request");
                    replacePage(new Main_FriendRequestFragment());
                    break;
                case 2:
                    startActivity(new Intent(MainHomeActivity.this, ShareActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    break;
                case 3:
                    toSetTitle("Notification");
                    replacePage(new Main_NotificationFragment());
                    break;
                case 4:
                    startActivity(new Intent(MainHomeActivity.this, UserInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    break;
                default:
                    toSetTitle(getResources().getString(R.string.app_name));
                    replacePage(new Main_HomeFragment());
                    break;
            }
        } catch (Exception | Error e) {
            e.printStackTrace();

        }
    }

    public void replacePage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send:
                    replacePage(new Main_ChatFragment());
                break;
            case android.R.id.home:
//                onBackPressed();
                startActivity(new Intent(MainHomeActivity.this, ShareActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d("hampp ", "backstack" + getFragmentManager().getBackStackEntryCount());
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTrace.stop();
    }
}