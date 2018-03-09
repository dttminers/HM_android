package com.hm.application.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.application.R;

public class Main_HomeFragment extends Fragment {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView mtv_header, mtv_travel_b, mtv_shop_with, mtv_entrepreneur, mtv_socialize, mtv_oservice,
            m1tv_travel_with, m1tv_dest, m1tv_plan, m1tv_activity, m1tv_theme, m1tv_rentout1, m1tv_fguide, m1tv_tbible, m1tv_near,
            m2tv_all_product, m2tv_gift_card,
            m3tv_refer, m3tv_guide, m3tv_start_blog, m3tv_rentout2,
            m4tv_barter, m4tv_find_neigh, m4tv_discuss, m4tv_let_travel, m4tv_get_help,
            m5tv_dis_cal, m5tv_t_route, m5tv_converter, m5tv_lang_translate, m5tv_budget, m5tv_off_map;
    LinearLayout mll_our_services, mll_socialize, mll_entrepreneur, mll_shop_with, mll_travel;

    public Main_HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {

            navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_menu);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Toast.makeText(getContext(), "clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();

                    switch (item.getItemId()) {
                        case R.id.home:
                            Toast.makeText(getContext(), "clicked home", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.tv_travel_book:
                            Toast.makeText(getContext(), "clicked travel book", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }
            });

            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }

            drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("High Mountain");
            actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.app_name, R.string.app_name);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            ///
            mtv_header = (TextView) getActivity().findViewById(R.id.tv_header);
            mtv_travel_b = (TextView) getActivity().findViewById(R.id.tv_travel_book);
            mtv_shop_with = (TextView) getActivity().findViewById(R.id.tv_shop_with);
            mtv_entrepreneur = (TextView) getActivity().findViewById(R.id.tv_entrepreneur);
            mtv_socialize = (TextView) getActivity().findViewById(R.id.tv_socialize);
            mtv_oservice = (TextView) getActivity().findViewById(R.id.tv_oservice);

            m1tv_travel_with = (TextView) getActivity().findViewById(R.id.tv_travel_with);
            m1tv_travel_with.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelWithUsFragment());
                }
            });
            m1tv_dest = (TextView) getActivity().findViewById(R.id.tv_dest);
            m1tv_dest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });

            m1tv_plan = (TextView) getActivity().findViewById(R.id.tv_plan);
            m1tv_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBPlanTripFragment());
                }
            });
            m1tv_activity = (TextView) getActivity().findViewById(R.id.tv_activity);
            m1tv_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBActivitiesFragment());
                }
            });
            m1tv_theme = (TextView) getActivity().findViewById(R.id.tv_theme);
            m1tv_theme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBThemeFragment());
                }
            });
            m1tv_rentout1 = (TextView) getActivity().findViewById(R.id.tv_rentout1);
            m1tv_rentout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });
            m1tv_fguide = (TextView) getActivity().findViewById(R.id.tv_fguide);
            m1tv_rentout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBDestinationsFragment());
                }
            });
            m1tv_tbible = (TextView) getActivity().findViewById(R.id.tv_tbible);
            m1tv_tbible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBTravelBibleFragment());
                }
            });
            m1tv_near = (TextView) getActivity().findViewById(R.id.tv_near);
            m1tv_near.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replacePage(new TBNearByFragment());
                }
            });

            m2tv_all_product = (TextView) getActivity().findViewById(R.id.tv_all_product);
            m2tv_gift_card = (TextView) getActivity().findViewById(R.id.tv_gift_card);

            m3tv_refer = (TextView) getActivity().findViewById(R.id.tv_refer);
            m3tv_guide = (TextView) getActivity().findViewById(R.id.tv_guide);
            m3tv_start_blog = (TextView) getActivity().findViewById(R.id.tv_start_blog);
            m3tv_rentout2 = (TextView) getActivity().findViewById(R.id.tv_rentout2);

            m4tv_barter = (TextView) getActivity().findViewById(R.id.tv_barter);
            m4tv_find_neigh = (TextView) getActivity().findViewById(R.id.tv_find_neigh);
            m4tv_discuss = (TextView) getActivity().findViewById(R.id.tv_discuss);
            m4tv_let_travel = (TextView) getActivity().findViewById(R.id.tv_let_travel);
            m4tv_get_help = (TextView) getActivity().findViewById(R.id.tv_get_help);

            m5tv_dis_cal = (TextView) getActivity().findViewById(R.id.tv_dis_cal);
            m5tv_t_route = (TextView) getActivity().findViewById(R.id.tv_t_route);
            m5tv_converter = (TextView) getActivity().findViewById(R.id.tv_converter);
            m5tv_lang_translate = (TextView) getActivity().findViewById(R.id.tv_lang_translate);
            m5tv_budget = (TextView) getActivity().findViewById(R.id.tv_budget);
            m5tv_off_map = (TextView) getActivity().findViewById(R.id.tv_off_map);


            mll_our_services = (LinearLayout) getActivity().findViewById(R.id.ll_our_services);
            mll_socialize = (LinearLayout) getActivity().findViewById(R.id.ll_socialize);
            mll_entrepreneur = (LinearLayout) getActivity().findViewById(R.id.ll_entrepreneur);
            mll_shop_with = (LinearLayout) getActivity().findViewById(R.id.ll_shop_with);
            mll_travel = (LinearLayout) getActivity().findViewById(R.id.ll_travel);

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
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flHomeContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}