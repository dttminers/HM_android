package com.hm.application.classes;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
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

public class UserTimelinePostNew {

    private static RelativeLayout mRlHeaderMain, mRlDataLayer;
    private static LinearLayout mLlFooterMain, mllNormalPost;
    private static ImageView mImgMore, mImgComment, mImgShare;
    private static CircleImageView mcircle_img;
    private static TextView mtxt_label, mtxt_time_ago, mTvTimeLineId, mtxtNo_like, mtxtNo_comment,mTvPostData;
    private static ViewPager mVp;
    private static TabLayout mTl;
    private static CheckBox mChkBoxLike, mChkBoxPostLiked;
    private static Map<String, String> idTimeLine = new HashMap<String, String>();
    private static boolean status;

    @SuppressLint("ClickableViewAccessibility")
    public static void toDisplayPost(final JSONObject jsonObject, final Context context, final LinearLayout mLlPostMain, final int i, String name, final UserTab1Fragment userTab1Fragment) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                final View itemView = inflater.inflate(R.layout.viewpager_post_main, null, false);
                itemView.setTag("" + i);
                // header file
                mRlHeaderMain = itemView.findViewById(R.id.rrHeaderMain);
                mRlDataLayer = itemView.findViewById(R.id.rrVpMain);

                mcircle_img = itemView.findViewById(R.id.circle_img);
                mImgMore = itemView.findViewById(R.id.imgMore);

                mtxt_label = itemView.findViewById(R.id.txt_label);
                mtxt_label.setTypeface(HmFonts.getRobotoMedium(context));

                mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
                mtxt_time_ago.setTypeface(HmFonts.getRobotoRegular(context));

                mTvTimeLineId = itemView.findViewById(R.id.tvTimelineId);
                mTvTimeLineId.setTypeface(HmFonts.getRobotoRegular(context));

                // ViewPager Part
                mVp = itemView.findViewById(R.id.vpMainPost);
                mTl = itemView.findViewById(R.id.tlMainPost);
                mChkBoxPostLiked = itemView.findViewById(R.id.imgPostLiked);

                // footer file
                mLlFooterMain = itemView.findViewById(R.id.llFooterMain);

                mChkBoxLike = itemView.findViewById(R.id.chkLike);

                mImgComment = itemView.findViewById(R.id.imgComment);

                mImgShare = itemView.findViewById(R.id.imgShare);

                // number file
                mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
                mtxtNo_like.setTypeface(HmFonts.getRobotoRegular(context));

                mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
                mtxtNo_comment.setTypeface(HmFonts.getRobotoRegular(context));

                mTvPostData = itemView.findViewById(R.id.txtPostData22);
                mTvPostData.setTypeface(HmFonts.getRobotoMedium(context));

                mllNormalPost = itemView.findViewById(R.id.llMainVpPost);

                /* DATA BINDING*/
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

                if (!jsonObject.isNull(context.getString(R.string.str_caption)) && jsonObject.getString(context.getString(R.string.str_caption)).trim().length()>0) {
                   mTvPostData.setVisibility(View.VISIBLE);
                   mTvPostData.setText((User.getUser(context).getUsername())+" : "+(jsonObject.getString(context.getString(R.string.str_caption))));
                }else {
                    mTvPostData.setVisibility(View.GONE);
                }

