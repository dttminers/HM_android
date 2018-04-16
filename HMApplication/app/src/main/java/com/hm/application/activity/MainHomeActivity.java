package com.hm.application.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.adapter.MenuListAdapter;
import com.hm.application.common.UserData;
import com.hm.application.fragments.Main_ChatFragment;
import com.hm.application.fragments.Main_FriendRequestFragment;
import com.hm.application.fragments.Main_HomeFragment;
import com.hm.application.fragments.Main_NotificationFragment;
import com.hm.application.fragments.Main_Tab3Fragment;
import com.hm.application.fragments.TBActivitiesFragment;
import com.hm.application.fragments.TBDestinationsFragment;
import com.hm.application.fragments.TBFindGuideFragment;
import com.hm.application.fragments.TBPlanTripFragment;
import com.hm.application.fragments.TBRentoutsFragment;
import com.hm.application.fragments.TBThemeFragment;
import com.hm.application.fragments.TBTravelBibleFragment;
import com.hm.application.fragments.TBTravelWithUsFragment;
import com.hm.application.fragments.UserTab1Fragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.services.MyFirebaseInstanceIDService;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainHomeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTbHome;

    // Drawer Section
    private DuoDrawerLayout mDuoDrawerLayout;
    private DuoMenuView mDuoMenuView;
    private DuoDrawerToggle mDuoDrawerToggle;

    // Menu User Information Section
    private LinearLayout mLlUserProHead, mLlUserProHead1, mLlUserProHead2;
    private TextView mTvUphName, mTvUphFrom, mTvUphNotification, mTvUphWallet, mTvUphBoard, mTvUphTemp, mTvUphBucket;
    private RatingBar mRbUphRatingData;
    private CircleImageView mCivDrawerMenuProfilePic, mCivMenuItemProfilePic;

    private ExpandableListView mElv;
    private List<String> mListDataHeader;
    private HashMap<String, List<String>> mListDataChild;
    private MenuListAdapter menuListAdapter;

    // Logout
    private LinearLayout mLlLogout;
    private TextView mTvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main_home);
            new MyFirebaseInstanceIDService().onTokenRefresh();

            UserData.toGetUserData(MainHomeActivity.this);
            AppDataStorage.getUserInfo(MainHomeActivity.this);
