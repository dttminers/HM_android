package com.hm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.application.R;
import com.hm.application.common.UserData;
import com.hm.application.fragments.AccountFragment;
import com.hm.application.fragments.Contact_Us_Fragment;
import com.hm.application.fragments.FAQFragment;
import com.hm.application.fragments.Main_ChatFragment;
import com.hm.application.fragments.Main_FriendRequestFragment;
import com.hm.application.fragments.Main_HomeFragment;
import com.hm.application.fragments.Main_NotificationFragment;
import com.hm.application.fragments.Main_Tab3Fragment;
import com.hm.application.fragments.MoreFragment;
import com.hm.application.fragments.TBActivitiesFragment;
import com.hm.application.fragments.TBDestinationsFragment;
import com.hm.application.fragments.TBFindGuideFragment;
import com.hm.application.fragments.TBNearByFragment;
import com.hm.application.fragments.TBPlanTripFragment;
import com.hm.application.fragments.TBRentoutsFragment;
import com.hm.application.fragments.TBThemeFragment;
import com.hm.application.fragments.TBTravelBibleFragment;
import com.hm.application.fragments.TBTravelWithUsFragment;
import com.hm.application.fragments.UserFollowersListFragment;
import com.hm.application.fragments.UserFollowingListFragment;
import com.hm.application.fragments.UserOptionsFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
//import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;

