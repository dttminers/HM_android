package com.hm.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hm.application.fragments.TBTravelWithUsFragment;
import com.hm.application.fragments.UserFollowersListFragment;
import com.hm.application.fragments.UserFollowingListFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.AppDataStorage;
import com.hm.application.model.User;
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

    private TabLayout tabLayout;
    private DuoDrawerLayout drawer;
    private DuoMenuView mDuoMenuView;
    private Toolbar toolbar;
    private TextView mtxtUphName, mtxtUphFrom, mtxtUphNotification, mtxtUphWallet, mtxtUphBoard, mtxtUphTemp, mtxtUphBucket;

    private LinearLayout mLlOurServicesData, mLlSocializedData, mLlEntrepreneurData, mLlShopWithUsData, mLlTravelWithUsData, mLlHighMountainData,
            mLlOurServices, mLlSocialized, mLlEntrepreneur, mLlShopWithUs, mLlTravelWithUs, mLlHighMountain,
            mllUserProHead, mllUserProHead1, mllUserProHead2;
    private CircleImageView mCivDrawerMenuProfilePic, mCivMenuItemProfilePic;
    private RatingBar mrbUphRatingData;

    ExpandableListView elv;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon_dark_grey);

        AppDataStorage.getUserInfo(MainHomeActivity.this);
        Log.d("HmApp", " MainHomeActivity " + User.getUser(MainHomeActivity.this).getUid());
        UserData.toGetUserData(MainHomeActivity.this);
        AppDataStorage.getUserInfo(MainHomeActivity.this);

        drawer = findViewById(R.id.drawer);
        mDuoMenuView = (DuoMenuView) drawer.getMenuView();

        tabLayout = findViewById(R.id.tbHome);
        tabLayout.getChildAt(0).setSelected(true);

        toolbar.setNavigationIcon(R.drawable.menu_icon);

        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();


        //user_profile_header
        //LinerLayout
        mllUserProHead = findViewById(R.id.llUserProHead);
        mllUserProHead1 = findViewById(R.id.llUserProHead1);
        mllUserProHead2 = findViewById(R.id.llUserProHead2);
        //ImageView
        mCivDrawerMenuProfilePic = findViewById(R.id.imgUph);
        Log.d("HmApp", " USERPicPath " + User.getUser(MainHomeActivity.this).getPicPath());
        if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
            Picasso.with(MainHomeActivity.this)
                    .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
                    .into(mCivDrawerMenuProfilePic);
        }
        //RatingBar
        mrbUphRatingData = findViewById(R.id.rbUphRatingData);
        // TextView
        mtxtUphName = findViewById(R.id.txtUphName);
        mtxtUphName.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
        if (User.getUser(MainHomeActivity.this).getUsername() != null) {
            mtxtUphName.setText(User.getUser(MainHomeActivity.this).getUsername());
        }

        mtxtUphFrom = findViewById(R.id.txtUphFrom);
        mtxtUphFrom.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
        if (User.getUser(MainHomeActivity.this).getLivesIn() != null) {
            mtxtUphFrom.setText(User.getUser(MainHomeActivity.this).getLivesIn());
        }

        mtxtUphNotification = findViewById(R.id.txtUphNotification);
        mtxtUphNotification.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

        mtxtUphWallet = findViewById(R.id.txtUphWallet);
        mtxtUphWallet.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

        mtxtUphBoard = findViewById(R.id.txtUphBoard);
        mtxtUphBoard.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

        mtxtUphTemp = findViewById(R.id.txtUphTemp);
        mtxtUphTemp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

        mtxtUphBucket = findViewById(R.id.txtUphBucket);
        mtxtUphBucket.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));

        // get the listview
        elv = findViewById(R.id.elv);

        // preparing list data
        prepareListData();

        // setting list adapter
        elv.setAdapter(new MenuListAdapter(this, listDataHeader, listDataChild));

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                CommonFunctions.toDisplayToast(
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), MainHomeActivity.this);

                toSelectFragment(listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                int index = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);
                return false;
            }
        });
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // Listview Group collasped listener
        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                CommonFunctions.toDisplayToast(
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        MainHomeActivity.this);
            }
        });

        replacePage(new Main_HomeFragment());

