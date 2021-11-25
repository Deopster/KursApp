package com.example.kursapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class DatabaseAdapter {

    DatabaseHelper helper;
    SQLiteDatabase db;
    Context context;

    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        this.context = context;
    }

    public SimpleCursorAdapter populateListViewFromDB(String table) {
        String columns[] = {DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_WORK, DatabaseHelper.KEY_DATE, DatabaseHelper.KEY_SAlARY};
        Cursor cursor = db.query(table, columns, null, null, null, null, null, null);
        String[] fromFieldNames = new String[]{
                DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_WORK, DatabaseHelper.KEY_DATE, DatabaseHelper.KEY_SAlARY
        };
        int[] toViewIDs = new int[]{R.id.item_id, R.id.item_name, R.id.item_work, R.id.item_date,R.id.item_salary};
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }

    public int updateEmailNew(long id, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_NAME, email);
        String whereArgs[] = {"" + id};
        int count = db.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.KEY_ROWID + "=?", whereArgs);
        return count;
    }

    public int deleteDataNew(long id) {
        String whereArgs[] = {"" + id};
        int count = db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.KEY_ROWID + "=?", whereArgs);
        return count;
    }
    public void Add(String name, String work, String date, String salary,String table) {
        try {
            System.out.println("dddd");
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("work", work);
            values.put("date", date);
            values.put("Salary", salary);
            long result = db.insert(table, null , values);
            if(result == -1){
                Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Успешно добавлен", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
        }
    }
    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "mydb.db";
        private static final String TABLE_NAME = "Data";
        private static final String TABLE_NAME1 = "Data1";
        private static final String TABLE_NAME2 = "Data2";
        // When you do change the structure of the database change the version number from 1 to 2
        private static final int DATABASE_VERSION = 1;
        private static final String KEY_ROWID = "_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_WORK = "work";
        private static final String KEY_DATE = "date";
        private static final String KEY_SAlARY = "Salary";
        private static final String CREATE_TABLE = "create table " + TABLE_NAME +
                " (" + KEY_ROWID + " integer primary key autoincrement, " + KEY_NAME + " text, " + KEY_WORK + " text, " + KEY_DATE + " text, " + KEY_SAlARY + " float)";
        private static final String CREATE_TABLE1 = "create table " + TABLE_NAME1 +
                " (" + KEY_ROWID + " integer primary key autoincrement, " + KEY_NAME + " text, " + KEY_WORK + " text, " + KEY_DATE + " text, " + KEY_SAlARY + " float)";
        private static final String CREATE_TABLE2 = "create table " + TABLE_NAME2 +
                " (" + KEY_ROWID + " integer primary key autoincrement, " + KEY_NAME + " text, " + KEY_WORK + " text, " + KEY_DATE + " text, " + KEY_SAlARY + " float)";
        private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;
        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                //this.getWritableDatabase();
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE1);
                db.execSQL(CREATE_TABLE2);
                Toast.makeText(context, "onCreate called", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "onUpgrade called", Toast.LENGTH_SHORT).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }

    }
}