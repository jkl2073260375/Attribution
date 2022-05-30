package com.example.attribution;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class Myhelper extends SQLiteOpenHelper {
    public Myhelper(@Nullable Context context) {
        super(context,"my.db" ,null, 1);
    }
//    创建数据库表同时加入数据
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE attribution(id Integer PRIMARY KEY AUTOINCREMENT,areaCode VARCHAR(10),address VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
