package com.example.david.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Stats.db";
    private static final String TABLE_NAME = "Stats_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "HEIGHT";
    public static final String COL_3 = "WEIGHT";
    public static final String COL_4 = "ACTIVITY_LEVEL";
    public static final String COL_5 = "WORKOUT_NAME";
    public static final String COL_6 = "INTENSITY";
    public static final String COL_7 = "LENGTH";
    public static final String COL_8 = "CALORIES_BURNED";
    private HashMap hp;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, HEIGHT INTEGER, WEIGHT INTEGER," +
                " ACTIVITY_LEVEL INTEGER,WORKOUT_NAME text, INTENSITY text, LENGTH INTEGER, CALORIES_BURNED INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Stats_table");
        onCreate(db);
    }

    protected boolean insertUser (int height, int weight,int activity_level, String workout_name,String intensity, int length, int calories_burned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HEIGHT", height);
        contentValues.put("WEIGHT", weight);
        contentValues.put("ACTIVITY_LEVEL", activity_level);
        contentValues.put("WORKOUT_NAME", workout_name);
        contentValues.put("INTENSITY", intensity);
        contentValues.put("LENGTH", length);
        contentValues.put("CALORIES_BURNED", calories_burned);
        db.insert("S", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Stats_table where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, int height, int weight,int activity_level, String workout_name, String intensity, int length, int calories_burned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HEIGHT", height);
        contentValues.put("WEIGHT", weight);
        contentValues.put("ACTIVITY_LEVEL", activity_level);
        contentValues.put("WORKOUT_NAME", workout_name);
        contentValues.put("INTENSITY", intensity);
        contentValues.put("LENGTH", length);
        contentValues.put("CALORIES_BURNED", calories_burned);
        db.update("Stats_table", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteUser (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Stats_table",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Stats_table", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COL_1)));
            res.moveToNext();
        }
        return array_list;
    }
}