public class MainHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;

    private DrawerLayout drawer;
    //    private DuoDrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView mtv_header, mtv_travelBook, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService,
            m1tv_travelWithUs, m1tv_destinations, m1tv_planTrip, m1tv_activities, m1tv_theme, m1tv_rentouts, m1tv_findGuide, m1tv_travelBible, m1tv_nearBy,
            m2tv_allProducts, m2tv_giftCards,
            m3tv_referAFriend, m3tv_beAGuide, m3tv_startBlogging, m3tv_giveRentouts,
            m4tv_letsBarter, m4tv_findNeighbour, m4tv_letsDiscuss, m4tv_letsTravel, m4tv_getHelp,
            m5tv_distanceCalculator, m5tv_trekkingRoute, m5tv_currencyConverter, m5tv_languageTranslator, m5tv_travelBudget, m5tv_offlineMaps,
            mtv_account, mtv_contact_us, mtv_faqs, mtv_more,
            mtxtUphName, mtxtUphFrom, mtxtUphNotification, mtxtUphWallet, mtxtUphBoard, mtxtUphTemp, mtxtUphBucket;

    private LinearLayout mll_our_services, mll_socialize, mll_entrepreneur, mll_shop_with, mll_travel, mllUserProHead, mllUserProHead1, mllUserProHead2;
    private CircleImageView mimgUph;
    private RatingBar mrbUphRatingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        User.getUser(MainHomeActivity.this).getUid();
        Log.d("HmApp", "MainHome UserName main: " + User.getUser(MainHomeActivity.this).getUsername() + " : " + User.getUser(MainHomeActivity.this).getUid());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.color.dark_pink1);

        AppDataStorage.getUserInfo(MainHomeActivity.this);
        Log.d("HmApp", " MainHomeActivity " + User.getUser(MainHomeActivity.this).getUid());
        UserData.toGetUserData(MainHomeActivity.this);
        AppDataStorage.getUserInfo(MainHomeActivity.this);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d("HmApp", " USERPicPath onDrawerOpened " + User.getUser(MainHomeActivity.this).getPicPath());
                if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
                    Picasso.with(MainHomeActivity.this)
                            .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
                            .into(mimgUph);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout = findViewById(R.id.tbHome);
        tabLayout.getChildAt(0).setSelected(true);

        toolbar.setNavigationIcon(R.drawable.menu_icon);

        replacePage(new TBRentoutsFragment());

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
//                       startActivity(new Intent(MainHomeActivity.this, PackageDetailActivity.class));
                        break;
                    case 3:
                        replacePage(new UserFollowersListFragment());
                        break;
                    case 4:
                        replacePage(new UserFollowingListFragment());
                        break;
                    default:
                        replacePage(new UserFollowersListFragment());
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

            mtv_header = header.findViewById(R.id.tv_header);
            mtv_header.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
            mtv_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainHomeActivity.this, " ll", Toast.LENGTH_SHORT).show();
                }
            });

            // Travel Book
            mtv_travelBook = header.findViewById(R.id.tv_travel_book);
            mtv_travelBook.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_travel = header.findViewById(R.id.ll_travel);

            // Travel With Us
            m1tv_travelWithUs = header.findViewById(R.id.tv_travel_with);
            m1tv_travelWithUs.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_travelWithUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelWithUsFragment());
                }
            });

            // Destinations
            m1tv_destinations = header.findViewById(R.id.tv_dest);
            m1tv_destinations.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_destinations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });

            // Plan Trip
            m1tv_planTrip = header.findViewById(R.id.tv_plan);
            m1tv_planTrip.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_planTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBPlanTripFragment());
                }
            });

            // Activities
            m1tv_activities = header.findViewById(R.id.tv_activity);
            m1tv_activities.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_activities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBActivitiesFragment());
                }
            });

            // Theme
            m1tv_theme = header.findViewById(R.id.tv_theme);
            m1tv_theme.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_theme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBThemeFragment());
                }
            });

            //Rentouts
            m1tv_rentouts = header.findViewById(R.id.tv_rentout1);
            m1tv_rentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_rentouts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBRentoutsFragment());
                }
            });

            //Find Guide
            m1tv_findGuide = header.findViewById(R.id.tv_fguide);
            m1tv_findGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_findGuide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBFindGuideFragment());
                }
            });

            //Travel Bible
            m1tv_travelBible = header.findViewById(R.id.tv_tbible);
            m1tv_travelBible.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_travelBible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelBibleFragment());
                }
            });

            //Near By
            m1tv_nearBy = header.findViewById(R.id.tv_near);
            m1tv_nearBy.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            m1tv_nearBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBNearByFragment());
                }
            });

            // Shop With Us
            mtv_shop_with = header.findViewById(R.id.tv_shop_with);
            mtv_shop_with.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_shop_with = header.findViewById(R.id.ll_shop_with);

            // All Products
            m2tv_allProducts = header.findViewById(R.id.tv_all_product);
            m2tv_allProducts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Gift Cards
            m2tv_giftCards = header.findViewById(R.id.tv_gift_card);
            m2tv_giftCards.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Be Entrepreneur
            mtv_entrepreneur = header.findViewById(R.id.tv_entrepreneur);
            mtv_entrepreneur.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_entrepreneur = header.findViewById(R.id.ll_entrepreneur);

            //Refer A Friend
            m3tv_referAFriend = header.findViewById(R.id.tv_refer);
            m3tv_referAFriend.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            //Be A Guide
            m3tv_beAGuide = header.findViewById(R.id.tv_guide);
            m3tv_beAGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Start Blogging
            m3tv_startBlogging = header.findViewById(R.id.tv_start_blog);
            m3tv_startBlogging.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Give Rentouts
            m3tv_giveRentouts = header.findViewById(R.id.tv_rentout2);
            m3tv_giveRentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Socialize
            mtv_letsSocialize = header.findViewById(R.id.tv_socialize);
            mtv_letsSocialize.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_socialize = header.findViewById(R.id.ll_socialize);

            // Lets Barter
            m4tv_letsBarter = header.findViewById(R.id.tv_barter);
            m4tv_letsBarter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Find Neighbour
            m4tv_findNeighbour = header.findViewById(R.id.tv_find_neigh);
            m4tv_findNeighbour.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Discuss
            m4tv_letsDiscuss = header.findViewById(R.id.tv_discuss);
            m4tv_letsDiscuss.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Lets Travel
            m4tv_letsTravel = header.findViewById(R.id.tv_let_travel);
            m4tv_letsTravel.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Get Help
            m4tv_getHelp = header.findViewById(R.id.tv_get_help);
            m4tv_getHelp.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Our Services
            mtv_ourService = header.findViewById(R.id.tv_oservice);
            mtv_ourService.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));

            mll_our_services = header.findViewById(R.id.ll_our_services);

            // Distance Calculator
            m5tv_distanceCalculator = header.findViewById(R.id.tv_dis_cal);
            m5tv_distanceCalculator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Trekking Route
            m5tv_trekkingRoute = header.findViewById(R.id.tv_t_route);
            m5tv_trekkingRoute.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Currency Converter
            m5tv_currencyConverter = header.findViewById(R.id.tv_converter);
            m5tv_currencyConverter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Language Translator
            m5tv_languageTranslator = header.findViewById(R.id.tv_lang_translate);
            m5tv_languageTranslator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // Travel Budget
            m5tv_travelBudget = header.findViewById(R.id.tv_budget);
            m5tv_travelBudget.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            // OfflineMaps
            m5tv_offlineMaps = header.findViewById(R.id.tv_off_map);
            m5tv_offlineMaps.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));

            //user_profile_header
            //LinerLayout
            mllUserProHead = header.findViewById(R.id.llUserProHead);
            mllUserProHead1 = header.findViewById(R.id.llUserProHead1);
            mllUserProHead2 = header.findViewById(R.id.llUserProHead2);
            //ImageView
            mimgUph = header.findViewById(R.id.imgUph);
            Log.d("HmApp", " USERPicPath " + User.getUser(MainHomeActivity.this).getPicPath());
            if (User.getUser(MainHomeActivity.this).getPicPath() != null) {

                Picasso.with(MainHomeActivity.this)
                        .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
                        .into(mimgUph);
            }
            //RatingBar
            mrbUphRatingData = header.findViewById(R.id.rbUphRatingData);
            // TextView
            mtxtUphName = header.findViewById(R.id.txtUphName);
            mtxtUphName.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
            if (User.getUser(MainHomeActivity.this).getUsername() != null) {
                mtxtUphName.setText(User.getUser(MainHomeActivity.this).getUsername());
            }

            mtxtUphFrom = header.findViewById(R.id.txtUphFrom);
            mtxtUphFrom.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
            if (User.getUser(MainHomeActivity.this).getLivesIn() != null) {
                mtxtUphFrom.setText(User.getUser(MainHomeActivity.this).getLivesIn());
            }

            mtxtUphNotification = header.findViewById(R.id.txtUphNotification);
            mtxtUphNotification.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mtxtUphWallet = header.findViewById(R.id.txtUphWallet);
            mtxtUphWallet.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mtxtUphBoard = header.findViewById(R.id.txtUphBoard);
            mtxtUphBoard.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mtxtUphTemp = header.findViewById(R.id.txtUphTemp);
            mtxtUphTemp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mtxtUphBucket = header.findViewById(R.id.txtUphBucket);
            mtxtUphBucket.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));


            mtv_travelBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toToggleMenuItem(mtv_travelBook, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService, mll_travel, mll_shop_with, mll_entrepreneur, mll_our_services, mll_socialize);
                }
            });

            mtv_shop_with.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toToggleMenuItem(mtv_shop_with, mtv_travelBook, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService, mll_shop_with, mll_travel, mll_entrepreneur, mll_our_services, mll_socialize);
                }
            });

            mtv_entrepreneur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toToggleMenuItem(mtv_entrepreneur, mtv_shop_with, mtv_travelBook, mtv_letsSocialize, mtv_ourService, mll_entrepreneur, mll_shop_with, mll_travel, mll_our_services, mll_socialize);
                }
            });

            mtv_letsSocialize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toToggleMenuItem(mtv_letsSocialize, mtv_shop_with, mtv_entrepreneur, mtv_travelBook, mtv_ourService, mll_socialize, mll_shop_with, mll_entrepreneur, mll_our_services, mll_travel);
                }
            });

            mtv_ourService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toToggleMenuItem(mtv_ourService, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_travelBook, mll_our_services, mll_shop_with, mll_entrepreneur, mll_travel, mll_socialize);
                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private void toToggleMenuItem(TextView mTvMain, TextView mTv1, TextView mTv2, TextView mTv3, TextView mTv4, LinearLayout mLlMain, LinearLayout mLl1, LinearLayout mLl2, LinearLayout mLl3, LinearLayout mLl4) {
        if (mLlMain.getVisibility() == View.GONE) {
            mTvMain.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);
            mTv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
            mTv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
            mTv3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
            mTv4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);

            mLl1.setVisibility(View.GONE);
            mLl2.setVisibility(View.GONE);
            mLl3.setVisibility(View.GONE);
            mLl4.setVisibility(View.GONE);
            mLlMain.setVisibility(View.VISIBLE);
        } else {
            mTvMain.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
            mTv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
            mTv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
            mTv3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
            mTv4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);

            mLl1.setVisibility(View.GONE);
            mLl2.setVisibility(View.GONE);
            mLl3.setVisibility(View.GONE);
            mLl4.setVisibility(View.GONE);
            mLlMain.setVisibility(View.GONE);
        }
    }

    public void replacePage(Fragment fragment) {
        Log.d("HmApp", "MainHome  fragment " + fragment.getTag() + " : " + fragment.getId() + ": " + fragment.getClass().getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        drawer.closeDrawer(GravityCompat.START);

//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//
//            @Override
//            public void onBackStackChanged() {
//                Log.d("HmApp", "MainHome onBackStackChanged 2 : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
//                if (getSupportFragmentManager().getBackStackEntryCount() == 0 && getSupportFragmentManager().getFragments()!= null) {
//                    Log.d("HmApp", "MainHome kk");
////                    replaceTabData(new Main_HomeFragment());
//                }
//
//                Log.d("HmApp", "MainHome CurrentFrag " + getSupportFragmentManager().findFragmentById(R.id.flHomeContainer).getClass() + ":" + getSupportFragmentManager().findFragmentById(R.id.flHomeContainer).getTag());
//            }
//        });

//        FragmentManager manager = getSupportFragmentManager();
//        if (manager != null) {
//            FragmentTransaction t = manager.beginTransaction();
//            Fragment currentFrag = manager.findFragmentById(R.id.flHomeContainer);
//
//            //Check if the new Fragment is the same
//            //If it is, don't add to the back stack
//            Log.d("HmApp", " Frag " + (currentFrag != null ? currentFrag.getClass() : null) + " : " + (currentFrag != null ? currentFrag.getClass().equals(fragment.getClass()) : null));
//            Log.d("HmApp", " same Frag " + getSupportFragmentManager().getBackStackEntryCount() + " : " + fragment.getClass().getName() + (currentFrag != null ? currentFrag.getTag() : null) + " : " + (currentFrag != null ? currentFrag.getId() : 0));
//            if (currentFrag != null && currentFrag.getClass().equals(fragment.getClass())) {
//                t.replace(R.id.flHomeContainer, fragment).commit();
//            } else {
//                t.replace(R.id.flHomeContainer, fragment).addToBackStack(null).commit();
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
//                replacePage(new UserOptionsFragment());
                startActivity(new Intent(MainHomeActivity.this, UserOptionsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.account) {
            replacePage(new AccountFragment());
        } else if (item.getItemId() == R.id.contact_us) {
            replacePage(new Contact_Us_Fragment());
        } else if (item.getItemId() == R.id.faq) {
            replacePage(new FAQFragment());
        } else if (item.getItemId() == R.id.more) {
            replacePage(new MoreFragment());
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("HmApp", "MainHome onBackPress : " + drawer.isDrawerOpen(GravityCompat.START) + " : " + getFragmentManager().getBackStackEntryCount());
        Log.d("HmApp", "MainHome onBackStackChanged 1 : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.d("HmApp", "MainHome km");
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            Log.d("HmApp", "MainHome kl");
            getFragmentManager().popBackStack();
        } else {
            Log.d("HmApp", "MainHome kj");
//            popBackStack();
            super.onBackPressed();
//            finish();
        }
    }

}