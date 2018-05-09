package com.hm.application.classes;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.fragments.TimelineLikeListFragment;
import com.hm.application.fragments.UserTab1Fragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.HmFonts;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserTimeLinePost {

    private static RelativeLayout mrr_header_file, mRlNumberFile;
    private static LinearLayout mll_footer, mllNumber_file, mllNormalPost;
    private static ImageView mImgActPic, mImgMore;
    private static CircleImageView mcircle_img;
    private static TextView mtxt_label, mtxt_time_ago, mTvTimeLineId, mtxt_like, mtxt_comment, mtxt_share, mtxtNo_like, mtxtNo_comment, mtxtNo_share, mtxtData22, mtxtDataVp;
    private static ViewPager mVp;
    private static TabLayout mTl;
    private static Map<String, String> idTimeLine = new HashMap<String, String>();

    //{"activity":"post","id":"54","timeline_id":"103","post":"Travel is my hobby","like_count":"0","comment_count":"3","share_count":"0","time":"2018-04-12 01:15:28","isliked":"false"}
    //{"activity":"photo","id":"24","timeline_id":"101","image":"uploads\/2\/photos\/11-04-2018 11:09 AM_Guardians-Of-The-Galaxy-Vol-2-920x584.png","caption":"Guardian of Galaxy","like_count":"5","comment_count":"1","share_count":"0","time":"2018-04-11 00:39:03","isliked":"false"}
    //{"activity":"album","id":"1","timeline_id":"5","image":"uploads\/2\/photos\/04-04-2018 11:29 AM_ironman-cover.jpg,uploads\/1\/photos\/04-04-2018 16:06 PM_captain-america-dp-780x405.jpg","caption":"goa rocks","like_count":"0","comment_count":"1","share_count":"0","time":"0000-00-00 00:00:00","isliked":"false"}

    public static void toDisplayNormalPost(final JSONObject jsonObject, final Context context, final LinearLayout mLlPostMain, final int i, String name, final UserTab1Fragment userTab1Fragment) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                final View itemView = inflater.inflate(R.layout.tab22_list, null, false);
                // ToSetLoopId
                itemView.setTag("" + i);

                // Bind Current View
                toBindView(context, itemView);

                mllNormalPost = itemView.findViewById(R.id.llTab22Main);

                if (!jsonObject.isNull(context.getString(R.string.str_profile_pic))) {
                    Picasso.with(context)
                            .load(AppConstants.URL + jsonObject.getString(context.getString(R.string.str_profile_pic)).replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(100, 100)
                            .into(mcircle_img);
                } else if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(100, 100)
                            .into(mcircle_img);
                } else {
                    mcircle_img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.username));
                }

                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(100, 100)
                            .into(mcircle_img);
                }

                if (name != null) {
                    mtxt_label.setText(CommonFunctions.firstLetterCaps(name));
                }

                if (!jsonObject.isNull(context.getString(R.string.str_post_small))) {
                    mtxtData22.setText(jsonObject.getString(context.getString(R.string.str_post_small)));
                } else if (!jsonObject.isNull(context.getString(R.string.str_caption))) {
                    mtxtData22.setText(jsonObject.getString(context.getString(R.string.str_caption)));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_friend_like))) {
                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_friend_like)) + " and " + jsonObject.getString(context.getString(R.string.str_like_count)) + " others");
                } else {
                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count)));
                }
