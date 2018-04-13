package com.hm.application.newtry.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.hm.application.newtry.materialcamera.internal.BaseCaptureActivity;
import com.hm.application.newtry.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