                if (!jsonObject.getString(context.getString(R.string.str_like_count)).equals("0")) {
                    if (!jsonObject.isNull(context.getString(R.string.str_friend_like))) {
                        mtxtNo_like.setText("Liked by "+(jsonObject.getString(context.getString(R.string.str_friend_like)) + " and " + jsonObject.getString(context.getString(R.string.str_like_count)) + " others"));
                    } else {
                        mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count))+" likes");
                    }
                } else {
                    mtxtNo_like.setVisibility(View.GONE);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_comment_count))) {
                    if (jsonObject.getString(context.getString(R.string.str_comment_count)).equals("1")) {
                        mtxtNo_comment.setVisibility(View.VISIBLE);
                        mtxtNo_comment.setText("View "+(jsonObject.getString(context.getString(R.string.str_comment_count)) + " " + context.getResources().getString(R.string.str_comment)));
                    } else  if (jsonObject.getInt(context.getString(R.string.str_comment_count))>= 2) {
                        mtxtNo_comment.setVisibility(View.VISIBLE);
                        mtxtNo_comment.setText("View all "+(jsonObject.getString(context.getString(R.string.str_comment_count)) + " " + context.getResources().getString(R.string.str_comments)));
                    }else {
                        mtxtNo_comment.setVisibility(View.GONE);
                    }
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
                        mChkBoxLike.setChecked(true);
                    } else {
                        mChkBoxLike.setChecked(false);
                    }
                } else {
                    mChkBoxLike.setChecked(false);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_timeline_id_))) {
                    idTimeLine.put(String.valueOf(i), jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                }

//                mRrHeaderMain.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        userTab1Fragment.toCallSinglePostData(Integer.parseInt(itemView.getTag().toString()), "Multiple");

//                    }
//                });

                mLlPostMain.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                            //here is the method for double tap
                            @Override
                            public boolean onDoubleTap(MotionEvent e) {
                                if (status){
                                    status = false;
                                    MyPost.toLikeUnlikePost(context, idTimeLine.get(itemView.getTag().toString()), mLlPostMain, itemView.getTag(), null, null);
                                }
                                Log.d("hmapp GestureDetector", "onDoubleTap");
                                return true;
                            }

                            @Override
                            public void onLongPress(MotionEvent e) {
                                super.onLongPress(e);
                                Log.d("hmapp GestureDetector", "onLongPress");
                            }

                            @Override
                            public boolean onDoubleTapEvent(MotionEvent e) {
                                Log.d("hmapp GestureDetector", "onDoubleTapEvent");
                                return true;
                            }

                            @Override
                            public boolean onDown(MotionEvent e) {
                                Log.d("hmapp GestureDetector", "onDown");
                                return true;
                            }

                        }).onTouchEvent(event);
                    }
                });

                mChkBoxLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (status) {
                            status = false;
                            MyPost.toLikeUnlikePost(context, idTimeLine.get(itemView.getTag().toString()), mLlPostMain, itemView.getTag(), mChkBoxPostLiked, mChkBoxLike);
                        }
                    }
                });

                mImgMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Common_Alert_box.toPostMoreIcon(context, idTimeLine.get(itemView.getTag().toString()), mLlPostMain, itemView.getTag());
                    }
                });

                mtxtNo_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallLikeListUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });

                mtxtNo_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallCommentUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });

                mImgComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toCallCommentUi(context, idTimeLine.get(itemView.getTag().toString()));
                    }
                });


                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
            Crashlytics.logException(e);
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
            e.printStackTrace();
            Crashlytics.logException(e);

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
            e.printStackTrace();
            Crashlytics.logException(e);

        }
    }
}

//                if (!jsonObject.isNull(context.getString(R.string.str_is_liked))) {
//                    if (jsonObject.getString(context.getString(R.string.str_is_liked)).toLowerCase().equals(context.getString(R.string.str_true))) {
//                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
//                        mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.blue));
//                    } else {
//                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
//                        mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.black));
//                    }
//                } else {
//                    mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
//                    mtxt_like.setTextColor(ContextCompat.getColor(context, R.color.black));
//                }
//
//                if (!jsonObject.isNull(context.getString(R.string.str_post_small))) {
//                    mtxtDataVp.setText(jsonObject.getString(context.getString(R.string.str_post_small)));
//                } else if (!jsonObject.isNull(context.getString(R.string.str_caption))) {
//                    mtxtDataVp.setText(jsonObject.getString(context.getString(R.string.str_caption)));
//                }
//
//                mtxt_like.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            MyPost.toLikeUnlikePost(context, idTimeLine.get(itemView.getTag().toString()), mLlPostMain, itemView.getTag(), null, null);
//                        } catch (Exception | Error e) {
//                            e.printStackTrace(); Crashlytics.logException(e);
//
//                        }
//                    }
//                });
//
//               mtxt_share.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            CommonFunctions.toShareData(context,
//                                    context.getString(R.string.app_name),
//                                    jsonObject.getString(context.getString(R.string.str_post_small)),
//                                    idTimeLine.get(itemView.getTag().toString()), null);
//                        } catch (Exception | Error e) {
//                            e.printStackTrace(); Crashlytics.logException(e);
//
//                        }
//                    }
//                });