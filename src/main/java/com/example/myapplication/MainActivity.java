package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //    private final Context mContext; //todo: 주시 개인생성코드
    private static String TAG = "DataBaseHelper"; //Logcat에 출력할 태그이름
    public final static String PACKAGE_NAME = "com.example.myapplication";
    public final static String DB_NAME_TOILET = "oasis_db_Toilet.db";
    public final static String DB_NAME_BOX = "oasis_db_Security_Box.db";
    public final static String DB_NAME_S4WOMEN = "oasis_db_Shelter4women.db";
    public final static String DB_NAME_S4DROWSINESS = "oasis_db_Drowsiness_shelter.db";
    public final static String DB_NAME_WIFI = "oasis_db_WIFI.db";
    public final static String DB_PATH = "db/";

    TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("test1", "MainActivity");
        Context mContext = this;
        Intent intent = getIntent();
        int menu = intent.getIntExtra("menu",0);

        contentView = findViewById(R.id.txt);

        try {
            boolean bResult = isCheckDB(mContext, DB_NAME_BOX);     //db가 있는지 검사
            bResult= bResult && isCheckDB(mContext, DB_NAME_S4DROWSINESS);
            bResult= bResult && isCheckDB(mContext, DB_NAME_S4WOMEN);
            bResult= bResult && isCheckDB(mContext, DB_NAME_TOILET);
            bResult= bResult && isCheckDB(mContext, DB_NAME_WIFI);
            Log.d("DB유무:", "DB check=" + bResult);
            if (!bResult) {                                         //db가 하나라도 없을 시 다시 복사
                copyDB(mContext, DB_NAME_BOX);
                copyDB(mContext, DB_NAME_S4DROWSINESS);
                copyDB(mContext, DB_NAME_S4WOMEN);
                copyDB(mContext, DB_NAME_TOILET);
                copyDB(mContext, DB_NAME_WIFI);
            } else {

            }
        } catch (Exception e) {
        }//end catch

        Data myData = roadData(menu);
        printData(myData);
    }//end oncreate


    //쿼리문을 이용해 데이터 출력
    public Data roadData(int menu){
        SQLiteDatabase db =null;
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Double> latitude = new ArrayList<>();
        ArrayList<Double> longitude = new ArrayList<>();
        ArrayList<String> address=new ArrayList<>();
        Cursor cursor= null;
        String Q ="";

        Log.v("roadData","아래에서 선택지 고르는중");

        switch(menu){
            case 1:
                db = (new TestDBHelper(this, DB_NAME_BOX).getWritableDatabase());
                Q ="select 시설명, 위도, 경도, 소재지도로명주소, 소재지지번주소 from tb_Security_Box";
                break;
            case 2:
                db = (new TestDBHelper(this, DB_NAME_S4DROWSINESS).getWritableDatabase());
                Q ="select 졸음쉼터명, 위도, 경도, 소재지도로명주소, 소재지지번주소 from tb_DrowsinessShelter";
                break;
            case 3:
                db = (new TestDBHelper(this, DB_NAME_S4WOMEN).getWritableDatabase());
                Q ="select 점포명, 위도, 경도, 소재지도로명주소, 소재지지번주소 from tb_Shelter4women";
                break;
            case 4:
                db = (new TestDBHelper(this, DB_NAME_TOILET).getWritableDatabase());
                Q ="select 화장실명 ,위도, 경도, 소재지도로명주소, 소재지지번주소 from tb_Toilet";
                break;
            case 5:
                db = (new TestDBHelper(this, DB_NAME_WIFI).getWritableDatabase());
                Q ="select 설치장소명 ,위도 ,경도, 소재지도로명주소, 소재지지번주소 from tb_WiFi";
                break;
        }

        if(db!=null)
            cursor = db.rawQuery(Q,null);
        else
            Log.v("roadData","cursor로 읽어오는 중");

        if(cursor.moveToFirst()) {                      //커서객체 데이터의 첫번째 행으로 즉 db의 첫번째 행

            int i = 0;
            do {
                str.add(cursor.getString(0));
                latitude.add(cursor.getDouble(1));
                longitude.add(cursor.getDouble(2));
                address.add(cursor.getString(3));           //소재지 도로명주소

                if(address.get(i) == null)                              //도로명 주소 없을 시
                    address.add(i,cursor.getString(4));     //소재지 지번주소

                i++;
            } while (cursor.moveToNext());                              //다음행으로 이동
        }else{                                                          //첫번째 행이 없을 경우
            str = null;
        }

        db.close();
        cursor.close();

        Data<ArrayList> dbData = new  Data<ArrayList>(str, latitude, longitude, address, cursor.getCount());
        //쿼리문의 결과를 받는다.
        return dbData;

    }

    public void printData(Data<ArrayList> dbData){
        for (int j = 0; j < dbData.getSize() ; j++) {
            contentView.append(dbData.name.get(j) + "\n" + dbData.latitude.get(j) + "\n" + dbData.longitude.get(j)
                    + "\n" + dbData.address.get(j) + "\n\n");  //출력
        }
    }


    public boolean isCheckDB(Context mContext, String DB_NAME) {//TODO:: 파일 패스 확인할 것
        String filePath = "/data/data/" + PACKAGE_NAME + "/databases/" + DB_NAME;
        File file = new File(filePath);
        if (file.exists()) { //파일이 존재 할 시
            Log.v("mainActivity", "파일존재함");
            return true;
        }

        return false; //파일이 존재하지 않을 시
    }



    //DB복사하기 _Assets 폴더의 db/xxx.db파일을 내부 db공간으로 복사하기
    public void copyDB(Context mContext, String db_name) throws IOException {
        Log.d("test_app2", "copyDB수행중");

        String folderPath = "/data/data/" + PACKAGE_NAME + "/databases";
        String filePath = "/data/data/" + PACKAGE_NAME + "/databases/" + db_name;

        File folder = new File(folderPath);//새로운 폴더 생성
        File file = new File(filePath);//새로운 폴더 생성

        InputStream is = mContext.getAssets().open("db/" + db_name);

        String a = String.valueOf(folder.exists());
        Log.d("copyDB폴더유무 ", a);


        if (folder.exists()) {
        } else {
            folder.mkdirs();
        }

        if (file.exists()) {    //파일이 존재하면 지우고 새로만듬
            file.delete();
            file.createNewFile();
        }

        String outFileName = DB_PATH + db_name;
        Log.d("test_app2", outFileName);
        OutputStream mOs = new FileOutputStream(file);

        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = is.read(mBuffer)) > 0) {
            mOs.write(mBuffer, 0, mLength);
        }

        mOs.flush();
        mOs.close();
        is.close();

    }

}//end class


