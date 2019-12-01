package com.mohan.raksha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import com.mohan.raksha.DatabaseContracts.*;

/**
 * Created by mohanmmohadikar on 9/23/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contacts.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_CONTACTS_TABLE = "CREATE TABLE "+
                ContactsEntry.TABLE_NAME + " (" +
                ContactsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContactsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ContactsEntry.COLUMN_NUMBER + " TEXT NOT NULL, " +
                ContactsEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ContactsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ ContactsEntry.TABLE_NAME,null);
        return res;
    }


}

