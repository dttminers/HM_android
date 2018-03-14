package com.hm.application.activity;

import android.support.v4.view.GravityCompat;
import android.content.Intent;
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
import com.hm.application.fragments.UserProfileFeaturesFragment;
import com.hm.application.utils.HmFonts;

public class MainHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView mtv_header, mtv_travelBook, mtv_shop_with, mtv_entrepreneur, mtv_LetsSocialize, mtv_ourService,
            m1tv_travelWithUs, m1tv_destinations, m1tv_planTrip, m1tv_activities, m1tv_theme, m1tv_rentouts, m1tv_findGuide, m1tv_travelBible, m1tv_nearBy,
            m2tv_allProducts, m2tv_giftCards,
            m3tv_referAFriend, m3tv_beAGuide, m3tv_startBlogging, m3tv_giveRentouts,
            m4tv_letsBarter, m4tv_findNeighbour, m4tv_letsDiscuss, m4tv_letsTravel, m4tv_getHelp,
            m5tv_distanceCalculator, m5tv_trekkingRoute, m5tv_currencyConverter, m5tv_languageTranslator, m5tv_travelBudget, m5tv_offlineMaps,
            mtv_account, mtv_contact_us, mtv_faqs, mtv_more;
    private LinearLayout mll_our_services, mll_socialize, mll_entrepreneur, mll_shop_with, mll_travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout = findViewById(R.id.tbHome);
        tabLayout.getChildAt(0).setSelected(true);

        replacePage(new Main_HomeFragment());

        menuItemBinding();
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

    private void menuItemBinding() {
        try {
            View header = navigationView.getHeaderView(0);

            mtv_header = (TextView) header.findViewById(R.id.tv_header);
            mtv_header.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            // Travel Book
            mtv_travelBook = (TextView) header.findViewById(R.id.tv_travel_book);
            mtv_travelBook.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_travel = (LinearLayout) header.findViewById(R.id.ll_travel);

            // Travel With Us
            m1tv_travelWithUs = (TextView) header.findViewById(R.id.tv_travel_with);
            m1tv_travelWithUs.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_travelWithUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelWithUsFragment());
                }
            });

            // Destinations
            m1tv_destinations = (TextView) header.findViewById(R.id.tv_dest);
            m1tv_destinations.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_destinations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });

            // Plan Trip
            m1tv_planTrip = (TextView) header.findViewById(R.id.tv_plan);
            m1tv_planTrip.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_planTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBPlanTripFragment());
                }
            });

            // Activities
            m1tv_activities = (TextView) header.findViewById(R.id.tv_activity);
            m1tv_activities.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_activities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBActivitiesFragment());
                }
            });

            // Theme
            m1tv_theme = (TextView) header.findViewById(R.id.tv_theme);
            m1tv_theme.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_theme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBThemeFragment());
                }
            });

            //Rentouts
            m1tv_rentouts = (TextView) header.findViewById(R.id.tv_rentout1);
            m1tv_rentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_rentouts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });

            //Find Guide
            m1tv_findGuide = (TextView) header.findViewById(R.id.tv_fguide);
            m1tv_findGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_findGuide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });

            //Travel Bible
            m1tv_travelBible = (TextView) header.findViewById(R.id.tv_tbible);
            m1tv_travelBible.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_travelBible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelBibleFragment());
                }
            });

            //Near By
            m1tv_nearBy = (TextView) header.findViewById(R.id.tv_near);
            m1tv_nearBy.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_nearBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBNearByFragment());
                }
            });

            // Shop With Us
            mtv_shop_with = (TextView) header.findViewById(R.id.tv_shop_with);
            mtv_shop_with.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_shop_with = (LinearLayout) header.findViewById(R.id.ll_shop_with);

            // All Products
            m2tv_allProducts = (TextView) header.findViewById(R.id.tv_all_product);
            m2tv_allProducts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Gift Cards
            m2tv_giftCards = (TextView) header.findViewById(R.id.tv_gift_card);
            m2tv_giftCards.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Be Entrepreneur
            mtv_entrepreneur = (TextView) header.findViewById(R.id.tv_entrepreneur);
            mtv_entrepreneur.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_entrepreneur = (LinearLayout) header.findViewById(R.id.ll_entrepreneur);

            //Refer A Friend
            m3tv_referAFriend = (TextView) header.findViewById(R.id.tv_refer);
            m3tv_referAFriend.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            //Be A Guide
            m3tv_beAGuide = (TextView) header.findViewById(R.id.tv_guide);
            m3tv_beAGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Start Blogging
            m3tv_startBlogging = (TextView) header.findViewById(R.id.tv_start_blog);
            m3tv_startBlogging.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Give Rentouts
            m3tv_giveRentouts = (TextView) header.findViewById(R.id.tv_rentout2);
            m3tv_giveRentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Socialize
            mtv_LetsSocialize = (TextView) header.findViewById(R.id.tv_socialize);
            mtv_LetsSocialize.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_socialize = (LinearLayout) header.findViewById(R.id.ll_socialize);

            // Lets Barter
            m4tv_letsBarter = (TextView) header.findViewById(R.id.tv_barter);
            m4tv_letsBarter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Find Neighbour
            m4tv_findNeighbour = (TextView) header.findViewById(R.id.tv_find_neigh);
            m4tv_findNeighbour.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Discuss
            m4tv_letsDiscuss = (TextView) header.findViewById(R.id.tv_discuss);
            m4tv_letsDiscuss.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Travel
            m4tv_letsTravel = (TextView) header.findViewById(R.id.tv_let_travel);
            m4tv_letsTravel.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Get Help
            m4tv_getHelp = (TextView) header.findViewById(R.id.tv_get_help);
            m4tv_getHelp.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Our Services
            mtv_ourService = (TextView) header.findViewById(R.id.tv_oservice);
            mtv_ourService.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_our_services = (LinearLayout) header.findViewById(R.id.ll_our_services);

            // Distance Calculator
            m5tv_distanceCalculator = (TextView) header.findViewById(R.id.tv_dis_cal);
            m5tv_distanceCalculator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Trekking Route
            m5tv_trekkingRoute = (TextView) header.findViewById(R.id.tv_t_route);
            m5tv_trekkingRoute.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Currency Converter
            m5tv_currencyConverter = (TextView) header.findViewById(R.id.tv_converter);
            m5tv_currencyConverter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Language Translator
            m5tv_languageTranslator = (TextView) header.findViewById(R.id.tv_lang_translate);
            m5tv_languageTranslator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Travel Budget
            m5tv_travelBudget = (TextView) header.findViewById(R.id.tv_budget);
            m5tv_travelBudget.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // OfflineMaps
            m5tv_offlineMaps = (TextView) header.findViewById(R.id.tv_off_map);
            m5tv_offlineMaps.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

