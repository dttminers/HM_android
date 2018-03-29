package com.hm.application.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.hm.application.R;
import com.hm.application.activity.MainHomeActivity;
import com.hm.application.activity.UserInfoActivity;
import com.hm.application.adapter.GridAdapter;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.fragments.CommentFragment;
import com.hm.application.model.AppConstants;
import com.hm.application.model.DemoItem;
import com.hm.application.model.User;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.quiltview.QuiltView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserTimeLinePost {

    private static RelativeLayout mrr_header_file;
    private static ImageView mImgActPic;
    private static CircleImageView mcircle_img;
    private static TextView mtxt_label, mtxt_time_ago, mTvTimeLineId;
    private static LinearLayout mll_footer, mllNumber_file;
    private static TextView mtxt_like, mtxt_comment, mtxt_share;
    private static TextView mtxtNo_like, mtxtNo_comment, mtxtNo_share;
    private static ViewPager mVp;
    private static TabLayout mTl;
    private static AsymmetricGridView listView;
    private static QuiltView quiltView;

    public static void toDisplayNormalPost(final JSONObject jsonObject, final Context context, final LinearLayout mLlPostMain, final int i) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                final View itemView = inflater.inflate(R.layout.tab22_list, null, false);

                itemView.setTag("" + i);
                toBindView(context, itemView);

                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20")).into(mcircle_img);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_post_small))) {
                    mtxt_label.setText(jsonObject.getString(context.getString(R.string.str_post_small)));
                } else if (!jsonObject.isNull(context.getString(R.string.str_caption))) {
                    mtxt_label.setText(jsonObject.getString(context.getString(R.string.str_caption)));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_like_count))) {
                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count)) + " " + context.getResources().getString(R.string.str_likes));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_comment_count))) {
                    mtxtNo_comment.setText(jsonObject.getString(context.getString(R.string.str_comment_count)) + " " + context.getResources().getString(R.string.str_comment));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_share_count))) {
                    mtxtNo_share.setText(jsonObject.getString(context.getString(R.string.str_share_count)) + " " + context.getResources().getString(R.string.str_share));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_image))) {
                    Picasso.with(context).load(AppConstants.URL + jsonObject.getString(context.getString(R.string.str_image)).replaceAll("\\s", "%20")).into(mImgActPic);
                } else {
                    mImgActPic.setVisibility(View.GONE);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_time))) {
                    mtxt_time_ago.setText(CommonFunctions.toSetDate(jsonObject.getString(context.getString(R.string.str_time))));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_is_liked))) {
                    if (jsonObject.getString(context.getString(R.string.str_is_liked)).toLowerCase().equals(context.getString(R.string.str_true))) {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                    } else {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                    }
                } else {
                    mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                }

                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context).load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20")).placeholder(R.color.light).error(R.color.light2).into(mcircle_img);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_timeline_id_))) {
                    mTvTimeLineId.setText(jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                }

                mtxt_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyPost.toLikeUnlikePost(context, jsonObject.getString(context.getString(R.string.str_timeline_id_)), mLlPostMain, itemView.getTag(), null, null);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }

                    }
                });

                mtxt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.TIMELINE_ID, itemView.getTag().toString());
                            CommentFragment cm = new CommentFragment();
                            cm.setArguments(bundle);
                            ((UserInfoActivity) context).replaceMainHomePage(cm);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });

                mtxt_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CommonFunctions.toShareData(context, context.getString(R.string.app_name), jsonObject.getString(context.getString(R.string.str_post_small)), jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });
                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void toDisplayPhotoPost(final JSONObject jsonObject, final Context context, final LinearLayout mLlPostMain, final int i) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                final View itemView = inflater.inflate(R.layout.viewpager_post_layout, null, false);
                itemView.setTag("" + i);
                toBindView(context, itemView);

                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20")).into(mcircle_img);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_post_small))) {
                    mtxt_label.setText(jsonObject.getString(context.getString(R.string.str_post_small)));
                } else if (!jsonObject.isNull(context.getString(R.string.str_caption))) {
                    mtxt_label.setText(jsonObject.getString(context.getString(R.string.str_caption)));
                }
                if (!jsonObject.isNull(context.getString(R.string.str_like_count))) {
                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count)) + " " + context.getResources().getString(R.string.str_likes));
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
                    mVp.setAdapter(new SlidingImageAdapter(context, jsonObject.getString(context.getString(R.string.str_image)).split(",")));
                    mTl.setupWithViewPager(mVp);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_is_liked))) {
                    if (jsonObject.getString(context.getString(R.string.str_is_liked)).toLowerCase().equals(context.getString(R.string.str_true))) {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                    } else {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                    }
                } else {
                    mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                }


                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context).load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20")).placeholder(R.color.light).error(R.color.light2).into(mcircle_img);
                }

                if (!jsonObject.isNull(context.getString(R.string.str_timeline_id_))) {
                    mTvTimeLineId.setText(jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                }

                mtxt_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyPost.toLikeUnlikePost(context, jsonObject.getString(context.getString(R.string.str_timeline_id_)), mLlPostMain, itemView.getTag(), null, null);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }

                    }
                });

                mtxt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.TIMELINE_ID, itemView.getTag().toString());
                            CommentFragment cm = new CommentFragment();
                            cm.setArguments(bundle);
                            ((UserInfoActivity) context).replaceMainHomePage(cm);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });

                mtxt_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CommonFunctions.toShareData(context, context.getString(R.string.app_name), jsonObject.getString(context.getString(R.string.str_post_small)), jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });


                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void toDisplayAlbumPost(final JSONObject jsonObject, final Context context, final LinearLayout mLlPostMain) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                final View itemView = inflater.inflate(R.layout.multi_image_layout, null, false);

                toBindView(context, itemView);

                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20"))
                            .placeholder(R.color.light)
                            .error(R.color.light2)
                            .into(mcircle_img);
                }

                mtxt_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyPost.toLikeUnlikePost(context, jsonObject.getString(context.getString(R.string.str_timeline_id_)), mLlPostMain, itemView.getTag(), null, null);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }

                    }
                });

                mtxt_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Log.d("Hampp", " comment " + itemView.getTag());
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.TIMELINE_ID, itemView.getTag().toString());
                            CommentFragment cm = new CommentFragment();
                            cm.setArguments(bundle);
                            ((UserInfoActivity) context).replaceMainHomePage(cm);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });

                mtxt_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CommonFunctions.toShareData(context, context.getString(R.string.app_name), jsonObject.getString(context.getString(R.string.str_post_small)), jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });

                // Choose your own preferred column width
                listView.setRequestedColumnWidth(CommonFunctions.dpToPx(context, 120));

                mtxt_time_ago.setText(R.string.str_albums);
                if (!jsonObject.isNull(context.getString(R.string.str_post_small))) {
                    mtxt_label.setText(jsonObject.getString(context.getString(R.string.str_post_small)));
                } else if (!jsonObject.isNull(context.getString(R.string.str_caption))) {
                    mtxt_label.setText(jsonObject.getString(context.getString(R.string.str_caption)));
                }

                if (!jsonObject.isNull(context.getString(R.string.str_timeline_id_))) {
                    mTvTimeLineId.setText(jsonObject.getString(context.getString(R.string.str_timeline_id_)));
                }

                if (!jsonObject.isNull(context.getString(R.string.str_like_count))) {
                    mtxtNo_like.setText(jsonObject.getString(context.getString(R.string.str_like_count)) + " " + context.getResources().getString(R.string.str_likes));
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
                if (!jsonObject.isNull(context.getString(R.string.str_is_liked))) {
                    if (jsonObject.getString(context.getString(R.string.str_is_liked)).toLowerCase().equals(context.getString(R.string.str_true))) {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                    } else {
                        mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_dark_pink, 0, 0, 0);
                    }
                } else {
                    mtxt_like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                }
                if (User.getUser(context).getPicPath() != null) {
                    Picasso.with(context)
                            .load(AppConstants.URL + User.getUser(context).getPicPath().replaceAll("\\s", "%20")).into(mcircle_img);
                }
                final List<DemoItem> items = new ArrayList<>();

                items.add(new DemoItem(2, 2, 0, R.drawable.tab1));
                items.add(new DemoItem(1, 1, 0, R.drawable.tab1));
                items.add(new DemoItem(1, 1, 0, R.drawable.tab1));
                items.add(new DemoItem(2, 2, 0, R.drawable.tab1));
                items.add(new DemoItem(1, 1, 0, R.drawable.tab1));
                items.add(new DemoItem(1, 1, 0, R.drawable.tab1));
                items.add(new DemoItem(1, 1, 0, R.drawable.tab1));
                items.add(new DemoItem(2, 2, 0, R.drawable.tab1));
                items.add(new DemoItem(1, 1, 0, R.drawable.tab1));
                items.add(new DemoItem(1, 1, 0, R.drawable.tab1));

                GridAdapter adapter = new GridAdapter(context, items);
                listView.setAdapter(new AsymmetricGridViewAdapter(context, listView, adapter));

                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void toDisplayMultiImagePost(JSONObject jsonObject, Context context, LinearLayout mLlPostMain) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                View itemView = inflater.inflate(R.layout.multi_photos_layout, null, false);
                quiltView = itemView.findViewById(R.id.quilt);
                quiltView.setChildPadding(5);
                ArrayList<ImageView> images = new ArrayList<ImageView>();
                for (int i = 0; i < 200; i++) {
                    ImageView image = new ImageView(context);
                    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    if (i % 2 == 0)
                        image.setImageResource(R.drawable.aboutuslogohm);
                    else
                        image.setImageResource(R.drawable.login1);
                    images.add(image);
                }
                quiltView.addPatchImages(images);
                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    private static void toBindView(Context context, View itemView) {
        // header file
        mrr_header_file = itemView.findViewById(R.id.rr_header_file);
        mcircle_img = itemView.findViewById(R.id.circle_img);
        mtxt_label = itemView.findViewById(R.id.txt_label);
        mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
        mTvTimeLineId = itemView.findViewById(R.id.tvTimelineId);

        // footer file
        mll_footer = itemView.findViewById(R.id.ll_footer);
        mtxt_like = itemView.findViewById(R.id.txt_like);
        mtxt_comment = itemView.findViewById(R.id.txt_comment);
        mtxt_share = itemView.findViewById(R.id.txt_share);

        // number file
        mllNumber_file = itemView.findViewById(R.id.llNumber_file);
        mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
        mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
        mtxtNo_share = itemView.findViewById(R.id.txtNo_share);

        mtxtNo_like.setText("0 " + context.getResources().getString(R.string.str_like));
        mtxtNo_comment.setText("0 " + context.getString(R.string.str_comment));
        mtxtNo_share.setText("0 " + context.getResources().getString(R.string.str_share));

        // Multi Image Layout
        // AsymmetricGridView
        listView = itemView.findViewById(R.id.listView);

        // Single Image View
        mImgActPic = itemView.findViewById(R.id.image_single);

        // home silde 2
        mVp = itemView.findViewById(R.id.vpHs2);
        mTl = itemView.findViewById(R.id.tlHs2);
    }
}
