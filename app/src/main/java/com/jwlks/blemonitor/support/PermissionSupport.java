package com.jwlks.blemonitor.support;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionSupport {

    private Context context;
    private Activity activity;

    private String[] permissions = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int MULTIPLE_PERMISSIONS = 101;

    private List<String>  permissionList;

    public PermissionSupport(){};
    public PermissionSupport(Activity mActivity, Context mContext) {
        this.activity = mActivity;
        this.context = mContext;
    }

    public boolean checkPermissions() {
        int result;
        permissionList = new ArrayList<>();

        for (String perm : permissions) {
            result = ContextCompat.checkSelfPermission(context, perm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(perm);
            }
        }
        if (!permissionList.isEmpty()) { //isEmpty == false => There are no item.
             return false;
        }
        return true;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);

    }

    public boolean permissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MULTIPLE_PERMISSIONS && (grantResults.length > 0)){
            for(int i = 0; i < grantResults.length; i++ ){
                if(grantResults[i] == -1) {
                    return false;
                }
            }
        }

        return true;
    }




}
