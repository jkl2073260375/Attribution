package com.example.attribution;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MyReceiver extends BroadcastReceiver {
    private TelephonyManager tm;

    @Override//
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
              String number=getResultData();
              Log.e("info","********************"+number);
              if(number.equals(119)){
                  setResultData("181");
              }
           Log.e("info",getResultData());
      }else{
            //监控呼入来电
            tm=(TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(new MyPhoneListener(context),PhoneStateListener.LISTEN_CALL_STATE);

        }
    }

    public MyReceiver() {
        super();
    }
}
class MyPhoneListener extends PhoneStateListener {
    private String address;
    private MainActivity mainActivity;
    private WindowManager wm;
    private Context context;
    private TextView tv;
    private static Boolean flag=false;
    //获取广播上下文对象
    public MyPhoneListener(Context context){
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.i("info", "incomingNumber:" + incomingNumber);
        mainActivity=new MainActivity();
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:  //空闲
                Log.e("info", "CALL_STATE_IDLE");
                if(wm != null){
                    wm.removeView(tv);
                    flag=false;
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: //通话
                Log.e("info", "CALL_STATE_OFFHOOK");
                address=mainActivity.getAddress(incomingNumber);
                Log.e("info","归属地为:"+address);
                listView(incomingNumber,address);
                break;
            case TelephonyManager.CALL_STATE_RINGING: //响铃
                if(!flag){
                   Log.e("info", "CALL_STATE_RINGING");
                   address=mainActivity.getAddress(incomingNumber);
                   Log.e("info","归属地为:"+address);
                   listView(incomingNumber,address);
                   break;
                }
        }
    }
    //创建悬浮窗
    public void listView(String incomingNumber,String address){
        //获取WindowManager对象
        wm = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //AIP25 以上得使用 TYPE_APPLICATION_OVERLAY 不然会报错
        if (Build.VERSION.SDK_INT > 25) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;//APP内有效
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//系统窗口
        }
        // 设置悬浮框不可触摸
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置宽高
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置初始位置
        params.gravity = Gravity.LEFT |Gravity.TOP;
        params.x = 000;
        params.y = 500;
        //设置背景色
        params.format = PixelFormat.RGBA_8888;
        tv = new TextView(context);
        tv.setText("来电号码为:\t" + incomingNumber+"\n\n来自于:\t"+address);
        tv.setTextSize(20);
        tv.setOnTouchListener(new View.OnTouchListener() {
            //保存悬浮框最后位置的变量
            int lastX, lastY;
            int paramX, paramY;
            @Override
            //监听悬浮窗移动
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        // 更新悬浮窗位置
                        wm.updateViewLayout(tv, params);
                        break;
                }
                return true;
            }
        });
        wm.addView(tv, params);
        flag=true;
    }
}