//
//
//            AppDataStorage.getUserInfo(MainHomeActivity.this);
//            UserData.toGetUserData(MainHomeActivity.this);

            mToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.color.dark_pink1);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon_dark_grey);

            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();

            mTbHome = findViewById(R.id.tbHome);
            mTbHome.getChildAt(0).setSelected(true);

            mToolbar.setNavigationIcon(R.drawable.menu_icon);

            mDuoDrawerToggle = new DuoDrawerToggle(this, mDuoDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDuoDrawerLayout.setDrawerListener(mDuoDrawerToggle);
            mDuoDrawerToggle.syncState();

            //user_profile_header
            //LinerLayout
            mLlUserProHead = findViewById(R.id.llUserProHead);
            mLlUserProHead1 = findViewById(R.id.llUserProHead1);
            mLlUserProHead2 = findViewById(R.id.llUserProHead2);

            //ImageView
            mCivDrawerMenuProfilePic = findViewById(R.id.imgUph);
            if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
                Picasso.with(MainHomeActivity.this)
                        .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
                        .resize(200, 200)
                        .error(R.color.light2)
                        .placeholder(R.color.light)
                        .into(mCivDrawerMenuProfilePic);
            }

            //RatingBar
            mRbUphRatingData = findViewById(R.id.rbUphRatingData);
            LayerDrawable star = (LayerDrawable) mRbUphRatingData.getProgressDrawable();
            star.getDrawable(2).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.light_red, null), PorterDuff.Mode.SRC_ATOP);
            star.getDrawable(0).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.grey5, null), PorterDuff.Mode.SRC_ATOP);
            star.getDrawable(1).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.light_red, null), PorterDuff.Mode.SRC_ATOP);

            // TextView
            mTvUphName = findViewById(R.id.txtUphName);
            mTvUphName.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
            if (User.getUser(MainHomeActivity.this).getUsername() != null) {
                mTvUphName.setText(User.getUser(MainHomeActivity.this).getUsername());
            }

            mTvUphFrom = findViewById(R.id.txtUphFrom);
            mTvUphFrom.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
            if (User.getUser(MainHomeActivity.this).getLivesIn() != null) {
                mTvUphFrom.setText(User.getUser(MainHomeActivity.this).getLivesIn());
            }

            mTvUphNotification = findViewById(R.id.txtUphNotification);
            mTvUphNotification.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mTvUphWallet = findViewById(R.id.txtUphWallet);
            mTvUphWallet.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mTvUphBoard = findViewById(R.id.txtUphBoard);
            mTvUphBoard.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mTvUphTemp = findViewById(R.id.txtUphTemp);
            mTvUphTemp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            mTvUphBucket = findViewById(R.id.txtUphBucket);
            mTvUphBucket.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

            // Logout
            mLlLogout = findViewById(R.id.ll_list_grp);
            mTvLogout = findViewById(R.id.lblListHeader);

            // get the listview
            mElv = findViewById(R.id.elv);

            // preparing list data
            prepareListData();

            // setting list adapter
            menuListAdapter = new MenuListAdapter(this, mListDataHeader, mListDataChild);
            mElv.setAdapter(menuListAdapter);
            setListViewHeight(mElv, 0, false);


            mElv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    toSelectFragment(mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition));
                    int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                    parent.setItemChecked(index, true);
                    return false;
                }
            });

            mElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    setListViewHeight(parent, groupPosition, true);
                    return false;
                }
            });

            toSetTabPage(0);

            mTbHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    toSetTabPage(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
//                    toSetTabPage(tab.getPosition());
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    toSetTabPage(tab.getPosition());
                }
            });

            mLlLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonFunctions.toLogout(MainHomeActivity.this);
                    finish();

                }
            });
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void toSetTabPage(int position) {
        try {
            switch (position) {
                case 0:
//                    replacePage(new Main_HomeFragment());
                    replacePage(new UserTab1Fragment());
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
                    replacePage(new TBTravelBibleFragment());
                    break;
                default:
                    replacePage(new Main_HomeFragment());
                    break;
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void setListViewHeight(ExpandableListView listView, int group, boolean status) {
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);

        for (int i = 0; i < menuListAdapter.getGroupCount(); i++) {

            View groupItem = menuListAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            // To Add Child View Height
            if (status) {
                if (((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group))) {
                    for (int j = 0; j < menuListAdapter.getChildrenCount(i); j++) {
                        View listItem = menuListAdapter.getChildView(i, j, false, null, listView);
                        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                        totalHeight += listItem.getMeasuredHeight();
                    }
                } // child view
            }// status
        }// end of for loop

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (menuListAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void toSelectFragment(String name) {
        try {
            switch (name) {
                case "Travel Packages":
                    replacePage(new TBTravelWithUsFragment());
                    break;
                case "Plan A Trip":
                    replacePage(new TBPlanTripFragment());
                    break;
                case "All Destinations":
                    replacePage(new TBDestinationsFragment());
                    break;
                case "Travel Themes":
                    replacePage(new TBThemeFragment());
                    break;
                case "Rentouts":
                    replacePage(new TBRentoutsFragment());
                    break;
                case "Activites":
                    replacePage(new TBActivitiesFragment());
                    break;
                case "Find A Guide":
                    replacePage(new TBFindGuideFragment());
                    break;
                default:
//                    replacePage(new TBTravelWithUsFragment());
                    break;
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

    private void prepareListData() {

        mListDataHeader = new ArrayList<>();
        mListDataChild = new HashMap<>();

        // Adding header data
        mListDataHeader.add("Travel With Us");
        mListDataHeader.add("Travel Bible");
        mListDataHeader.add("Shop With Us");
        mListDataHeader.add("Be An Entrepreneur");
        mListDataHeader.add("Lets Socialise");
        mListDataHeader.add("Other Services");
        mListDataHeader.add("Entertainment");
        mListDataHeader.add("Budget");
        mListDataHeader.add("High Mountains");
        mListDataHeader.add("Help Us Improve");

        // Adding child data
        List<String> menu1 = new ArrayList<>();
        menu1.add("Travel Packages");
        menu1.add("Plan A Trip");
        menu1.add("Customise A Trip");
        menu1.add("All Destinations");
        menu1.add("Travel Themes");
        menu1.add("Activites");
        menu1.add("Rentouts");
        menu1.add("Find A Guide");
        menu1.add("Guide Curated Iteneraries");
        menu1.add("Near By");
        menu1.add("Trip Care");


        List<String> menu2 = new ArrayList<>();
        menu2.add("Travel Bogs");
        menu2.add("Travel Dairies");
        menu2.add("Upload A blog/ Diary");

        List<String> menu3 = new ArrayList<>();
        menu3.add("All Products");
        menu3.add("Products by Travellers");
        menu3.add("Gift Card");
        menu3.add("Favourite");

        List<String> menu4 = new ArrayList<>();
        menu4.add("Refer A Friend");
        menu4.add("Be a Guide");
        menu4.add("Start Blogging");
        menu4.add("My Rentouts");
        menu4.add("My Products");
        menu4.add("FoTostock");
        menu4.add("Bid your products");

        List<String> menu5 = new ArrayList<>();
        menu5.add("Let's Barter");
        menu5.add("Let's Discuss");
        menu5.add("Let's Travel");
        menu5.add("Get a help");
        menu5.add("Know your neighbourhood");

        List<String> menu6 = new ArrayList<>();
        menu6.add("Maps");
        menu6.add("Distance Calculator");
        menu6.add("Trekking Routes");
        menu6.add("Offline Road Maps");
        menu6.add("Location Tracker");

        List<String> menu7 = new ArrayList<>();
        menu7.add("Music");
        menu7.add("Movies");
        menu7.add("Travel Magazines");
        menu7.add("Travel Boook");
        menu7.add("Discover the Best in the World");
        menu7.add("Language Translator");
        menu7.add("Learn the Language");

        List<String> menu8 = new ArrayList<>();
        menu8.add("Trip Budget/ Money Management");
        menu8.add("Past Trip Accounts");
        menu8.add("Currency Converter");

        List<String> menu9 = new ArrayList<>();
        menu9.add("About Us");
        menu9.add("CSR");
        menu9.add("Send Feedback");
        menu9.add("Contact Us");
        menu9.add("Join Team HM");
        menu9.add("Rate Us");

        List<String> menu10 = new ArrayList<>();
        menu10.add("Travel Support");
        menu10.add("Blog Support");
        menu10.add("Shop Support");
        menu10.add("Entrepreneurial Support");
        menu10.add("Payment Support");
        menu10.add("Other");

        mListDataChild.put(mListDataHeader.get(0), menu1);
        mListDataChild.put(mListDataHeader.get(1), menu2);
        mListDataChild.put(mListDataHeader.get(2), menu3);
        mListDataChild.put(mListDataHeader.get(3), menu4);
        mListDataChild.put(mListDataHeader.get(4), menu5);
        mListDataChild.put(mListDataHeader.get(5), menu6);
        mListDataChild.put(mListDataHeader.get(6), menu7);
        mListDataChild.put(mListDataHeader.get(7), menu8);
        mListDataChild.put(mListDataHeader.get(8), menu9);
        mListDataChild.put(mListDataHeader.get(9), menu10);
    }

    public void replacePage(Fragment fragment) {
        Log.d("HmApp", "MainHome  fragment " + fragment.getTag() + " : " + fragment.getId() + ": " + fragment.getClass().getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        mDuoDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        View mMenuUserIcon = menu.findItem(R.id.menu_user_profile).getActionView();
        mCivMenuItemProfilePic = mMenuUserIcon.findViewById(R.id.ivPicUser);
        if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
            Picasso.with(MainHomeActivity.this)
                    .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
                    .into(mCivMenuItemProfilePic);
        }
        mCivMenuItemProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHomeActivity.this, UserOptionsActivity.class));
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Toast.makeText(MainHomeActivity.this, " Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_user_profile:
                startActivity(new Intent(MainHomeActivity.this, UserOptionsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d("HmApp", "MainHome onBackPress : " + mDuoDrawerLayout.isDrawerOpen(GravityCompat.START) + " : " + getFragmentManager().getBackStackEntryCount());
        Log.d("HmApp", "MainHome onBackStackChanged 1 : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
        if (mDuoDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDuoDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}


//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.account) {
//            replacePage(new AccountFragment());
//        } else if (item.getItemId() == R.id.contact_us) {
//            replacePage(new Contact_Us_Fragment());
//        } else if (item.getItemId() == R.id.faq) {
//            replacePage(new FAQFragment());
//        } else if (item.getItemId() == R.id.more) {
//            replacePage(new MoreFragment());
//        }
//        mDuoDrawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

//    private void menuItemBinding() {
//        try {
//
////            View header = navigationView.getHeaderView(0);
//
//            mtv_header = header.findViewById(R.id.tv_header);
//            mtv_header.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//            mtv_header.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(MainHomeActivity.this, " ll", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            // Travel Book
//            mtv_travelBook = header.findViewById(R.id.tv_travel_book);
//            mtv_travelBook.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlTravelWithUsData = header.findViewById(R.id.ll_travel);
//
//            // Travel With Us
//            m1tv_travelWithUs = header.findViewById(R.id.tv_travel_with);
//            m1tv_travelWithUs.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_travelWithUs.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelWithUsFragment());
//                }
//            });
//
//            // Destinations
//            m1tv_destinations = header.findViewById(R.id.tv_dest);
//            m1tv_destinations.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_destinations.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBDestinationsFragment());
//                }
//            });
//
//            // Plan Trip
//            m1tv_planTrip = header.findViewById(R.id.tv_plan);
//            m1tv_planTrip.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_planTrip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBPlanTripFragment());
//                }
//            });
//
//            // Activities
//            m1tv_activities = header.findViewById(R.id.tv_activity);
//            m1tv_activities.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_activities.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBActivitiesFragment());
//                }
//            });
//
//            // Theme
//            m1tv_theme = header.findViewById(R.id.tv_theme);
//            m1tv_theme.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_theme.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBThemeFragment());
//                }
//            });
//
//            //Rentouts
//            m1tv_rentouts = header.findViewById(R.id.tv_rentout1);
//            m1tv_rentouts.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_rentouts.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBRentoutsFragment());
//                }
//            });
//
//            //Find Guide
//            m1tv_findGuide = header.findViewById(R.id.tv_fguide);
//            m1tv_findGuide.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_findGuide.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBFindGuideFragment());
//                }
//            });
//
//            //Travel Bible
//            m1tv_travelBible = header.findViewById(R.id.tv_tbible);
//            m1tv_travelBible.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_travelBible.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelBibleFragment());
//                }
//            });
//
//            //Near By
//            m1tv_nearBy = header.findViewById(R.id.tv_near);
//            m1tv_nearBy.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_nearBy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBNearByFragment());
//                }
//            });
//
//            // Shop With Us
//            mtv_shop_with = header.findViewById(R.id.tv_shop_with);
//            mtv_shop_with.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlShopWithUsData = header.findViewById(R.id.ll_shop_with);
//
//            // All Products
//            m2tv_allProducts = header.findViewById(R.id.tv_all_product);
//            m2tv_allProducts.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Gift Cards
//            m2tv_giftCards = header.findViewById(R.id.tv_gift_card);
//            m2tv_giftCards.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Be Entrepreneur
//            mtv_entrepreneur = header.findViewById(R.id.tv_entrepreneur);
//            mtv_entrepreneur.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlEntrepreneurData = header.findViewById(R.id.ll_entrepreneur);
//
//            //Refer A Friend
//            m3tv_referAFriend = header.findViewById(R.id.tv_refer);
//            m3tv_referAFriend.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            //Be A Guide
//            m3tv_beAGuide = header.findViewById(R.id.tv_guide);
//            m3tv_beAGuide.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Start Blogging
//            m3tv_startBlogging = header.findViewById(R.id.tv_start_blog);
//            m3tv_startBlogging.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Give Rentouts
//            m3tv_giveRentouts = header.findViewById(R.id.tv_rentout2);
//            m3tv_giveRentouts.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Socialize
//            mtv_letsSocialize = header.findViewById(R.id.tv_socialize);
//            mtv_letsSocialize.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlSocializedData = header.findViewById(R.id.ll_socialize);
//
//            // Lets Barter
//            m4tv_letsBarter = header.findViewById(R.id.tv_barter);
//            m4tv_letsBarter.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Find Neighbour
//            m4tv_findNeighbour = header.findViewById(R.id.tv_find_neigh);
//            m4tv_findNeighbour.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Discuss
//            m4tv_letsDiscuss = header.findViewById(R.id.tv_discuss);
//            m4tv_letsDiscuss.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Travel
//            m4tv_letsTravel = header.findViewById(R.id.tv_let_travel);
//            m4tv_letsTravel.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Get Help
//            m4tv_getHelp = header.findViewById(R.id.tv_get_help);
//            m4tv_getHelp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Our Services
//            mtv_ourService = header.findViewById(R.id.tv_oservice);
//            mtv_ourService.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlOurServicesData = header.findViewById(R.id.ll_our_services);
//
//            // Distance Calculator
//            m5tv_distanceCalculator = header.findViewById(R.id.tv_dis_cal);
//            m5tv_distanceCalculator.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Trekking Route
//            m5tv_trekkingRoute = header.findViewById(R.id.tv_t_route);
//            m5tv_trekkingRoute.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Currency Converter
//            m5tv_currencyConverter = header.findViewById(R.id.tv_converter);
//            m5tv_currencyConverter.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Language Translator
//            m5tv_languageTranslator = header.findViewById(R.id.tv_lang_translate);
//            m5tv_languageTranslator.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Travel Budget
//            m5tv_travelBudget = header.findViewById(R.id.tv_budget);
//            m5tv_travelBudget.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // OfflineMaps
//            m5tv_offlineMaps = header.findViewById(R.id.tv_off_map);
//            m5tv_offlineMaps.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            //user_profile_header
//            //LinerLayout
//            mLlUserProHead = header.findViewById(R.id.llUserProHead);
//            mLlUserProHead1 = header.findViewById(R.id.llUserProHead1);
//            mLlUserProHead2 = header.findViewById(R.id.llUserProHead2);
//            //ImageView
//            mCivDrawerMenuProfilePic = header.findViewById(R.id.imgUph);
//            Log.d("HmApp", " USERPicPath " + User.getUser(MainHomeActivity.this).getPicPath());
//            if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
//
//                Picasso.with(MainHomeActivity.this)
//                        .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
//                        .into(mCivDrawerMenuProfilePic);
//            }
//            //RatingBar
//            mRbUphRatingData = header.findViewById(R.id.rbUphRatingData);
//            // TextView
//            mTvUphName = header.findViewById(R.id.txtUphName);
//            mTvUphName.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getUsername() != null) {
//                mTvUphName.setText(User.getUser(MainHomeActivity.this).getUsername());
//            }
//
//            mTvUphFrom = header.findViewById(R.id.txtUphFrom);
//            mTvUphFrom.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getLivesIn() != null) {
//                mTvUphFrom.setText(User.getUser(MainHomeActivity.this).getLivesIn());
//            }
//
//            mTvUphNotification = header.findViewById(R.id.txtUphNotification);
//            mTvUphNotification.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphWallet = header.findViewById(R.id.txtUphWallet);
//            mTvUphWallet.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphBoard = header.findViewById(R.id.txtUphBoard);
//            mTvUphBoard.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphTemp = header.findViewById(R.id.txtUphTemp);
//            mTvUphTemp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphBucket = header.findViewById(R.id.txtUphBucket);
//            mTvUphBucket.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//
//            mtv_travelBook.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_travelBook, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService, mLlTravelWithUsData, mLlShopWithUsData, mLlEntrepreneurData, mLlOurServicesData, mLlSocializedData);
//                }
//            });
//
//            mtv_shop_with.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_shop_with, mtv_travelBook, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService, mLlShopWithUsData, mLlTravelWithUsData, mLlEntrepreneurData, mLlOurServicesData, mLlSocializedData);
//                }
//            });
//
//            mtv_entrepreneur.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_entrepreneur, mtv_shop_with, mtv_travelBook, mtv_letsSocialize, mtv_ourService, mLlEntrepreneurData, mLlShopWithUsData, mLlTravelWithUsData, mLlOurServicesData, mLlSocializedData);
//                }
//            });
//
//            mtv_letsSocialize.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_letsSocialize, mtv_shop_with, mtv_entrepreneur, mtv_travelBook, mtv_ourService, mLlSocializedData, mLlShopWithUsData, mLlEntrepreneurData, mLlOurServicesData, mLlTravelWithUsData);
//                }
//            });
//
//            mtv_ourService.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_ourService, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_travelBook, mLlOurServicesData, mLlShopWithUsData, mLlEntrepreneurData, mLlTravelWithUsData, mLlSocializedData);
//                }
//            });
//        } catch (Exception | Error e) {
//            e.printStackTrace();FirebaseCrash.report(e);();
//        }
//    }


//09/04/2018
//    private void menuItemBinding() {
//        try {
//
////            View header = navigationView.getHeaderView(0);
//
//
//            // Travel Book
//            mtv_travelBook = findViewById(R.id.tv_travel_book);
//            mtv_travelBook.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mLlTravelWithUsData = findViewById(R.id.ll_travel);
//            mLlTravelWithUs = findViewById(R.id.ll_travel_book);
//
//            // Travel With Us
//            m1tv_travelWithUs = findViewById(R.id.tv_travel_with);
//            m1tv_travelWithUs.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_travelWithUs.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelWithUsFragment());
//                }
//            });
//
//            // Destinations
//            m1tv_destinations = findViewById(R.id.tv_dest);
//            m1tv_destinations.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_destinations.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBDestinationsFragment());
//                }
//            });
//
//            // Plan Trip
//            m1tv_planTrip = findViewById(R.id.tv_plan);
//            m1tv_planTrip.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_planTrip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBPlanTripFragment());
//                }
//            });
//
//            // Activities
//            m1tv_activities = findViewById(R.id.tv_activity);
//            m1tv_activities.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_activities.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBActivitiesFragment());
//                }
//            });
//
//            // Theme
//            m1tv_theme = findViewById(R.id.tv_theme);
//            m1tv_theme.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_theme.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBThemeFragment());
//                }
//            });
//
//            //Rentouts
//            m1tv_rentouts = findViewById(R.id.tv_rentout1);
//            m1tv_rentouts.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_rentouts.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBRentoutsFragment());
//                }
//            });
//
//            //Find Guide
//            m1tv_findGuide = findViewById(R.id.tv_fguide);
//            m1tv_findGuide.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_findGuide.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBFindGuideFragment());
//                }
//            });
//
//            //Travel Bible
//            m1tv_travelBible = findViewById(R.id.tv_tbible);
//            m1tv_travelBible.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_travelBible.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelBibleFragment());
//                }
//            });
//
//            //Near By
//            m1tv_nearBy = findViewById(R.id.tv_near);
//            m1tv_nearBy.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            m1tv_nearBy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBNearByFragment());
//                }
//            });
//
//            // Shop With Us
//            mtv_shop_with = findViewById(R.id.tv_shop_with);
//            mtv_shop_with.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mLlShopWithUsData = findViewById(R.id.ll_shop_with);
//            mLlShopWithUs = findViewById(R.id.ll_shop_with_us);
//
//            // All Products
//            m2tv_allProducts = findViewById(R.id.tv_all_product);
//            m2tv_allProducts.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Gift Cards
//            m2tv_giftCards = findViewById(R.id.tv_gift_card);
//            m2tv_giftCards.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Be Entrepreneur
//            mtv_entrepreneur = findViewById(R.id.tv_entrepreneur);
//            mtv_entrepreneur.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mLlEntrepreneurData = findViewById(R.id.ll_entrepreneur);
//            mLlEntrepreneur = findViewById(R.id.ll_be_entrepreneur);
//
//            //Refer A Friend
//            m3tv_referAFriend = findViewById(R.id.tv_refer);
//            m3tv_referAFriend.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            //Be A Guide
//            m3tv_beAGuide = findViewById(R.id.tv_guide);
//            m3tv_beAGuide.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Start Blogging
//            m3tv_startBlogging = findViewById(R.id.tv_start_blog);
//            m3tv_startBlogging.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Give Rentouts
//            m3tv_giveRentouts = findViewById(R.id.tv_rentout2);
//            m3tv_giveRentouts.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Socialize
//            mtv_letsSocialize = findViewById(R.id.tv_socialize);
//            mtv_letsSocialize.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mLlSocializedData = findViewById(R.id.ll_socialize);
//            mLlSocialized = findViewById(R.id.ll_lets_socialize);
//
//            // Lets Barter
//            m4tv_letsBarter = findViewById(R.id.tv_barter);
//            m4tv_letsBarter.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Find Neighbour
//            m4tv_findNeighbour = findViewById(R.id.tv_find_neigh);
//            m4tv_findNeighbour.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Discuss
//            m4tv_letsDiscuss = findViewById(R.id.tv_discuss);
//            m4tv_letsDiscuss.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Lets Travel
//            m4tv_letsTravel = findViewById(R.id.tv_let_travel);
//            m4tv_letsTravel.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Get Help
//            m4tv_getHelp = findViewById(R.id.tv_get_help);
//            m4tv_getHelp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Our Services
//            mtv_ourService = findViewById(R.id.tv_our_service);
//            mtv_ourService.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mLlOurServicesData = findViewById(R.id.ll_our_services);
//            mLlOurServices = findViewById(R.id.ll_our_service);
//
//            // Distance Calculator
//            m5tv_distanceCalculator = findViewById(R.id.tv_dis_cal);
//            m5tv_distanceCalculator.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Trekking Route
//            m5tv_trekkingRoute = findViewById(R.id.tv_t_route);
//            m5tv_trekkingRoute.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Currency Converter
//            m5tv_currencyConverter = findViewById(R.id.tv_converter);
//            m5tv_currencyConverter.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Language Translator
//            m5tv_languageTranslator = findViewById(R.id.tv_lang_translate);
//            m5tv_languageTranslator.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Travel Budget
//            m5tv_travelBudget = findViewById(R.id.tv_budget);
//            m5tv_travelBudget.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // OfflineMaps
//            m5tv_offlineMaps = findViewById(R.id.tv_off_map);
//            m5tv_offlineMaps.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            // Hm
//            mtv_Hm = findViewById(R.id.tv_Hm);
//            mtv_Hm.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mLlHighMountain = findViewById(R.id.ll_hm);
//            mLlHighMountainData = findViewById(R.id.llHighMountain);
//
//
//            //user_profile_header
//            //LinerLayout
//            mLlUserProHead = findViewById(R.id.llUserProHead);
//            mLlUserProHead1 = findViewById(R.id.llUserProHead1);
//            mLlUserProHead2 = findViewById(R.id.llUserProHead2);
//            //ImageView
//            mCivDrawerMenuProfilePic = findViewById(R.id.imgUph);
//            Log.d("HmApp", " USERPicPath " + User.getUser(MainHomeActivity.this).getPicPath());
//            if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
//                Picasso.with(MainHomeActivity.this)
//                        .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
//                        .into(mCivDrawerMenuProfilePic);
//            }
//            //RatingBar
//            mRbUphRatingData = findViewById(R.id.rbUphRatingData);
//            // TextView
//            mTvUphName = findViewById(R.id.txtUphName);
//            mTvUphName.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getUsername() != null) {
//                mTvUphName.setText(User.getUser(MainHomeActivity.this).getUsername());
//            }
//
//            mTvUphFrom = findViewById(R.id.txtUphFrom);
//            mTvUphFrom.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getLivesIn() != null) {
//                mTvUphFrom.setText(User.getUser(MainHomeActivity.this).getLivesIn());
//            }
//
//            mTvUphNotification = findViewById(R.id.txtUphNotification);
//            mTvUphNotification.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphWallet = findViewById(R.id.txtUphWallet);
//            mTvUphWallet.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphBoard = findViewById(R.id.txtUphBoard);
//            mTvUphBoard.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphTemp = findViewById(R.id.txtUphTemp);
//            mTvUphTemp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mTvUphBucket = findViewById(R.id.txtUphBucket);
//            mTvUphBucket.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//
//            mtv_travelBook.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_travelBook, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService, mtv_Hm,
//                            mLlTravelWithUsData, mLlShopWithUsData, mLlEntrepreneurData, mLlOurServicesData, mLlSocializedData, mLlHighMountainData, mLlTravelWithUs);
//                }
//            });
//
//            mtv_shop_with.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_shop_with, mtv_travelBook, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService, mtv_Hm,
//                            mLlShopWithUsData, mLlTravelWithUsData, mLlEntrepreneurData, mLlOurServicesData, mLlSocializedData, mLlHighMountainData, mLlShopWithUs);
//                }
//            });
//
//            mtv_entrepreneur.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_entrepreneur, mtv_shop_with, mtv_travelBook, mtv_letsSocialize, mtv_ourService, mtv_Hm,
//                            mLlEntrepreneurData, mLlShopWithUsData, mLlTravelWithUsData, mLlOurServicesData, mLlSocializedData, mLlHighMountainData, mLlEntrepreneur);
//                }
//            });
//
//            mtv_letsSocialize.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_letsSocialize, mtv_shop_with, mtv_entrepreneur, mtv_travelBook, mtv_ourService, mtv_Hm,
//                            mLlSocializedData, mLlShopWithUsData, mLlEntrepreneurData, mLlOurServicesData, mLlTravelWithUsData, mLlHighMountainData, mLlSocialized);
//                }
//            });
//
//            mtv_ourService.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toToggleMenuItem(mtv_ourService, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_travelBook, mtv_Hm,
//                            mLlOurServicesData, mLlShopWithUsData, mLlEntrepreneurData, mLlTravelWithUsData, mLlSocializedData, mLlHighMountainData, mLlOurServices);
//                }
//            });
//            mtv_Hm.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    toToggleMenuItem(mtv_Hm, mtv_ourService, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_travelBook,
//                            mLlHighMountain, mLlOurServicesData, mLlShopWithUsData, mLlEntrepreneurData, mLlTravelWithUsData, mLlSocializedData, mLlHighMountain);
//                }
//            });
//        } catch (Exception | Error e) {
//            e.printStackTrace();FirebaseCrash.report(e);();
//        }
//    }

//09/04/2018

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

//    private void toToggleMenuItem(TextView mTvMain, TextView mTv1, TextView mTv2, TextView mTv3, TextView mTv4, TextView mTv5,
//                                  LinearLayout mLlMain, LinearLayout mLl1, LinearLayout mLl2, LinearLayout mLl3, LinearLayout mLl4, LinearLayout mLl5, LinearLayout mMainLbl) {
//        if (mLlMain.getVisibility() == View.GONE) {
//            mTvMain.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collaspe_, 0);
//            mTv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//
//            mLl1.setVisibility(View.GONE);
//            mLl2.setVisibility(View.GONE);
//            mLl3.setVisibility(View.GONE);
//            mLl4.setVisibility(View.GONE);
//            mLl5.setVisibility(View.GONE);
//
//            mLlOurServices.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlSocialized.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlEntrepreneur.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlShopWithUs.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlTravelWithUs.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlHighMountain.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//
//            mLlMain.setVisibility(View.VISIBLE);
//
//            mMainLbl.setBackground(ContextCompat.getDrawable(MainHomeActivity.this, R.drawable.sel));
//
//        } else {
//            mTvMain.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//            mTv5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_, 0);
//
//            mLl1.setVisibility(View.GONE);
//            mLl2.setVisibility(View.GONE);
//            mLl3.setVisibility(View.GONE);
//            mLl4.setVisibility(View.GONE);
//            mLl5.setVisibility(View.GONE);
//            mLlMain.setVisibility(View.GONE);
//
//            mLlOurServices.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlSocialized.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlEntrepreneur.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlShopWithUs.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlTravelWithUs.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//            mLlHighMountain.setBackgroundColor(ContextCompat.getColor(MainHomeActivity.this, R.color.white));
//        }
//    }
//
//    TextView mtv_travelBook, mtv_shop_with, mtv_entrepreneur, mtv_letsSocialize, mtv_ourService, mtv_Hm,
//            m1tv_travelWithUs, m1tv_destinations, m1tv_planTrip, m1tv_activities, m1tv_theme, m1tv_rentouts, m1tv_findGuide, m1tv_travelBible, m1tv_nearBy,
//            m2tv_allProducts, m2tv_giftCards,
//            m3tv_referAFriend, m3tv_beAGuide, m3tv_startBlogging, m3tv_giveRentouts,
//            m4tv_letsBarter, m4tv_findNeighbour, m4tv_letsDiscuss, m4tv_letsTravel, m4tv_getHelp,
//            m5tv_distanceCalculator, m5tv_trekkingRoute, m5tv_currencyConverter, m5tv_languageTranslator, m5tv_travelBudget, m5tv_offlineMaps,
//            mtv_account, mtv_contact_us, mtv_faqs, mtv_more,