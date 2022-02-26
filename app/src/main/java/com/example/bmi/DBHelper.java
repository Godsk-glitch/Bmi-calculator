package com.example.bmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "mad.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("Create Table User(userName TEXT,height TEXT,weight TEXT,status TEXT,value TEXT,storedDate TEXT,phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists User");
    }
    public Cursor getData(String phone){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor =DB.rawQuery("select * from User where phone=?",new String[]{phone});
        return cursor;
    }
    public Boolean addData(String name,String height,String weight,String status,String value,String storedDate, String phone){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",name);
        contentValues.put("height",height);
        contentValues.put("weight",weight);
        contentValues.put("status",status);
        contentValues.put("value",value);
        contentValues.put("storedDate",storedDate);
        contentValues.put("phone",phone);
        long result=DB.insert("User",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
}
