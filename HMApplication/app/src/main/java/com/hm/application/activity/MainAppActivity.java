package com.hm.application.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.fragments.UserInfoFragment;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainAppActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {

    private DuoDrawerLayout mDuoDrawerLayout;
    private DuoMenuView mDuoMenuView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        mDuoDrawerLayout = findViewById(R.id.drawer);
        mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.menu_icon);

        TextView mtv_travelBook = findViewById(R.id.tv_travel_book);
        mtv_travelBook.setTypeface(HmFonts.getRobotoBold(MainAppActivity.this));
        mtv_travelBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonFunctions.toDisplayToast("Lll ll " , MainAppActivity.this);
            }
        });

        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mDuoDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

        mDuoMenuView.setOnMenuClickListener(this);
        mDuoMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonFunctions.toDisplayToast(" Views " , MainAppActivity.this);

                // Travel Book
                TextView mtv_travelBook = v.findViewById(R.id.tv_travel_book);
                mtv_travelBook.setTypeface(HmFonts.getRobotoBold(MainAppActivity.this));
                mtv_travelBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonFunctions.toDisplayToast("Lll" , MainAppActivity.this);
                    }
                });
            }
        });
//        mDuoMenuView.setAdapter(mMenuAdapter);

    }

    @Override
    public void onFooterClicked() {
        CommonFunctions.toDisplayToast("tologout", MainAppActivity.this);
    }

    @Override
    public void onHeaderClicked() {
        CommonFunctions.toDisplayToast("to view", MainAppActivity.this);
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        Log.d("Hmapp", " click "+ position  + objectClicked);
        // Set the toolbar title
//        setTitle(mTitles.get(position));

        // Set the right options selected
//        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            default:
                goToFragment(new UserInfoFragment());
                break;
        }

        // Close the drawer
        mDuoDrawerLayout.closeDrawer();
    }

    public void goToFragment(Fragment fragment) {
        Log.d("HmApp", "MainHome  fragment " + fragment.getTag() + " : " + fragment.getId() + ": " + fragment.getClass().getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
