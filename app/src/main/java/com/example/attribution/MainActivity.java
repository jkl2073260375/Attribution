package com.example.attribution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Attribution> list;
    Attribution a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
    private  MyReceiver myReceiver;
    public MainActivity(){
         list=new ArrayList<Attribution>();
         a1=new Attribution(1,"张三","0311-181","0311","河北省石家庄");
         a2=new Attribution(2,"李四","0381-181","0381","河北省衡水");
         a3=new Attribution(3,"王五","0351-181","0351","山西省太原市");
         a4=new Attribution(4,"赵六","0419-181","0419","辽宁省辽阳市");
         a5=new Attribution(5,"田七","0421-181","0421","辽宁省朝阳市");
         a6=new Attribution(6,"麻二","0571-181","0571","浙江省杭州市");
         a7=new Attribution(7,"顺一","0579-181","0579","浙江省义乌市");
         a8=new Attribution(8,"就零","0791 -181","0791 ","江西省南昌市");
         a9=new Attribution(9,"赫九","0794-181","0794","江西省抚州市");
         a10=new Attribution(10,"何二","0795-181","0795","江西宜春");
         list.add(a1);
         list.add(a2);
         list.add(a3);
         list.add(a4);
         list.add(a5);
         list.add(a6);
         list.add(a7);
         list.add(a8);
         list.add(a9);
         list.add(a10);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestDrawOverLays();
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG,Manifest.permission.RECEIVE_BOOT_COMPLETED},1);
    }
    //获取归属地
    public String getAddress(String number){
        String address=null;
        for (int i = 0; i <list.size() ; i++) {
            if(number.substring(0,4).equals(list.get(i).getAreaCode())){
                Log.e("info",number.substring(0,4));
                address=list.get(i).getAddress();
            }
        }
        return address;
    }
    //判断是否开启悬浮窗口权限,没有则跳转到权限页面去开启
    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if (!Settings.canDrawOverlays(MainActivity.this)) {
            Toast.makeText(this, "您还没有打开悬浮窗权限", Toast.LENGTH_SHORT).show();
            //跳转到相应软件的设置页面
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + MainActivity.this.getPackageName()));
            startActivityForResult(intent, 100);
        } else {
            // 授权成功之后执行的方法

        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            for(int i=0;i<permissions.length;i++){
                if(grantResults[i]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this, permissions[i]+"申请成功",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this,i+"申请失败",Toast.LENGTH_LONG).show();
                }

            }
        }
    }
        @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
