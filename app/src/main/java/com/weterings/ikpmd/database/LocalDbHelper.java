package com.weterings.ikpmd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class LocalDbHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase localSqlDb;
    private static LocalDbHelper ldbHelper;
    private static final String localDbName = "ikpmdShekels";
    private static final int localDbVersion = 2;

    private LocalDbHelper(Context context){
        super(context, localDbName, null, localDbVersion);
    }

    public static synchronized LocalDbHelper getDbHelper(Context context){
        if(ldbHelper == null){
            ldbHelper = new LocalDbHelper(context);
            localSqlDb = ldbHelper.getWritableDatabase();
        }
        return ldbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        db.execSQL("CREATE TABLE " + LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE + " (" +
                        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LocalDbValues.ScoreMultiplierColumn.MULTIPLIER1 + " TEXT," +
                LocalDbValues.ScoreMultiplierColumn.MULTIPLIER2 + " TEXT," +
                LocalDbValues.ScoreMultiplierColumn.MULTIPLIER3 + " TEXT," +
                LocalDbValues.ScoreMultiplierColumn.MULTIPLIER4 + " TEXT);"
                );

        db.execSQL("CREATE TABLE " + LocalDbValues.ScoreTables.SCORETABLE + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LocalDbValues.ScoreColumn.SCORE + " TEXT," +
                LocalDbValues.ScoreColumn.SHEKELS + " TEXT);"
        );
    }catch(Exception e){
          e.printStackTrace();
        }}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE);
        onCreate(db);
    }
    public Cursor query(String table, String[] columns) {
        return localSqlDb.query(table, columns, null, null, null, null, null);
    }
    public void insertIntoTable(String table, String nullColumn, ContentValues contentValues) {
        localSqlDb.insert(table, nullColumn, contentValues);
    }

    public void updateTable(String table, ContentValues contentValues, String selection, String[] selectArgs) {
        localSqlDb.update(table, contentValues, selection, selectArgs);
    }
}
