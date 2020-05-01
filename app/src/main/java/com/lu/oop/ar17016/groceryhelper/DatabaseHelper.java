package com.lu.oop.ar17016.groceryhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "grocery_database";
    private static final String TABLE_NAME = "grocery_table";
    private static final String COL1 = "name";
    private static final String COL2 = "quantity";

    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT," + COL2 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean addData(String name, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, quantity);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    GroceryItem getById(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = " + id.toString(), null);
        data.moveToFirst();
        return new GroceryItem(
            data.getInt(0),
            data.getString(1),
            data.getString(2)
        );
    }

    boolean updateExistingRaw(Integer id, String name, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, quantity);
        long result =  db.update(TABLE_NAME, contentValues, "ID = "+id.toString(), null);

        Log.i("Edit", String.valueOf(result));

        return result > 0;
    }

    boolean deleteById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID =" + id.toString(), null) > 0;
    }

    Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
}
