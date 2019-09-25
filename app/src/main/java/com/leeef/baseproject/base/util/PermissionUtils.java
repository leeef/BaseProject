package com.leeef.baseproject.base.util;

import android.app.Activity;

import com.leeef.baseproject.R;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @ClassName: PermissionUtils
 * @Description:
 * @Author: leeeeef
 * @CreateDate: 2019/7/30 10:51
 */
public class PermissionUtils {
  public static void requestPermissions(
    @NonNull Activity host, @NonNull String rationale,
    int requestCode, @Size(min = 1) @NonNull String... perms) {

    EasyPermissions.requestPermissions(
      new PermissionRequest.Builder(host, requestCode, perms)
        .setRationale(rationale)
        .setTheme(R.style.EasyPermissionsTheme)
        .build());
  }
}
