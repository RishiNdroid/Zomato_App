package com.example.rndroid.zomato_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rndroid on 27/1/17.
 */

public class MyDatabase {

    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;
    private final String ID = " _id integer primary key ";
    private final String TABLE_NAME = " restaurant ";
    private final String NAME_COLOUMN = "name text ";
    private final String LOCALITY_COLOUMN = "locality text ";
    private final String ADDRESS_COLOUMN = "address text ";
    private final String THUMB_COLOUMN = "thumb text ";
    private final String LATITUDE_COLOUMN = "latitude text ";
    private final String LONGITUE_COLOUMN = "longitude text ";

    public MyDatabase(Context context){
        myHelper = new MyHelper(context, "zomato.db", null, 1);
    }

    // open database
    public void openDB(){
        sqLiteDatabase = myHelper.getWritableDatabase();
    }

    //insert values
    public void insertContact(String name, String locality, String address, String thumb, String latitude, String longitude){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("locality", locality);
        values.put("address", address);
        values.put("thumb", thumb);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public Cursor getRestaurants(){
        Cursor cursor = null;
        cursor = sqLiteDatabase.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    public class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table "+TABLE_NAME+" ( "+ID+", "+NAME_COLOUMN+", "+LOCALITY_COLOUMN+", "+ADDRESS_COLOUMN+", "+
            THUMB_COLOUMN+", "+LATITUDE_COLOUMN+", "+LONGITUE_COLOUMN+" );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
