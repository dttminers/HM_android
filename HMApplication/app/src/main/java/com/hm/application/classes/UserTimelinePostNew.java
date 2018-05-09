package com.hm.application.classes;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hm.application.R;
import com.hm.application.utils.HmFonts;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserTimelinePostNew {

    private RelativeLayout mRrHeaderMain, mRrVpMain;
    private LinearLayout mLlMainVpPost, mLlFooterMain;
    private ImageView mImgActPic, mImgMore,mImgComment,mImgShare;
    private static CircleImageView mcircle_img;
    private  TextView mtxt_label, mtxt_time_ago, mTvTimeLineId, mtxt_like, mtxt_comment, mtxt_share, mtxtNo_like, mtxtNo_comment, mtxtNo_share, mtxtData22, mtxtDataVp;
    private  ViewPager mVp;
    private TabLayout mTl;
    private CheckBox mChkBoxLike,mChkBoxPostLiked;

    private void toBindView(final Context context, View itemView) {
        // header file
        mRrHeaderMain = itemView.findViewById(R.id.rrHeaderMain);

        mcircle_img = itemView.findViewById(R.id.circle_img);
        mImgMore = itemView.findViewById(R.id.imgMore);

        mtxt_label = itemView.findViewById(R.id.txt_label);
        mtxt_label.setTypeface(HmFonts.getRobotoRegular(context));

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
    }
}
