package com.hm.application.activity;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.application.R;
import com.hm.application.fragments.Main_ChatFragment;
import com.hm.application.fragments.Main_FriendRequestFragment;
import com.hm.application.fragments.Main_HomeFragment;
import com.hm.application.fragments.Main_NotificationFragment;
import com.hm.application.fragments.Main_Tab3Fragment;
import com.hm.application.fragments.TBActivitiesFragment;
import com.hm.application.fragments.TBDestinationsFragment;
import com.hm.application.fragments.TBNearByFragment;
import com.hm.application.fragments.TBPlanTripFragment;
import com.hm.application.fragments.TBThemeFragment;
import com.hm.application.fragments.TBTravelBibleFragment;
import com.hm.application.fragments.TBTravelWithUsFragment;
import com.hm.application.user_data.LoginFragment;
import com.hm.application.utils.HmFonts;

public class MainHomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    TextView mtv_header, mtv_travel_b, mtv_shop_with, mtv_entrepreneur, mtv_socialize, mtv_oservice,
            m1tv_travel_with, m1tv_dest, m1tv_plan, m1tv_activity, m1tv_theme, m1tv_rentout1, m1tv_fguide, m1tv_tbible, m1tv_near,
            m2tv_all_product, m2tv_gift_card,
            m3tv_refer, m3tv_guide, m3tv_start_blog, m3tv_rentout2,
            m4tv_barter, m4tv_find_neigh, m4tv_discuss, m4tv_let_travel, m4tv_get_help,
            m5tv_dis_cal, m5tv_t_route, m5tv_converter, m5tv_lang_translate, m5tv_budget, m5tv_off_map;
    LinearLayout mll_our_services, mll_socialize, mll_entrepreneur, mll_shop_with, mll_travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        tabLayout = findViewById(R.id.tbHome);
        tabLayout.getChildAt(0).setSelected(true);
        replacePage(new Main_HomeFragment());

        menuBinding();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replacePage(new Main_HomeFragment());
                        break;
                    case 1:
                        replacePage(new Main_FriendRequestFragment());
                        break;
                    case 2:
                        replacePage(new Main_Tab3Fragment());
                        break;
                    case 3:
                        replacePage(new Main_NotificationFragment());
                        break;
                    case 4:
                        replacePage(new Main_ChatFragment());
                        break;
                    default:
                        replacePage(new Main_HomeFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void menuBinding() {
        try {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            navigationView = (NavigationView) findViewById(R.id.navigation_menu);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Toast.makeText(MainHomeActivity.this, "clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.tab4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(MainHomeActivity.this, drawerLayout, R.string.action_settings, R.string.cast_casting_to_device) {
                @Override
                public void setToolbarNavigationClickListener(View.OnClickListener onToolbarNavigationClickListener) {
                    super.setToolbarNavigationClickListener(onToolbarNavigationClickListener);
                }
            };

            mDrawerToggle.setHomeAsUpIndicator(R.drawable.tab2);
            mDrawerToggle.setDrawerIndicatorEnabled(true);

            mtv_header = (TextView) findViewById(R.id.tv_header);
            mtv_travel_b = (TextView) findViewById(R.id.tv_travel_book);
            mtv_shop_with = (TextView) findViewById(R.id.tv_shop_with);
            mtv_entrepreneur = (TextView) findViewById(R.id.tv_entrepreneur);
            mtv_socialize = (TextView) findViewById(R.id.tv_socialize);
            mtv_oservice = (TextView) findViewById(R.id.tv_oservice);

            m1tv_travel_with = (TextView) findViewById(R.id.tv_travel_with);
            m1tv_travel_with.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelWithUsFragment());
                }
            });
            m1tv_dest = (TextView) findViewById(R.id.tv_dest);
            m1tv_dest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });

            m1tv_plan = (TextView) findViewById(R.id.tv_plan);
            m1tv_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBPlanTripFragment());
                }
            });
            m1tv_activity = (TextView) findViewById(R.id.tv_activity);
            m1tv_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBActivitiesFragment());
                }
            });
            m1tv_theme = (TextView) findViewById(R.id.tv_theme);
            m1tv_theme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBThemeFragment());
                }
            });
            m1tv_rentout1 = (TextView) findViewById(R.id.tv_rentout1);
            m1tv_rentout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });
            m1tv_fguide = (TextView) findViewById(R.id.tv_fguide);
            m1tv_rentout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });
            m1tv_tbible = (TextView) findViewById(R.id.tv_tbible);
            m1tv_tbible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelBibleFragment());
                }
            });
            m1tv_near = (TextView) findViewById(R.id.tv_near);
            m1tv_near.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBNearByFragment());
                }
            });

            m2tv_all_product = (TextView) findViewById(R.id.tv_all_product);
            m2tv_gift_card = (TextView) findViewById(R.id.tv_gift_card);

            m3tv_refer = (TextView) findViewById(R.id.tv_refer);
            m3tv_guide = (TextView) findViewById(R.id.tv_guide);
            m3tv_start_blog = (TextView) findViewById(R.id.tv_start_blog);
            m3tv_rentout2 = (TextView) findViewById(R.id.tv_rentout2);

            m4tv_barter = (TextView) findViewById(R.id.tv_barter);
            m4tv_find_neigh = (TextView) findViewById(R.id.tv_find_neigh);
            m4tv_discuss = (TextView) findViewById(R.id.tv_discuss);
            m4tv_let_travel = (TextView) findViewById(R.id.tv_let_travel);
            m4tv_get_help = (TextView) findViewById(R.id.tv_get_help);

            m5tv_dis_cal = (TextView) findViewById(R.id.tv_dis_cal);
            m5tv_t_route = (TextView) findViewById(R.id.tv_t_route);
            m5tv_converter = (TextView) findViewById(R.id.tv_converter);
            m5tv_lang_translate = (TextView) findViewById(R.id.tv_lang_translate);
            m5tv_budget = (TextView) findViewById(R.id.tv_budget);
            m5tv_off_map = (TextView) findViewById(R.id.tv_off_map);


            mll_our_services = (LinearLayout) findViewById(R.id.ll_our_services);
            mll_socialize = (LinearLayout) findViewById(R.id.ll_socialize);
            mll_entrepreneur = (LinearLayout) findViewById(R.id.ll_entrepreneur);
            mll_shop_with = (LinearLayout) findViewById(R.id.ll_shop_with);
            mll_travel = (LinearLayout) findViewById(R.id.ll_travel);

            mtv_travel_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mll_our_services.setVisibility(View.GONE);
                    mll_socialize.setVisibility(View.GONE);
                    mll_entrepreneur.setVisibility(View.GONE);
                    mll_shop_with.setVisibility(View.GONE);
                    mll_travel.setVisibility(View.VISIBLE);
                }
            });

            mtv_shop_with.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mll_our_services.setVisibility(View.GONE);
                    mll_socialize.setVisibility(View.GONE);
                    mll_entrepreneur.setVisibility(View.GONE);
                    mll_shop_with.setVisibility(View.VISIBLE);
                    mll_travel.setVisibility(View.GONE);
                }
            });

            mtv_entrepreneur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mll_our_services.setVisibility(View.GONE);
                    mll_socialize.setVisibility(View.GONE);
                    mll_entrepreneur.setVisibility(View.VISIBLE);
                    mll_shop_with.setVisibility(View.GONE);
                    mll_travel.setVisibility(View.GONE);
                }
            });

            mtv_socialize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mll_our_services.setVisibility(View.GONE);
                    mll_socialize.setVisibility(View.VISIBLE);
                    mll_entrepreneur.setVisibility(View.GONE);
                    mll_shop_with.setVisibility(View.GONE);
                    mll_travel.setVisibility(View.GONE);
                }
            });

            mtv_oservice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mll_our_services.setVisibility(View.VISIBLE);
                    mll_socialize.setVisibility(View.GONE);
                    mll_entrepreneur.setVisibility(View.GONE);
                    mll_shop_with.setVisibility(View.GONE);
                    mll_travel.setVisibility(View.GONE);
                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public void replacePage(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                Toast.makeText(MainHomeActivity.this, " Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuUserProfile:
                startActivity(new Intent(MainHomeActivity.this, UserProfileListActivity.class));
                break;

            case android.R.id.home:
//                Toast.makeText(MainHomeActivity.this, "Drawer Layout", Toast.LENGTH_SHORT).show();
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) { // close
                    drawerLayout.closeDrawer(Gravity.START);
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.tab4);
                    Toast.makeText(MainHomeActivity.this, "Drawer Layout Close", Toast.LENGTH_SHORT).show();
                } else {
                    // open drawer
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.tab2);
                    drawerLayout.openDrawer(Gravity.START);
                    Toast.makeText(MainHomeActivity.this, "Drawer Layout Open ", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}