//        menuItemBinding();

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

    private void toSelectFragment(String s) {
        try {
            switch (s) {
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
        }
    }

    private void prepareListData() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        // Adding header data
        listDataHeader.add("Travel With Us");
        listDataHeader.add("Travel Bible");
        listDataHeader.add("Shop With Us");
        listDataHeader.add("Be An Entrepreneur");
        listDataHeader.add("Lets Socialise");
        listDataHeader.add("Other Services");
        listDataHeader.add("Entertainment");
        listDataHeader.add("Budget");
        listDataHeader.add("High Mountains");
        listDataHeader.add("Help Us Improve");
//        listDataHeader.add("Logout");

        // Adding child data
        List<String> menu1 = new ArrayList<String>();
        menu1.add("Travel Packages");
        menu1.add("Plan A Trip");
        menu1.add("Customise A Trip");
        menu1.add("All Destinations");
        menu1.add("Travel Themes");
        menu1.add("Activites");
        menu1.add("Rentouts");
        menu1.add("Find A Guide");
        menu1.add("Near By");
        menu1.add("Trip Care");

        List<String> menu2 = new ArrayList<String>();
        menu2.add("Travel Bogs");
        menu2.add("Travel Dairies");
        menu2.add("Upload A blog/ Diary");

        List<String> menu3 = new ArrayList<String>();
        menu3.add("All Products");
        menu3.add("Products by Travellers");
        menu3.add("Gift Card");
        menu3.add("Favourite");
        List<String> menu4 = new ArrayList<String>();
        menu4.add("Refer A Friend");
        menu4.add("Be a Guide");
        menu4.add("Start Blogging");
        menu4.add("My Rentouts");
        menu4.add("My Products");
        menu4.add("FoTostock");
        menu4.add("Bid your products");
        List<String> menu5 = new ArrayList<String>();
        menu5.add("Let's Barter");
        menu5.add("Let's Discuss");
        menu5.add("Let's Travel");
        menu5.add("Get a help");
        menu5.add("Know your neighbourhood");
        List<String> menu6 = new ArrayList<String>();
        menu6.add("Maps");
        menu6.add("Distance Calculator");
        menu6.add("Trekking Routes");
        menu6.add("Offline Road Maps");
        menu6.add("Location Tracker");
        List<String> menu7 = new ArrayList<String>();
        menu7.add("Music");
        menu7.add("Movies");
        menu7.add("Travel Magazines");
        menu7.add("Travel Boook");
        menu7.add("Discover the Best in the World");
        menu7.add("Language Translator");
        menu7.add("Learn the Language");
        List<String> menu8 = new ArrayList<String>();
        menu8.add("Trip Budget/ Money Management");
        menu8.add("Past Trip Accounts");
        menu8.add("Currency Converter");
        List<String> menu9 = new ArrayList<String>();
        menu9.add("About Us");
        menu9.add("CSR");
        menu9.add("Send Feedback");
        menu9.add("Contact Us");
        menu9.add("Join Team HM");
        menu9.add("Rate Us");
        List<String> menu10 = new ArrayList<String>();
        menu10.add("Travel Support");
        menu10.add("Blog Support");
        menu10.add("Shop Support");
        menu10.add("Entrepreneurial Support");
        menu10.add("Payment Support");
        menu10.add("Other");
//        List<String> menu11 = new ArrayList<String>();
//        menu11.add("Customer Support");
//        menu11.add("User - User Chat");
//        menu11.add("Feed Page - Home Page");
//        menu11.add("HM Team - User ");
//        menu11.add("");
//        menu11.add("");
//        menu11.add("");
//        menu11.add("");
//        menu11.add("");


        listDataChild.put(listDataHeader.get(0), menu1);
        listDataChild.put(listDataHeader.get(1), menu2);
        listDataChild.put(listDataHeader.get(2), menu3);
        listDataChild.put(listDataHeader.get(3), menu4);
        listDataChild.put(listDataHeader.get(4), menu5);
        listDataChild.put(listDataHeader.get(5), menu6);
        listDataChild.put(listDataHeader.get(6), menu7);
        listDataChild.put(listDataHeader.get(7), menu8);
        listDataChild.put(listDataHeader.get(8), menu9);
        listDataChild.put(listDataHeader.get(9), menu10);
//        listDataChild.put(null, menu10);
//        listDataChild.put(listDataHeader.get(10), menu11);

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
//                PlanTrip.toUpdateMyPost(MainHomeActivity.this);
                Toast.makeText(MainHomeActivity.this, " Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_user_profile:
//                replacePage(new UserOptionsFragment());
                startActivity(new Intent(MainHomeActivity.this, UserOptionsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
//        drawer.closeDrawer(GravityCompat.START);
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
//            m1tv_travelWithUs.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_travelWithUs.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelWithUsFragment());
//                }
//            });
//
//            // Destinations
//            m1tv_destinations = header.findViewById(R.id.tv_dest);
//            m1tv_destinations.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_destinations.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBDestinationsFragment());
//                }
//            });
//
//            // Plan Trip
//            m1tv_planTrip = header.findViewById(R.id.tv_plan);
//            m1tv_planTrip.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_planTrip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBPlanTripFragment());
//                }
//            });
//
//            // Activities
//            m1tv_activities = header.findViewById(R.id.tv_activity);
//            m1tv_activities.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_activities.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBActivitiesFragment());
//                }
//            });
//
//            // Theme
//            m1tv_theme = header.findViewById(R.id.tv_theme);
//            m1tv_theme.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_theme.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBThemeFragment());
//                }
//            });
//
//            //Rentouts
//            m1tv_rentouts = header.findViewById(R.id.tv_rentout1);
//            m1tv_rentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_rentouts.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBRentoutsFragment());
//                }
//            });
//
//            //Find Guide
//            m1tv_findGuide = header.findViewById(R.id.tv_fguide);
//            m1tv_findGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_findGuide.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBFindGuideFragment());
//                }
//            });
//
//            //Travel Bible
//            m1tv_travelBible = header.findViewById(R.id.tv_tbible);
//            m1tv_travelBible.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_travelBible.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelBibleFragment());
//                }
//            });
//
//            //Near By
//            m1tv_nearBy = header.findViewById(R.id.tv_near);
//            m1tv_nearBy.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
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
//            m2tv_allProducts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Gift Cards
//            m2tv_giftCards = header.findViewById(R.id.tv_gift_card);
//            m2tv_giftCards.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Be Entrepreneur
//            mtv_entrepreneur = header.findViewById(R.id.tv_entrepreneur);
//            mtv_entrepreneur.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlEntrepreneurData = header.findViewById(R.id.ll_entrepreneur);
//
//            //Refer A Friend
//            m3tv_referAFriend = header.findViewById(R.id.tv_refer);
//            m3tv_referAFriend.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            //Be A Guide
//            m3tv_beAGuide = header.findViewById(R.id.tv_guide);
//            m3tv_beAGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Start Blogging
//            m3tv_startBlogging = header.findViewById(R.id.tv_start_blog);
//            m3tv_startBlogging.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Give Rentouts
//            m3tv_giveRentouts = header.findViewById(R.id.tv_rentout2);
//            m3tv_giveRentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Socialize
//            mtv_letsSocialize = header.findViewById(R.id.tv_socialize);
//            mtv_letsSocialize.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlSocializedData = header.findViewById(R.id.ll_socialize);
//
//            // Lets Barter
//            m4tv_letsBarter = header.findViewById(R.id.tv_barter);
//            m4tv_letsBarter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Find Neighbour
//            m4tv_findNeighbour = header.findViewById(R.id.tv_find_neigh);
//            m4tv_findNeighbour.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Discuss
//            m4tv_letsDiscuss = header.findViewById(R.id.tv_discuss);
//            m4tv_letsDiscuss.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Travel
//            m4tv_letsTravel = header.findViewById(R.id.tv_let_travel);
//            m4tv_letsTravel.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Get Help
//            m4tv_getHelp = header.findViewById(R.id.tv_get_help);
//            m4tv_getHelp.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Our Services
//            mtv_ourService = header.findViewById(R.id.tv_oservice);
//            mtv_ourService.setTypeface(HmFonts.getRobotoBold(MainHomeActivity.this));
//
//            mLlOurServicesData = header.findViewById(R.id.ll_our_services);
//
//            // Distance Calculator
//            m5tv_distanceCalculator = header.findViewById(R.id.tv_dis_cal);
//            m5tv_distanceCalculator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Trekking Route
//            m5tv_trekkingRoute = header.findViewById(R.id.tv_t_route);
//            m5tv_trekkingRoute.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Currency Converter
//            m5tv_currencyConverter = header.findViewById(R.id.tv_converter);
//            m5tv_currencyConverter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Language Translator
//            m5tv_languageTranslator = header.findViewById(R.id.tv_lang_translate);
//            m5tv_languageTranslator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Travel Budget
//            m5tv_travelBudget = header.findViewById(R.id.tv_budget);
//            m5tv_travelBudget.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // OfflineMaps
//            m5tv_offlineMaps = header.findViewById(R.id.tv_off_map);
//            m5tv_offlineMaps.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            //user_profile_header
//            //LinerLayout
//            mllUserProHead = header.findViewById(R.id.llUserProHead);
//            mllUserProHead1 = header.findViewById(R.id.llUserProHead1);
//            mllUserProHead2 = header.findViewById(R.id.llUserProHead2);
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
//            mrbUphRatingData = header.findViewById(R.id.rbUphRatingData);
//            // TextView
//            mtxtUphName = header.findViewById(R.id.txtUphName);
//            mtxtUphName.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getUsername() != null) {
//                mtxtUphName.setText(User.getUser(MainHomeActivity.this).getUsername());
//            }
//
//            mtxtUphFrom = header.findViewById(R.id.txtUphFrom);
//            mtxtUphFrom.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getLivesIn() != null) {
//                mtxtUphFrom.setText(User.getUser(MainHomeActivity.this).getLivesIn());
//            }
//
//            mtxtUphNotification = header.findViewById(R.id.txtUphNotification);
//            mtxtUphNotification.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphWallet = header.findViewById(R.id.txtUphWallet);
//            mtxtUphWallet.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphBoard = header.findViewById(R.id.txtUphBoard);
//            mtxtUphBoard.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphTemp = header.findViewById(R.id.txtUphTemp);
//            mtxtUphTemp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphBucket = header.findViewById(R.id.txtUphBucket);
//            mtxtUphBucket.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
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
//            e.printStackTrace();
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
//            mtv_travelBook.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            mLlTravelWithUsData = findViewById(R.id.ll_travel);
//            mLlTravelWithUs = findViewById(R.id.ll_travel_book);
//
//            // Travel With Us
//            m1tv_travelWithUs = findViewById(R.id.tv_travel_with);
//            m1tv_travelWithUs.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_travelWithUs.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelWithUsFragment());
//                }
//            });
//
//            // Destinations
//            m1tv_destinations = findViewById(R.id.tv_dest);
//            m1tv_destinations.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_destinations.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBDestinationsFragment());
//                }
//            });
//
//            // Plan Trip
//            m1tv_planTrip = findViewById(R.id.tv_plan);
//            m1tv_planTrip.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_planTrip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBPlanTripFragment());
//                }
//            });
//
//            // Activities
//            m1tv_activities = findViewById(R.id.tv_activity);
//            m1tv_activities.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_activities.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBActivitiesFragment());
//                }
//            });
//
//            // Theme
//            m1tv_theme = findViewById(R.id.tv_theme);
//            m1tv_theme.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_theme.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBThemeFragment());
//                }
//            });
//
//            //Rentouts
//            m1tv_rentouts = findViewById(R.id.tv_rentout1);
//            m1tv_rentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_rentouts.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBRentoutsFragment());
//                }
//            });
//
//            //Find Guide
//            m1tv_findGuide = findViewById(R.id.tv_fguide);
//            m1tv_findGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_findGuide.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBFindGuideFragment());
//                }
//            });
//
//            //Travel Bible
//            m1tv_travelBible = findViewById(R.id.tv_tbible);
//            m1tv_travelBible.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_travelBible.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBTravelBibleFragment());
//                }
//            });
//
//            //Near By
//            m1tv_nearBy = findViewById(R.id.tv_near);
//            m1tv_nearBy.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            m1tv_nearBy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    replacePage(new TBNearByFragment());
//                }
//            });
//
//            // Shop With Us
//            mtv_shop_with = findViewById(R.id.tv_shop_with);
//            mtv_shop_with.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            mLlShopWithUsData = findViewById(R.id.ll_shop_with);
//            mLlShopWithUs = findViewById(R.id.ll_shop_with_us);
//
//            // All Products
//            m2tv_allProducts = findViewById(R.id.tv_all_product);
//            m2tv_allProducts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Gift Cards
//            m2tv_giftCards = findViewById(R.id.tv_gift_card);
//            m2tv_giftCards.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Be Entrepreneur
//            mtv_entrepreneur = findViewById(R.id.tv_entrepreneur);
//            mtv_entrepreneur.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            mLlEntrepreneurData = findViewById(R.id.ll_entrepreneur);
//            mLlEntrepreneur = findViewById(R.id.ll_be_entrepreneur);
//
//            //Refer A Friend
//            m3tv_referAFriend = findViewById(R.id.tv_refer);
//            m3tv_referAFriend.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            //Be A Guide
//            m3tv_beAGuide = findViewById(R.id.tv_guide);
//            m3tv_beAGuide.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Start Blogging
//            m3tv_startBlogging = findViewById(R.id.tv_start_blog);
//            m3tv_startBlogging.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Give Rentouts
//            m3tv_giveRentouts = findViewById(R.id.tv_rentout2);
//            m3tv_giveRentouts.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Socialize
//            mtv_letsSocialize = findViewById(R.id.tv_socialize);
//            mtv_letsSocialize.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            mLlSocializedData = findViewById(R.id.ll_socialize);
//            mLlSocialized = findViewById(R.id.ll_lets_socialize);
//
//            // Lets Barter
//            m4tv_letsBarter = findViewById(R.id.tv_barter);
//            m4tv_letsBarter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Find Neighbour
//            m4tv_findNeighbour = findViewById(R.id.tv_find_neigh);
//            m4tv_findNeighbour.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Discuss
//            m4tv_letsDiscuss = findViewById(R.id.tv_discuss);
//            m4tv_letsDiscuss.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Lets Travel
//            m4tv_letsTravel = findViewById(R.id.tv_let_travel);
//            m4tv_letsTravel.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Get Help
//            m4tv_getHelp = findViewById(R.id.tv_get_help);
//            m4tv_getHelp.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Our Services
//            mtv_ourService = findViewById(R.id.tv_our_service);
//            mtv_ourService.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            mLlOurServicesData = findViewById(R.id.ll_our_services);
//            mLlOurServices = findViewById(R.id.ll_our_service);
//
//            // Distance Calculator
//            m5tv_distanceCalculator = findViewById(R.id.tv_dis_cal);
//            m5tv_distanceCalculator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Trekking Route
//            m5tv_trekkingRoute = findViewById(R.id.tv_t_route);
//            m5tv_trekkingRoute.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Currency Converter
//            m5tv_currencyConverter = findViewById(R.id.tv_converter);
//            m5tv_currencyConverter.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Language Translator
//            m5tv_languageTranslator = findViewById(R.id.tv_lang_translate);
//            m5tv_languageTranslator.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Travel Budget
//            m5tv_travelBudget = findViewById(R.id.tv_budget);
//            m5tv_travelBudget.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // OfflineMaps
//            m5tv_offlineMaps = findViewById(R.id.tv_off_map);
//            m5tv_offlineMaps.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            // Hm
//            mtv_Hm = findViewById(R.id.tv_Hm);
//            mtv_Hm.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//
//            mLlHighMountain = findViewById(R.id.ll_hm);
//            mLlHighMountainData = findViewById(R.id.llHighMountain);
//
//
//            //user_profile_header
//            //LinerLayout
//            mllUserProHead = findViewById(R.id.llUserProHead);
//            mllUserProHead1 = findViewById(R.id.llUserProHead1);
//            mllUserProHead2 = findViewById(R.id.llUserProHead2);
//            //ImageView
//            mCivDrawerMenuProfilePic = findViewById(R.id.imgUph);
//            Log.d("HmApp", " USERPicPath " + User.getUser(MainHomeActivity.this).getPicPath());
//            if (User.getUser(MainHomeActivity.this).getPicPath() != null) {
//                Picasso.with(MainHomeActivity.this)
//                        .load(AppConstants.URL + User.getUser(MainHomeActivity.this).getPicPath().replaceAll("\\s", "%20"))
//                        .into(mCivDrawerMenuProfilePic);
//            }
//            //RatingBar
//            mrbUphRatingData = findViewById(R.id.rbUphRatingData);
//            // TextView
//            mtxtUphName = findViewById(R.id.txtUphName);
//            mtxtUphName.setTypeface(HmFonts.getRobotoMedium(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getUsername() != null) {
//                mtxtUphName.setText(User.getUser(MainHomeActivity.this).getUsername());
//            }
//
//            mtxtUphFrom = findViewById(R.id.txtUphFrom);
//            mtxtUphFrom.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//            if (User.getUser(MainHomeActivity.this).getLivesIn() != null) {
//                mtxtUphFrom.setText(User.getUser(MainHomeActivity.this).getLivesIn());
//            }
//
//            mtxtUphNotification = findViewById(R.id.txtUphNotification);
//            mtxtUphNotification.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphWallet = findViewById(R.id.txtUphWallet);
//            mtxtUphWallet.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphBoard = findViewById(R.id.txtUphBoard);
//            mtxtUphBoard.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphTemp = findViewById(R.id.txtUphTemp);
//            mtxtUphTemp.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
//
//            mtxtUphBucket = findViewById(R.id.txtUphBucket);
//            mtxtUphBucket.setTypeface(HmFonts.getRobotoRegular(MainHomeActivity.this));
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
//            e.printStackTrace();
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