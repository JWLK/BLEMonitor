package com.jwlks.blemonitor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwlks.blemonitor.support.PermissionSupport;
import com.jwlks.blemonitor.util.Dlog;

import com.jwlks.blemonitor.R;

public class InitSplash extends AppCompatActivity {
    public Dlog mDlog = new Dlog(this);
    PermissionSupport perms;
    Handler hd = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_splash);

        if(mDlog != null){
            boolean isDebuggable = Dlog.isDebuggable();
            Dlog.d("Debugging Status: "+isDebuggable);
        }
        permissionCheck();
        NetworkConnection();
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }



    private class AutoIntentHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), DeviceScanActivity.class)); //로딩이 끝난 후, ChoiceFunction 이동
            InitSplash.this.finish(); // 로딩페이지 Activity stack에서 제거
        }
    }


    private void permissionCheck() {
        if(Build.VERSION.SDK_INT >= 23){
            perms = new PermissionSupport(this, this);
            if(!perms.checkPermissions()){
                perms.requestPermission();
            } else {
                hd.postDelayed(new AutoIntentHandler(), 5000); // millie seconds 후에 hd handler 실행  3000ms = 3초
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(!perms.permissionResult(requestCode, permissions, grantResults)) {
            showToast_PermissionDeny();
        } else {
            hd.postDelayed(new AutoIntentHandler(), 5000); // millie seconds 후에 hd handler 실행  3000ms = 3초
        }
    }

    private void showToast_PermissionDeny() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }


    private boolean NetworkConnection() {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                if(activeNetwork != null && activeNetwork.getType() == networkType){
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void NotConnected_showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("네트워크 연결 오류");
        builder.setMessage("사용 가능한 무선네트워크가 없습니다.\n" + "먼저 무선네트워크 연결상태를 확인해 주세요.")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish(); // exit
                        //application 프로세스를 강제 종료
                        android.os.Process.killProcess(android.os.Process.myPid() );
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


}