//                if (!jsonObject.isNull(context.getString(R.string.str_friend_like))) {
//                    if (jsonObject.getString(context.getString(R.string.str_like_count)).equals("0")){
//                        mtxtNo_like.setVisibility(View.GONE);
//                    }else {
//                        mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_friend_like))+" and "+ jsonObject.getString(context.getString(R.string.str_like_count))+" others");
//                        mtxtNo_like.setVisibility(View.VISIBLE);
//                    }
//                }else {
//                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count)));
//                }

                if (jsonObject.getString(context.getString(R.string.str_like_count)).equals("0")){
                    mtxtNo_like.setVisibility(View.GONE);
                }else {
                    if (!jsonObject.isNull(context.getString(R.string.str_friend_like))) {
                        mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_friend_like))+" and "+ jsonObject.getString(context.getString(R.string.str_like_count))+" others");
                        mtxtNo_like.setVisibility(View.VISIBLE);
                    }else {
                        mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count)));
                    }
                }

                if (!jsonObject.isNull(context.getString(R.string.str_comment_count))) {
                    mtxtNo_comment.setText(jsonObject.getString(context.getString(R.string.str_comment_count)) + " " + context.getResources().getString(R.string.str_comment));
                }

                if (!jsonObject.isNull(context.getString(R.string.str_share_count))) {
                    mtxtNo_share.setText(jsonObject.getString(context.getString(R.string.str_share_count)) + " " + context.getResources().getString(R.string.str_share));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_image))) {
                    Picasso.with(context)
                            .load(AppConstants.URL + jsonObject.getString(context.getString(R.string.str_image)).replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(500, 500)
                            .into(mImgActPic);
                } else {
                    mImgActPic.setVisibility(View.GONE);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_time))) {
                    mtxt_time_ago.setText(CommonFunctions.toSetDate(jsonObject.getString(context.getString(R.string.str_time))));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_is_liked))) {
                    if (jsonObject.getString(context.getString(R.string.str_is_liked)).toLowerCase().equals(context.getString(R.string.str_true))) {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                        mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.blue));
                    } else {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                        mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.black));
                    }
                } else {
                    mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                    mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.black));
                }

                if (!jsonObject.isNull(context.getString(R.string.str_timeline_id_))) {
                    idTimeLine.put(String.valueOf(i), jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                }

                mllNormalPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userTab1Fragment.toCallSinglePostData(Integer.parseInt(itemView.getTag().toString()), "Single");
                    }
                });

                mtxtNo_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallLikeListUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });

                mtxt_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyPost.toLikeUnlikePost(context, idTimeLine.get(itemView.getTag().toString()), mLlPostMain, itemView.getTag().toString(), null, null);
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }

                    }
                });

                mtxt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallCommentUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });

                mtxtNo_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallCommentUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });

                mtxt_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CommonFunctions.toShareData(context,
                                    context.getString(R.string.app_name),
                                    jsonObject.getString(context.getString(R.string.str_post_small)),
                                    idTimeLine.get(itemView.getTag().toString()), null);
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }
                    }
                });
                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    public static void toDisplayPhotoPost(final JSONObject jsonObject, final Context context, final LinearLayout mLlPostMain, final int i, String name, final UserTab1Fragment userTab1Fragment) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                final View itemView = inflater.inflate(R.layout.viewpager_post_layout, null, false);
                itemView.setTag("" + i);
                toBindView(context, itemView);

                mllNormalPost = itemView.findViewById(R.id.llMainVpPost);

                if (!jsonObject.isNull(context.getString(R.string.str_profile_pic))) {
                    Picasso.with(context)
                            .load(AppConstants.URL + jsonObject.getString(context.getString(R.string.str_profile_pic)).replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(100, 100)
                            .into(mcircle_img);
                } else if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(100, 100)
                            .into(mcircle_img);
                } else {
                    mcircle_img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.username));
                }

                if (name != null) {
                    mtxt_label.setText(CommonFunctions.firstLetterCaps(name));
                }

                if (!jsonObject.isNull(context.getString(R.string.str_post_small))) {
                    mtxtDataVp.setText(jsonObject.getString(context.getString(R.string.str_post_small)));
                } else if (!jsonObject.isNull(context.getString(R.string.str_caption))) {
                    mtxtDataVp.setText(jsonObject.getString(context.getString(R.string.str_caption)));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_friend_like))) {
                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_friend_like)) + " and " + jsonObject.getString(context.getString(R.string.str_like_count)) + " others");
                } else {
                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count)));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_comment_count))) {
                    mtxtNo_comment.setText(jsonObject.getString(context.getString(R.string.str_comment_count)) + " " + context.getResources().getString(R.string.str_comment));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_share_count))) {
                    mtxtNo_share.setText(jsonObject.getString(context.getString(R.string.str_share_count)) + " " + context.getResources().getString(R.string.str_share));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_time))) {
                    mtxt_time_ago.setText(CommonFunctions.toSetDate(jsonObject.getString(context.getString(R.string.str_time))));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_image))) {
                    mVp.setAdapter(
                            new SlidingImageAdapter(
                                    context,
                                    jsonObject.getString(context.getString(R.string.str_image)).split(","),
                                    itemView.getTag().toString(),
                                    userTab1Fragment, mTl
                            )
                    );
                    mTl.setupWithViewPager(mVp);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_is_liked))) {
                    if (jsonObject.getString(context.getString(R.string.str_is_liked)).toLowerCase().equals(context.getString(R.string.str_true))) {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                        mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.blue));
                    } else {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                        mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.black));
                    }
                } else {
                    mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                    mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.black));
                }

                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                            .error(R.color.light2)
                            .placeholder(R.color.light)
                            .resize(500, 500)
                            .into(mcircle_img);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_timeline_id_))) {
                    idTimeLine.put(String.valueOf(i), jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                }

                mllNormalPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userTab1Fragment.toCallSinglePostData(Integer.parseInt(itemView.getTag().toString()), "Multiple");
                    }
                });

                mtxt_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyPost.toLikeUnlikePost(context, idTimeLine.get(itemView.getTag().toString()), mLlPostMain, itemView.getTag(), null, null);
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }
                    }
                });

                mtxtNo_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallLikeListUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });

                mtxt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallCommentUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });
                mtxtNo_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallCommentUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });

                mtxt_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CommonFunctions.toShareData(context,
                                    context.getString(R.string.app_name),
                                    jsonObject.getString(context.getString(R.string.str_post_small)),
                                    idTimeLine.get(itemView.getTag().toString()), null);
                        } catch (Exception | Error e) {
                            e.printStackTrace(); Crashlytics.logException(e);

                        }
                    }
                });
                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    private static void toCallCommentUi(Context context, String timeLineId) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.TIMELINE_ID, timeLineId);
            CommentFragment cm = new CommentFragment();
            cm.setArguments(bundle);
            ((MainHomeActivity) context).replacePage(cm);
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    private static void toCallLikeListUi(Context context, String timeLineId) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.TIMELINE_ID, timeLineId);
            TimelineLikeListFragment time = new TimelineLikeListFragment();
            time.setArguments(bundle);
            ((MainHomeActivity) context).replacePage(time);
        } catch (Exception | Error e) {
            e.printStackTrace(); Crashlytics.logException(e);

        }
    }

    private static void toBindView(final Context context, View itemView) {
        // header file
        mrr_header_file = itemView.findViewById(R.id.rr_header_file);
        mcircle_img = itemView.findViewById(R.id.circle_img);
        mImgMore = itemView.findViewById(R.id.imgMore);
        mImgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common_Alert_box.toPostMoreIcon(context);
            }
        });

        mtxt_label = itemView.findViewById(R.id.txt_label);
        mtxt_label.setTypeface(HmFonts.getRobotoRegular(context));

        mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
        mtxt_time_ago.setTypeface(HmFonts.getRobotoRegular(context));

        mTvTimeLineId = itemView.findViewById(R.id.tvTimelineId);
        mTvTimeLineId.setTypeface(HmFonts.getRobotoRegular(context));

        // footer file
        mll_footer = itemView.findViewById(R.id.ll_footer);

        mtxt_like = itemView.findViewById(R.id.txt_like);
        mtxt_like.setTypeface(HmFonts.getRobotoRegular(context));

        mtxt_comment = itemView.findViewById(R.id.txt_comment);
        mtxt_comment.setTypeface(HmFonts.getRobotoRegular(context));

        mtxt_share = itemView.findViewById(R.id.txt_share);
        mtxt_share.setTypeface(HmFonts.getRobotoRegular(context));

        // number file
        mllNumber_file = itemView.findViewById(R.id.llNumber_file);
        mRlNumberFile = itemView.findViewById(R.id.rlNumber_file);

        mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
        mtxtNo_like.setTypeface(HmFonts.getRobotoRegular(context));

        mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
        mtxtNo_comment.setTypeface(HmFonts.getRobotoRegular(context));

        mtxtNo_share = itemView.findViewById(R.id.txtNo_share);
        mtxtNo_share.setTypeface(HmFonts.getRobotoRegular(context));

        mtxtData22 = itemView.findViewById(R.id.txtPostData22);
        if (mtxtData22 != null) {
            mtxtData22.setTypeface(HmFonts.getRobotoRegular(context));
        }

        mtxtDataVp = itemView.findViewById(R.id.txtHs2Title);
        if (mtxtDataVp != null) {
            mtxtDataVp.setTypeface(HmFonts.getRobotoRegular(context));
        }

        mtxtNo_like.setText("0");
        mtxtNo_comment.setText("0 " + context.getString(R.string.str_comment));
        mtxtNo_share.setText("0 " + context.getResources().getString(R.string.str_share));

        // Single Image View
        mImgActPic = itemView.findViewById(R.id.image_single);

        // home silde 2
        mVp = itemView.findViewById(R.id.vpHs2);
        mTl = itemView.findViewById(R.id.tlHs2);

    }
}