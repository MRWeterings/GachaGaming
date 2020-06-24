package com.weterings.ikpmd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDbHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase lSQLdb;
    private static LocalDbHelper ldbHelper;
    private static final String localDbName = "cookieclicker.db";
    private static final int localDbVersion = 1;

    private LocalDbHelper(Context context){
        super(context, localDbName, null, localDbVersion);
    }

    public static synchronized LocalDbHelper getDbHelper(Context context){
        if(ldbHelper == null){
            ldbHelper = new LocalDbHelper(context);
            lSQLdb = ldbHelper.getWritableDatabase();
        }
        return ldbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