//            mtv_account = (TextView) header.findViewById(R.id.tv_Accouts);
//            mtv_account.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtv_contact_us = (TextView) header.findViewById(R.id.tv_contact_us);
//            mtv_contact_us.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtv_faqs = (TextView) header.findViewById(R.id.tv_faqs);
//            mtv_faqs.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtv_more = (TextView) header.findViewById(R.id.tv_more);
//            mtv_more.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));


            mtv_travelBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mtv_travelBook.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);

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
                    mtv_shop_with.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);

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
                    mtv_entrepreneur.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);

                    mll_our_services.setVisibility(View.GONE);
                    mll_socialize.setVisibility(View.GONE);
                    mll_entrepreneur.setVisibility(View.VISIBLE);
                    mll_shop_with.setVisibility(View.GONE);
                    mll_travel.setVisibility(View.GONE);
                }
            });

            mtv_LetsSocialize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mtv_LetsSocialize.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);

                    mll_our_services.setVisibility(View.GONE);
                    mll_socialize.setVisibility(View.VISIBLE);
                    mll_entrepreneur.setVisibility(View.GONE);
                    mll_shop_with.setVisibility(View.GONE);
                    mll_travel.setVisibility(View.GONE);
                }
            });

            mtv_ourService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mtv_ourService.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
                if (drawer.isDrawerOpen(Gravity.LEFT)) { // close
                    drawer.closeDrawer(Gravity.START);
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.tab4);
                    Toast.makeText(MainHomeActivity.this, "Drawer Layout Close", Toast.LENGTH_SHORT).show();
                } else {
                    // open drawer
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.tab2);
                    drawer.openDrawer(Gravity.START);
                    Toast.makeText(MainHomeActivity.this, "Drawer Layout Open ", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            // Handle the camera action
        } else if (id == R.id.contact_us) {

        } else if (id == R.id.faq) {

        } else if (id == R.id.more) {
            replacePage(new UserProfileFeaturesFragment());
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}