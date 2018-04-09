package com.hm.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.activity.PackageDetailActivity;
import com.hm.application.common.MyBucketList;
import com.hm.application.model.AppConstants;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class PackageSectionViewPagerAdapter extends PagerAdapter {

    private Context context;
    private JSONArray data;
    private String fromTo;
    private RelativeLayout mRlTravel1, mRlTravel2, mRlTravel3;
    private ImageView mIvTravelPic, mIvAddToBL;
    private TextView mTxtTravelLoc, mTxtTravelPrice, mTxtTravelTitle;
    private RatingBar mRbTravel;
    private LinearLayout mLlTbTimerMain, mLlTbTimer;
    private Button btnUnlockNow;
    private ImageView mIvTbTimer;
    private TextView mTxtTbTimer;
    private boolean timerStatus;

    public PackageSectionViewPagerAdapter(Context ctx, JSONArray array, String from, boolean status) {
        context = ctx;
        data = array;
        fromTo = from;
        timerStatus = status;
    }

    @Override
    public int getCount() {
        return data != null ? data.length() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        try {

            View item = LayoutInflater.from(context).inflate(R.layout.place_info_item_layout, container, false);

            if (item != null) {
                mRlTravel1 = item.findViewById(R.id.rlTravel1);
                mRlTravel2 = item.findViewById(R.id.rlTravel2);
                mRlTravel3 = item.findViewById(R.id.rlTravel3);

                mIvTravelPic = item.findViewById(R.id.imgTravel);
                mIvAddToBL = item.findViewById(R.id.ivAddToBL);

                mTxtTravelLoc = item.findViewById(R.id.txtPlace);
                mTxtTravelLoc.setTypeface(HmFonts.getRobotoRegular(context));

                mTxtTravelPrice = item.findViewById(R.id.txtPrice);
                mTxtTravelPrice.setTypeface(HmFonts.getRobotoRegular(context));

                mTxtTravelTitle = item.findViewById(R.id.txtTagName);
                mTxtTravelTitle.setTypeface(HmFonts.getRobotoRegular(context));

                mRbTravel = item.findViewById(R.id.rating_br);

                // Timer layout
                mLlTbTimerMain = item.findViewById(R.id.llTbTimerMain);
                mLlTbTimer = item.findViewById(R.id.llTbTimer);

                btnUnlockNow = item.findViewById(R.id.btnUnlockNow);

                mIvTbTimer = item.findViewById(R.id.ivTbTimer);

                mTxtTbTimer = item.findViewById(R.id.txtTbTimer);
                mTxtTbTimer.setTypeface(HmFonts.getRobotoRegular(context));

                mIvAddToBL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (fromTo.equals(context.getString(R.string.str_package_info))) {
                                MyBucketList.toAddITemInBucketList(context, context.getString(R.string.str_package), data.getJSONObject(position).getString(context.getString(R.string.str_id)));
                            } else if (fromTo.equals(context.getString(R.string.str_rentout_info))) {
                                MyBucketList.toAddITemInBucketList(context, context.getString(R.string.str_rentout), data.getJSONObject(position).getString(context.getString(R.string.str_id)));
                            } else if (fromTo.equals(context.getString(R.string.str_activity_info))) {
                                MyBucketList.toAddITemInBucketList(context, context.getString(R.string.str_activity), data.getJSONObject(position).getString(context.getString(R.string.str_id)));
                            } else if (fromTo.equals(context.getString(R.string.str_destination_info))) {
                                MyBucketList.toAddITemInBucketList(context, context.getString(R.string.str_destination), data.getJSONObject(position).getString(context.getString(R.string.str_id)));
                            } else if (fromTo.equals(context.getString(R.string.str_theme_info))){
                                MyBucketList.toAddITemInBucketList(context, context.getString(R.string.str_theme), data.getJSONObject(position).getString(context.getString(R.string.str_id)));
                            } else {
                                MyBucketList.toAddITemInBucketList(context, context.getString(R.string.str_package), data.getJSONObject(position).getString(context.getString(R.string.str_id)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                if (timerStatus) {
                    mLlTbTimerMain.setVisibility(View.VISIBLE);
                    mLlTbTimer.setVisibility(View.VISIBLE);
                    btnUnlockNow.setVisibility(View.VISIBLE);
                    mIvTbTimer.setVisibility(View.VISIBLE);
                    mTxtTbTimer.setVisibility(View.VISIBLE);
                } else {
                    mLlTbTimerMain.setVisibility(View.GONE);
                    mLlTbTimer.setVisibility(View.GONE);
                    btnUnlockNow.setVisibility(View.GONE);
                    mIvTbTimer.setVisibility(View.GONE);
                    mTxtTbTimer.setVisibility(View.GONE);
                }

                LayerDrawable star = (LayerDrawable) mRbTravel.getProgressDrawable();
                star.getDrawable(2).setColorFilter(ResourcesCompat.getColor(context.getResources(), R.color.light_orange2, null), PorterDuff.Mode.SRC_ATOP);
                star.getDrawable(0).setColorFilter(ResourcesCompat.getColor(context.getResources(), R.color.light2, null), PorterDuff.Mode.SRC_ATOP);
                star.getDrawable(1).setColorFilter(ResourcesCompat.getColor(context.getResources(), R.color.light_orange2, null), PorterDuff.Mode.SRC_ATOP);

                if (!data.getJSONObject(position).isNull(context.getString(R.string.str_title))) {
                    mTxtTravelTitle.setText(CommonFunctions.firstLetterCaps(data.getJSONObject(position).getString(context.getString(R.string.str_title))));
                }
                if (!data.getJSONObject(position).isNull(context.getString(R.string.str_price))) {
                    mTxtTravelPrice.setText(CommonFunctions.firstLetterCaps(context.getString(R.string.str_lbl_rs) + " " + data.getJSONObject(position).getString(context.getString(R.string.str_price))));
                }
                if (!data.getJSONObject(position).isNull(context.getString(R.string.str_destination_))) {
                    mTxtTravelLoc.setText(CommonFunctions.firstLetterCaps(data.getJSONObject(position).getString(context.getString(R.string.str_destination_))));
                }
                if (!data.getJSONObject(position).isNull(context.getString(R.string.str_package_img_url))) {
                    Picasso.with(context)
                            .load(data.getJSONObject(position).getString(context.getString(R.string.str_package_img_url)).trim().split(",")[0].trim().replaceAll("\\s", "%20"))
                            .into(mIvTravelPic);

                } else if (!data.getJSONObject(position).isNull(context.getString(R.string.str_image_url))) {
                    Picasso.with(context)
                            .load(data.getJSONObject(position).getString(context.getString(R.string.str_image_url)).trim().trim().replaceAll("\\s", "%20"))
                            .into(mIvTravelPic);
                }

                mRlTravel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, PackageDetailActivity.class).putExtra(AppConstants.DETAIL_TAG, fromTo));

                    }
                });
                container.addView(item);
                return item;
            } else {
                return null;
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            return null;
        }
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return (0.90f);
    }
}