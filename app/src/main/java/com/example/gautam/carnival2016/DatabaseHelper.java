package com.example.gautam.carnival2016;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gautam on 03-06-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Carnival.db";
    public static final String TABLE_NAME = "carnival_table";
    public static final String COL1 = "FLAT";
    public static final String COL2 = "NAME";
    public static final String COL3 = "CONT";
    public static final String COL4 = "PH";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + " (FLAT INTEGER PRIMARY KEY,NAME TEXT,CONT INTEGER,PH INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String flat, String name, String cont, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COL1,flat);
        contentValues.put(COL2,name);
        contentValues.put(COL3,cont);
        contentValues.put(COL4,phone);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
    public boolean updateData(String flat,String name, String contribution, String mobile){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COL1,flat);
        contentValues.put(COL2,name);
        contentValues.put(COL3,contribution);
        contentValues.put(COL4,mobile);
        db.update(TABLE_NAME,contentValues,"FLAT = ?",new String[] { flat });
        return true;
    }

    public Integer deleteData(String flat){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"FLAT = ?",new String[] {flat});
    }

}
