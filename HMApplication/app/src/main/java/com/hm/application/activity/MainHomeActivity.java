package com.hm.application.activity;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.crash.FirebaseCrash;
import com.hm.application.R;
import com.hm.application.common.UserData;
import com.hm.application.fragments.Main_ChatFragment;
import com.hm.application.fragments.Main_FriendRequestFragment;
import com.hm.application.fragments.Main_HomeFragment;
import com.hm.application.fragments.Main_NotificationFragment;
import com.hm.application.fragments.Main_Tab3Fragment;
import com.hm.application.fragments.UserTab1Fragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
import com.hm.application.newtry.Share.ShareActivity;
import com.hm.application.services.GCMRegistrationIntentService;
import com.hm.application.services.MyFirebaseInstanceIDService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainHomeActivity extends AppCompatActivity {

    //Creating a broadcast receiver for gcm registration
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private TabLayout mTbHome;
    private CircleImageView mCivMenuItemProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main_home);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_left_black_24dp));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//                Spannable text = new SpannableString(getSupportActionBar().getTitle());
//                text.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.dark_pink3)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                getSupportActionBar().setTitle(text);
            }

            new MyFirebaseInstanceIDService().onTokenRefresh();

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

//            //Initializing our broadcast receiver
//            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//
//                //When the broadcast received
//                //We are sending the broadcast from GCMRegistrationIntentService
//
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
//                        String token = intent.getStringExtra("token");
//                        Log.d("Hmapp", " GCM Token : "  + token);
////                        Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();
////                    } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
////                        Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
////                    } else {
////                        Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
//                    }
//                }
//            };



        } catch (Exception | Error e) {
            e.printStackTrace();
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
//                    startActivity(new Intent(MainHomeActivity.this, ShareActivity.class));
                    break;
                case 3:
                    replacePage(new Main_NotificationFragment());
                    break;
                case 4:
                    replacePage(new Main_ChatFragment());
//                    startActivity(new Intent(MainHomeActivity.this, CommentActivity.class));
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

    public void replacePage(Fragment fragment) {
        Log.d("HmApp", "MainHome  fragment " + fragment.getTag() + " : " + fragment.getId() + ": " + fragment.getClass().getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        EditText editText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.BLACK);

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
                startActivity(new Intent(MainHomeActivity.this, UserInfoActivity.class));
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
//                Toast.makeText(MainHomeActivity.this, " Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_user_profile:
                startActivity(new Intent(MainHomeActivity.this, UserInfoActivity.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
}

/*  17/04/2018

//            //Checking play service is available or not
//            int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
//
//            //if play service is not available
//            if(ConnectionResult.SUCCESS != resultCode) {
//                //If play service is supported but not installed
//                if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                    //Displaying message that play service is not installed
//                    Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
//                    GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
//
//                    //If play service is not supported
//                    //Displaying an error message
//                } else {
//                    Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
//                }
//
//                //If play service is available
//            } else {
//                //Starting intent to register device
//                Intent itent = new Intent(this, GCMRegistrationIntentService.class);
//                startService(itent);
//            }

public class MainHomeActivity extends AppCompatActivity {


    //    private Toolbar mToolbar;
    private TabLayout mTbHome;

    // Drawer Section
//    private DuoDrawerLayout mDuoDrawerLayout;
//    private DuoMenuView mDuoMenuView;
//    private DuoDrawerToggle mDuoDrawerToggle;

    // Menu User Information Section
    private LinearLayout mLlUserProHead, mLlUserProHead1, mLlUserProHead2;
    //    private TextView mTvUphName, mTvUphFrom, mTvUphNotification, mTvUphWallet, mTvUphBoard, mTvUphTemp, mTvUphBucket;
//    private RatingBar mRbUphRatingData;
    private CircleImageView /*mCivDrawerMenuProfilePic,*mCivMenuItemProfilePic;

//    private ExpandableListView mElv;
//    private List<String> mListDataHeader;
//    private HashMap<String, List<String>> mListDataChild;
//    private MenuListAdapter menuListAdapter;

    // Logout
//    private LinearLayout mLlLogout;
//    private TextView mTvLogout;

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

//            mToolbar = findViewById(R.id.toolbar);
//            setSupportActionBar(mToolbar);

//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(R.color.dark_pink1);
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon_dark_grey);

//            mDuoDrawerLayout = findViewById(R.id.drawer);
//            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();

            mTbHome = findViewById(R.id.tbHome);
            mTbHome.getChildAt(0).setSelected(true);

//            mToolbar.setNavigationIcon(R.drawable.menu_icon);
//
//            mDuoDrawerToggle = new DuoDrawerToggle(this, mDuoDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
//            mDuoDrawerLayout.setDrawerListener(mDuoDrawerToggle);
//            mDuoDrawerToggle.syncState();

            //user_profile_header
            //LinerLayout
            mLlUserProHead = findViewById(R.id.llUserProHead);
            mLlUserProHead1 = findViewById(R.id.llUserProHead1);
            mLlUserProHead2 = findViewById(R.id.llUserProHead2);

            //ImageView
//            mCivDrawerMenuProfilePic = findViewById(R.id.imgUph);
//            if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
//                Picasso.with(MainHomeActivity.this)
//                        .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
//                        .resize(200, 200)
//                        .error(R.color.light2)
//                        .placeholder(R.color.light)
//                        .into(mCivDrawerMenuProfilePic);
//            }
//
//            //RatingBar
//            mRbUphRatingData = findViewById(R.id.rbUphRatingData);
//            LayerDrawable star = (LayerDrawable) mRbUphRatingData.getProgressDrawable();
//            star.getDrawable(2).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.light_red, null), PorterDuff.Mode.SRC_ATOP);
//            star.getDrawable(0).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.grey5, null), PorterDuff.Mode.SRC_ATOP);
//            star.getDrawable(1).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.light_red, null), PorterDuff.Mode.SRC_ATOP);
//
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
//            // Logout
//            mLlLogout = findViewById(R.id.ll_list_grp);
//            mTvLogout = findViewById(R.id.lblListHeader);
//
//            // get the listview
//            mElv = findViewById(R.id.elv);
//
//            // preparing list data
//            prepareListData();
//
//            // setting list adapter
//            menuListAdapter = new MenuListAdapter(this, mListDataHeader, mListDataChild);
//            mElv.setAdapter(menuListAdapter);
//            setListViewHeight(mElv, 0, false);
//
//
//            mElv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                @Override
//                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                    toSelectFragment(mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosition));
//                    int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
//                    parent.setItemChecked(index, true);
//                    return false;
//                }
//            });
//
//            mElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//                @Override
//                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                    setListViewHeight(parent, groupPosition, true);
//                    return false;
//                }
//            });

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

//            mLlLogout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CommonFunctions.toLogout(MainHomeActivity.this);
//                    finish();
//                }
//            });
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
                    replacePage(new TBNearByFragment());
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

//    private void setListViewHeight(ExpandableListView listView, int group, boolean status) {
//        int totalHeight = 0;
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
//
//        for (int i = 0; i < menuListAdapter.getGroupCount(); i++) {
//
//            View groupItem = menuListAdapter.getGroupView(i, false, null, listView);
//            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//
//            totalHeight += groupItem.getMeasuredHeight();
//
//            // To Add Child View Height
//            if (status) {
//                if (((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group))) {
//                    for (int j = 0; j < menuListAdapter.getChildrenCount(i); j++) {
//                        View listItem = menuListAdapter.getChildView(i, j, false, null, listView);
//                        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//
//                        totalHeight += listItem.getMeasuredHeight();
//                    }
//                } // child view
//            }// status
//        }// end of for loop
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        int height = totalHeight + (listView.getDividerHeight() * (menuListAdapter.getGroupCount() - 1));
//        if (height < 10)
//            height = 200;
//        params.height = height;
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }

//    private void toSelectFragment(String name) {
//        try {
//            switch (name) {
//                case "Travel Packages":
//                    replacePage(new TBTravelWithUsFragment());
//                    break;
//                case "Plan A Trip":
//                    replacePage(new TBPlanTripFragment());
//                    break;
//                case "All Destinations":
//                    replacePage(new TBDestinationsFragment());
//                    break;
//                case "Travel Themes":
//                    replacePage(new TBThemeFragment());
//                    break;
//                case "Rentouts":
//                    replacePage(new TBRentoutsFragment());
//                    break;
//                case "Activites":
//                    replacePage(new TBActivitiesFragment());
//                    break;
//                case "Find A Guide":
//                    replacePage(new TBFindGuideFragment());
//                    break;
//                default:
////                    replacePage(new TBTravelWithUsFragment());
//                    break;
//            }
//        } catch (Exception | Error e) {
//            e.printStackTrace();
//            FirebaseCrash.report(e);
//        }
//    }

//    private void prepareListData() {
//
//        mListDataHeader = new ArrayList<>();
//        mListDataChild = new HashMap<>();
//
//        // Adding header data
//        mListDataHeader.add("Travel With Us");
//        mListDataHeader.add("Travel Bible");
//        mListDataHeader.add("Shop With Us");
//        mListDataHeader.add("Be An Entrepreneur");
//        mListDataHeader.add("Lets Socialise");
//        mListDataHeader.add("Other Services");
//        mListDataHeader.add("Entertainment");
//        mListDataHeader.add("Budget");
//        mListDataHeader.add("High Mountains");
//        mListDataHeader.add("Help Us Improve");
//
//        // Adding child data
//        List<String> menu1 = new ArrayList<>();
//        menu1.add("Travel Packages");
//        menu1.add("Plan A Trip");
//        menu1.add("Customise A Trip");
//        menu1.add("All Destinations");
//        menu1.add("Travel Themes");
//        menu1.add("Activites");
//        menu1.add("Rentouts");
//        menu1.add("Find A Guide");
//        menu1.add("Guide Curated Iteneraries");
//        menu1.add("Near By");
//        menu1.add("Trip Care");
//
//
//        List<String> menu2 = new ArrayList<>();
//        menu2.add("Travel Bogs");
//        menu2.add("Travel Dairies");
//        menu2.add("Upload A blog/ Diary");
//
//        List<String> menu3 = new ArrayList<>();
//        menu3.add("All Products");
//        menu3.add("Products by Travellers");
//        menu3.add("Gift Card");
//        menu3.add("Favourite");
//
//        List<String> menu4 = new ArrayList<>();
//        menu4.add("Refer A Friend");
//        menu4.add("Be a Guide");
//        menu4.add("Start Blogging");
//        menu4.add("My Rentouts");
//        menu4.add("My Products");
//        menu4.add("FoTostock");
//        menu4.add("Bid your products");
//
//        List<String> menu5 = new ArrayList<>();
//        menu5.add("Let's Barter");
//        menu5.add("Let's Discuss");
//        menu5.add("Let's Travel");
//        menu5.add("Get a help");
//        menu5.add("Know your neighbourhood");
//
//        List<String> menu6 = new ArrayList<>();
//        menu6.add("Maps");
//        menu6.add("Distance Calculator");
//        menu6.add("Trekking Routes");
//        menu6.add("Offline Road Maps");
//        menu6.add("Location Tracker");
//
//        List<String> menu7 = new ArrayList<>();
//        menu7.add("Music");
//        menu7.add("Movies");
//        menu7.add("Travel Magazines");
//        menu7.add("Travel Boook");
//        menu7.add("Discover the Best in the World");
//        menu7.add("Language Translator");
//        menu7.add("Learn the Language");
//
//        List<String> menu8 = new ArrayList<>();
//        menu8.add("Trip Budget/ Money Management");
//        menu8.add("Past Trip Accounts");
//        menu8.add("Currency Converter");
//
//        List<String> menu9 = new ArrayList<>();
//        menu9.add("About Us");
//        menu9.add("CSR");
//        menu9.add("Send Feedback");
//        menu9.add("Contact Us");
//        menu9.add("Join Team HM");
//        menu9.add("Rate Us");
//
//        List<String> menu10 = new ArrayList<>();
//        menu10.add("Travel Support");
//        menu10.add("Blog Support");
//        menu10.add("Shop Support");
//        menu10.add("Entrepreneurial Support");
//        menu10.add("Payment Support");
//        menu10.add("Other");
//
//        mListDataChild.put(mListDataHeader.get(0), menu1);
//        mListDataChild.put(mListDataHeader.get(1), menu2);
//        mListDataChild.put(mListDataHeader.get(2), menu3);
//        mListDataChild.put(mListDataHeader.get(3), menu4);
//        mListDataChild.put(mListDataHeader.get(4), menu5);
//        mListDataChild.put(mListDataHeader.get(5), menu6);
//        mListDataChild.put(mListDataHeader.get(6), menu7);
//        mListDataChild.put(mListDataHeader.get(7), menu8);
//        mListDataChild.put(mListDataHeader.get(8), menu9);
//        mListDataChild.put(mListDataHeader.get(9), menu10);
//    }

    public void replacePage(Fragment fragment) {
        Log.d("HmApp", "MainHome  fragment " + fragment.getTag() + " : " + fragment.getId() + ": " + fragment.getClass().getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
//        mDuoDrawerLayout.closeDrawer(GravityCompat.START);
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
        Log.d("HmApp", "MainHome onBackStackChanged 1 : " + getSupportFragmentManager().getBackStackEntryCount() + " : " + getSupportFragmentManager().getFragments());
//        if (mDuoDrawerLayout.isDrawerOpen(GravityCompat.START)) {
//            mDuoDrawerLayout.closeDrawer(GravityCompat.START);
//        } else
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
*/
