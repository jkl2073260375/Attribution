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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import android.widget.Button;
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
//    MyHelper helper;
    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        requestDrawOverLays();
//        final MyHelper myHelper=new MyHelper(this);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG,Manifest.permission.RECEIVE_BOOT_COMPLETED},1);

    }

    public void insert(View view){
        Myhelper helper=new Myhelper(MainActivity.this);
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0311","河北省石家庄"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0381","河北省衡水"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0351","山西省太原市"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0419","辽宁省辽阳市"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0421","辽宁省朝阳市"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0571","浙江省杭州市"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0579","浙江省义乌市"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0791","江西省南昌市"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0794","江西省抚州市"});
        db.execSQL("insert into Attribution(areaCode,address)values(?,?)",new Object[]{"0795","江西宜春"});
        Toast.makeText(MainActivity.this, "添加完成", Toast.LENGTH_SHORT).show();
        db.close();
    }
    //获取归属地
    public String getAddress(String number){
        String address=null;
        String area=number.substring(0,4);
        Myhelper helper=new Myhelper(MainActivity.this);
        //获取可读SQLiteDatabase对象
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query("attribution",null,"areaCode=?",new String[]{area},null,null,null);
        if (cursor.getCount()!=0 ){
          while(cursor.moveToNext()){
              String id=cursor.getString(0);
              String areaCode=cursor.getString(1);
              address=cursor.getString(2);
          }
        }
        cursor.close();
        db.close();
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
