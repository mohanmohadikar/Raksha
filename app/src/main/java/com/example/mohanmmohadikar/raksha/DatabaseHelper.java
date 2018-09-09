package com.example.mohanmmohadikar.raksha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mohanmmohadikar on 9/10/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Contacts.db";
    public static final String TABLE_NAME = "Contact_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NUMBER1";
    public static final String COL_3 = "NUMBER2";
    public static final String COL_4 = "NUMBER3";
    public static final String COL_5 = "NUMBER4";
    public static final String COL_6 = "NUMBER5";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NUMBER1 TEXT,NUMBER2 TEXT,NUMBER3 TEXT,NUMBER4 TEXT,NUMBER5 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String number1,String number2,String number3,String number4,String number5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,number1);
        contentValues.put(COL_3,number2);
        contentValues.put(COL_4,number3);
        contentValues.put(COL_5,number4);
        contentValues.put(COL_6,number5);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String number1,String number2,String number3,String number4,String number5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,number1);
        contentValues.put(COL_3,number2);
        contentValues.put(COL_4,number3);
        contentValues.put(COL_5,number4);
        contentValues.put(COL_6,number5);


        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }



/*

    public void getData( String a1,String a2,String a3,String a4,String a5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        a1 = contentValues.put(COL_2,number1);
        a2 = contentValues.put(COL_3,number2);
        a3 = contentValues.put(COL_4,number3);
        a4 = contentValues.put(COL_5,number4);
        a5 = contentValues.put(COL_6,number5);


    }
*/

    }
