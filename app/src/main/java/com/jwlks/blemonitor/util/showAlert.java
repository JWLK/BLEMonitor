package com.jwlks.blemonitor.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class showAlert {

    public static void set(Context mContext, String mTitle, String mContents){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mTitle);
        builder.setMessage(mContents)
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
