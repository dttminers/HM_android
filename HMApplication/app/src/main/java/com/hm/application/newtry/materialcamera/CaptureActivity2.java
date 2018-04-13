package com.hm.application.newtry.materialcamera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.hm.application.newtry.materialcamera.internal.BaseCaptureActivity;
import com.hm.application.newtry.materialcamera.internal.Camera2Fragment;


public class CaptureActivity2 extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return Camera2Fragment.newInstance();
  }
}
