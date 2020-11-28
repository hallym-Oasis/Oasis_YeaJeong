package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//DB생성 및 업데이트
class TestDBHelper extends SQLiteOpenHelper {
    Context mContext;
    public final static String PACKAGE_NAME = "com.example.myapplication";
    public static String DB_PATH= "/data/data/" + PACKAGE_NAME + "/databases/";

    public TestDBHelper(Context context, String name){
        super(context,DB_PATH +name,null, 1);//버젼이 높아지면 onUpgrade를 수행


    }
    @Override
    public void onConfigure(SQLiteDatabase db){
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //기존에 있는 DB를 사용하므로 create 문 생략
//        db.execSQL("CREATE TABLE tb_test2(" +
//                "NAME          TEXT, " +
//                "나이      INTEGER," +
//                "LATITUDE       REAL," +
//                "LONGITUDE      REAL" +
//                ");");
    } // end onCreate()


    //현재 db가 이미 존재하면 지우고 다시 생성
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //기존에 있는 db를 사용하므로 생략
//        db.execSQL("DROP TABLE IF EXISTS tb_test2");
//        onCreate(db);
    }
}