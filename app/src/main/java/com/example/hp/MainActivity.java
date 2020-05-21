package com.example.hp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;
import android.provider.Settings;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        smsManager = SmsManager.getDefault();

        if (judgePermission()) {
            // 有权限，直接发送
            sendMessage();
        }else {
            // 没有权限，获取权限
            createDialog();
        }
    }

    // 创建dialog，提醒并获取权限
    private void createDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请注意：");
        dialog.setMessage(R.string.dialog_message);
        dialog.setCancelable(false);
        dialog.setNegativeButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Intent intent =  new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                Intent intent1 =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent1);
                MainActivity.this.finish();
            }
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[] {
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.SEND_SMS
                    }, 1);
                }
            }
        });
        dialog.show();
    }

    boolean judgePermission() {
        return (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                sendMessage();
            }
            else {
                MainActivity.this.finish();
            }
        }
    }

    private void sendMessage() {
        smsManager.sendTextMessage("1065930051",
                null, "MM", null, null);
        Toast toast;
        toast=Toast.makeText(MainActivity.this,null,Toast.LENGTH_SHORT);
        toast.setText("短信发送成功");
        toast.show();
        MainActivity.this.finish();
    }
}
