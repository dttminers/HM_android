package com.hm.application.utils.insta.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.hm.application.utils.insta.materialcamera.internal.BaseCaptureActivity;
import com.hm.application.utils.insta.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
