package com.example.achal.doctors;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

/**
 * Created by achal on 10-Jul-18.
 */

public class CustomDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "db";
    private static final String TABLE_NAME = "tab";
    private static final String col_id = "id";        //0
    private static final String col_name = "name";  //1
    private static final String col_qa = "qualification";        //2
    private static final String col_exp = "expertise";    //3
    private static final String col_chamber = "chamber";    //4
    private static final String col_loc = "location";    //5
    private static final String col_con = "contact";    //6


    CustomDBHelper(Context c) {
        super(c, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String s = "CREATE TABLE " + TABLE_NAME + "(" + col_id+ " INT PRIMARY KEY," + col_name + " TEXT," + col_qa+ " TEXT,"+ col_exp+ " TEXT,"+
                col_chamber+ " TEXT,"+ col_loc+ " TEXT," + col_con + " TEXT)";
        mydb.execSQL(s);
        Log.e("dbstat", "database created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    /*------------------------------------------
                    add
            --------------------------------------------*/

    Random rand = new Random();
    public boolean addData(String name,String qa, String exp, String chamber, String loc, String con) {
        Log.d("say",name+qa+exp+chamber+loc+con);
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int id = rand.nextInt(5000);
        cv.put(col_id, id);
        cv.put(col_name, name);
        cv.put(col_exp, exp);
        cv.put(col_chamber, chamber);
        cv.put(col_qa, qa);
        cv.put(col_loc, loc);
        cv.put(col_con, con);

        long flag=  sqdb.insert(TABLE_NAME, null, cv);
        if(flag==-1) return false;
        else return true;
    }


    /*------------------------------------------
                    getAll
    --------------------------------------------*/

    Cursor getAllByCursor() {
        SQLiteDatabase sq = this.getReadableDatabase();

        String q = "SELECT * FROM " + TABLE_NAME;

        Cursor c = sq.rawQuery(q, null);

        return c;

    }

    /*------------------------------------------
          getByLocationAndExpertise Filter
    --------------------------------------------*/
    Cursor getBySpinner(String loc, String exp) {
        SQLiteDatabase sq = this.getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_NAME + " WHERE " + col_loc + " = '" + loc + "' AND "+col_exp +" = '"+exp+"'";
        Cursor c = sq.rawQuery(q, null);
        return c;
    }

    Cursor getByLoc(String loc) {
        SQLiteDatabase sq = this.getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_NAME + " WHERE " + col_loc + " = '" + loc+"'";
        Cursor c = sq.rawQuery(q, null);
        return c;
    }
    Cursor getByExp(String exp) {
        SQLiteDatabase sq = this.getReadableDatabase();
        String q = "SELECT * FROM " + TABLE_NAME + " WHERE " +col_exp +" = '"+exp+"'";
        Cursor c = sq.rawQuery(q, null);
        return c;
    }
/*

    void removeByReg(int reg) {
        SQLiteDatabase sq = this.getWritableDatabase();
        String q = "delete from "+TABLE_NAME+" where "+col_reg+" ="+reg;
        sq.execSQL(q);

    }
    void update(String reg, String title, String des){
        SQLiteDatabase sq = this.getWritableDatabase();
        Log.d("say","ok");
        ContentValues cv  = new ContentValues();
        cv.put(col_title,title);
        cv.put(col_des,des);
        sq.update( TABLE_NAME,cv,col_reg+"="+reg,null );
        Log.d("say","updated");

    }
*/
}
