package com.example.hp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;
import android.provider.Settings;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmsManager sms = SmsManager.getDefault();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("请注意：");
            dialog.setMessage("本程序支持从1065930051处获取闪讯密码。\n\n" +
                    "使用前，请先将本程序的以下权限修改为“允许”：\n" +
                    "○发送短信\n○获取手机信息\n\n" +
                    "设置完成后再次启动时，自动退出为正常，可前往短信查看是否发送成功。");
            dialog.setCancelable(false);
            dialog.setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent =  new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            });
            dialog.show();
        }else {
            sms.sendTextMessage("1065930051",
                    null, "MM", null, null);
            Toast toast;
            toast=Toast.makeText(MainActivity.this,null,Toast.LENGTH_SHORT);
            toast.setText("短信发送成功");
            toast.show();
            MainActivity.this.finish();
        }
    }
}
