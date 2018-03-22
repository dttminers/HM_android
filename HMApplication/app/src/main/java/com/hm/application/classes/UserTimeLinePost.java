package com.hm.application.classes;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.hm.application.R;
import com.hm.application.adapter.GridAdapter;
import com.hm.application.adapter.SlidingImageAdapter;
import com.hm.application.common.MyPost;
import com.hm.application.model.AppConstants;
import com.hm.application.model.DemoItem;
import com.hm.application.utils.CommonFunctions;
import com.hm.application.utils.quiltview.QuiltView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserTimeLinePost {

    public static RelativeLayout mrr_header_file;
    public static ImageView mImgActPic, mcircle_img;
    static TextView mtxt_label, mtxt_time_ago;
    static LinearLayout mll_footer, mllNumber_file;
    static TextView mtxt_like, mtxt_comment, mtxt_share;
    static TextView mtxtNo_like, mtxtNo_comment, mtxtNo_share;
    static ViewPager mVp;
    static TabLayout mTl;
    static QuiltView quiltView;


    public static void toDisplayAlbumPost(JSONObject jsonObject, Context context, LinearLayout mLlPostMain) {
        try {
            /*"activity": "album",
        "id": "1",
        "timeline_id": "24",
        "post": null,
        "image": "https://static.pexels.com/photos/7640/pexels-photo.jpg, https://static.pexels.com/photos/7640/pexels-photo.jpg, https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Himalayas_from_Kullu_Valley%2C_Himachal_Pradesh.jpg/220px-Himalayas_from_Kullu_Valley%2C_Himachal_Pradesh.jpg",
        "like_count": "0",
        "comment_count": "0",
        "share_count": "0",
        "time": "20-3-2018 14:43 PM"*/
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                View itemView = inflater.inflate(R.layout.multi_image_layout, null, false);


                AsymmetricGridView listView = itemView.findViewById(R.id.listView);

                mrr_header_file = itemView.findViewById(R.id.rr_header_file);
                mll_footer = itemView.findViewById(R.id.ll_footer);
                mllNumber_file = itemView.findViewById(R.id.llNumber_file);
                mcircle_img = itemView.findViewById(R.id.circle_img);
                mtxt_label = itemView.findViewById(R.id.txt_label);
                mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
                mtxt_like = itemView.findViewById(R.id.txt_like);
                mtxt_comment = itemView.findViewById(R.id.txt_comment);
                mtxt_share = itemView.findViewById(R.id.txt_share);
                mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
                mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
                mtxtNo_share = itemView.findViewById(R.id.txtNo_share);

                // Choose your own preferred column width
                listView.setRequestedColumnWidth(CommonFunctions.dpToPx(context, 120));
//                final List<AsymmetricItem> items = new ArrayList<>();

                mtxt_time_ago.setText("albums");
                if (!jsonObject.isNull("post")) {
                    mtxt_label.setText(jsonObject.getString("post"));
                }
                if (!jsonObject.isNull("like_count")) {
                    mtxtNo_like.setText(jsonObject.getString("like_count") + " " + context.getResources().getString(R.string.str_likes));
                }
                if (!jsonObject.isNull("comment_count")) {
                    mtxtNo_comment.setText(jsonObject.getString("comment_count") + " " + context.getResources().getString(R.string.str_comment));
                }
                if (!jsonObject.isNull("share_count")) {
                    mtxtNo_share.setText(jsonObject.getString("share_count") + " " + context.getResources().getString(R.string.str_share));
                }
//                if (!jsonObject.isNull("image")) {
//                    Picasso.with(context).load(AppConstants.URL + jsonObject.getString("image").replaceAll("\\s", "%20")).into(mImgActPic);
//                }

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
                /*
                String sampleString = "101,203,405";
      String[] stringArray = sampleString.split(",");
      int[] intArray = new int[stringArray.length];
      for (int i = 0; i < stringArray.length; i++) {
         String numberAsString = stringArray[i];
         intArray[i] = Integer.parseInt(numberAsString);
      }
      System.out.println("Number of integers: " + intArray.length);
      System.out.println("The integers are:");
      for (int number : intArray) {
         System.out.println(number);
      }
                 */

            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }

    }

    public static void toDisplayNormalPost(JSONObject jsonObject, Context context, LinearLayout mLlPostMain) {
        try {
            /*{
                "activity": "post",
                    "id": "2",
                    "timeline_id": "25",
                    "post": "Iron Man",
                    "image": null,
                    "like_count": "0",
                    "comment_count": "0",
                    "share_count": "0",
                    "time": "20-03-2018 16:02 PM"
            },*/
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                View itemView = inflater.inflate(R.layout.tab22_list, null, false);

                mrr_header_file = itemView.findViewById(R.id.rr_header_file);
                mll_footer = itemView.findViewById(R.id.ll_footer);
                mllNumber_file = itemView.findViewById(R.id.llNumber_file);
                mImgActPic = itemView.findViewById(R.id.image_single);
                mcircle_img = itemView.findViewById(R.id.circle_img);
                mtxt_label = itemView.findViewById(R.id.txt_label);
                mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
                mtxt_like = itemView.findViewById(R.id.txt_like);
                mtxt_comment = itemView.findViewById(R.id.txt_comment);
                mtxt_share = itemView.findViewById(R.id.txt_share);
                mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
                mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
                mtxtNo_share = itemView.findViewById(R.id.txtNo_share);

                if (!jsonObject.isNull("post")) {
                    mtxt_label.setText(jsonObject.getString("post"));
                }
                if (!jsonObject.isNull("like_count")) {
                    mtxtNo_like.setText(jsonObject.getString("like_count") + " " + context.getResources().getString(R.string.str_likes));
                }
                if (!jsonObject.isNull("comment_count")) {
                    mtxtNo_comment.setText(jsonObject.getString("comment_count") + " " + context.getResources().getString(R.string.str_comment));
                }
                if (!jsonObject.isNull("share_count")) {
                    mtxtNo_share.setText(jsonObject.getString("share_count") + " " + context.getResources().getString(R.string.str_share));
                }
                if (!jsonObject.isNull("image")) {
                    Picasso.with(context).load(AppConstants.URL + jsonObject.getString("image").replaceAll("\\s", "%20")).into(mImgActPic);
                }


                mLlPostMain.addView(itemView);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();


        }
    }

    public static void toDisplayPhotoPost(final JSONObject jsonObject, final Context context, final LinearLayout mLlPostMain) {
        try {
         /*"activity": "album",
        "id": "1",
        "timeline_id": "24",
        "post": null,
        "image": "https://static.pexels.com/photos/7640/pexels-photo.jpg, https://static.pexels.com/photos/7640/pexels-photo.jpg, https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Himalayas_from_Kullu_Valley%2C_Himachal_Pradesh.jpg/220px-Himalayas_from_Kullu_Valley%2C_Himachal_Pradesh.jpg",
        "like_count": "0",
        "comment_count": "0",
        "share_count": "0",
        "time": "20-3-2018 14:43 PM"*/
         /*
                String sampleString = "101,203,405";
      String[] stringArray = sampleString.split(",");
      int[] intArray = new int[stringArray.length];
      for (int i = 0; i < stringArray.length; i++) {
         String numberAsString = stringArray[i];
         intArray[i] = Integer.parseInt(numberAsString);
      }
      System.out.println("Number of integers: " + intArray.length);
      System.out.println("The integers are:");
      for (int number : intArray) {
         System.out.println(number);
      }
                 */

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                final View itemView = inflater.inflate(R.layout.viewpager_post_layout, null, false);

                mrr_header_file = itemView.findViewById(R.id.rr_header_file);
                mll_footer = itemView.findViewById(R.id.ll_footer);
                mllNumber_file = itemView.findViewById(R.id.llNumber_file);
                mcircle_img = itemView.findViewById(R.id.circle_img);
                mtxt_label = itemView.findViewById(R.id.txt_label);
                mtxt_time_ago = itemView.findViewById(R.id.txt_time_ago);
                mtxt_like = itemView.findViewById(R.id.txt_like);
                mtxt_comment = itemView.findViewById(R.id.txt_comment);
                mtxt_share = itemView.findViewById(R.id.txt_share);
                mtxtNo_like = itemView.findViewById(R.id.txtNo_like);
                mtxtNo_comment = itemView.findViewById(R.id.txtNo_comment);
                mtxtNo_share = itemView.findViewById(R.id.txtNo_share);
                mVp = itemView.findViewById(R.id.vpHs2);
                mTl = itemView.findViewById(R.id.tlHs2);

                if (!jsonObject.isNull("post")) {
                    mtxt_label.setText(jsonObject.getString("post"));
                }
                if (!jsonObject.isNull("like_count")) {
                    mtxtNo_like.setText(jsonObject.getString("like_count") + " " + context.getResources().getString(R.string.str_likes));
                }
                if (!jsonObject.isNull("comment_count")) {
                    mtxtNo_comment.setText(jsonObject.getString("comment_count") + " " + context.getResources().getString(R.string.str_comment));
                }
                if (!jsonObject.isNull("share_count")) {
                    mtxtNo_share.setText(jsonObject.getString("share_count") + " " + context.getResources().getString(R.string.str_share));
                }
                if (!jsonObject.isNull("image")) {
//                    Picasso.with(context).load(AppConstants.URL + jsonObject.getString("image").replaceAll("\\s", "%20")).into(mImgActPic);
                    mVp.setAdapter(new SlidingImageAdapter(context, jsonObject.getString("image").split(",")));
                    mTl.setupWithViewPager(mVp);
                }

                mtxt_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyPost.toLikeUnlikePost(context, jsonObject.getString("timeline_id"));
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
}
