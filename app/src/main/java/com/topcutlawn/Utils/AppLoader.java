package com.topcutlawn.Utils;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.topcutlawn.R;


public class AppLoader {
    Activity activity;
    private Dialog dialog;

    public AppLoader(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loader);
        //  dialog.setTitle("this is title");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void showDialog() {
        dialog.show();
    }


    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

}



