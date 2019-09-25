package com.leeef.baseproject.base.util;

import android.content.Context;

import com.leeef.baseproject.base.widget.LoadingDialog;


/**
 * @ClassName: LoadingUtil
 * @Description:
 * @Author: leeeeef
 * @CreateDate: 2019/7/26 10:09
 */
public class LoadingUtil {

  private static LoadingDialog loadingDialog;

  public static void show(Context context) {
    loadingDialog = new LoadingDialog(context, "玩命加载中...");
    loadingDialog.show();
  }

  public static void hide() {
    if (loadingDialog != null && loadingDialog.isShowing())
      loadingDialog.dismiss();
  }
}
