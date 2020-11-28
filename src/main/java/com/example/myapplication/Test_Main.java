package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Test_Main extends AppCompatActivity implements View.OnClickListener {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;

    //버튼 테스트를 위한 메인함수
    //나중에 이부분을 전체 버튼이 있는 레이아웃으로 수정
    //버튼이름도 수정할 것, R.id.btn... 이름 확인해서 수정할 것
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__main);

        btn1 = findViewById(R.id.btn_Drows);
        btn2 = findViewById(R.id.btn_Box);
        btn3 = findViewById(R.id.btn_S4Women);
        btn4 = findViewById(R.id.btn_Toilet);
        btn5 = findViewById(R.id.btn_WIFI);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }
        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()){               //들어온 이벤트의 id를 가져옴
                case R.id.btn_Box :             //들어온게 박스 버튼과 이벤트가 같다면
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("menu",1);
                    startActivity(intent);
                    break;

                case R.id.btn_Drows :             //들어온게 박스 버튼과 이벤트가 같다면
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("menu",2);
                    startActivity(intent);
                    break;

                case R.id.btn_S4Women :             //들어온게 박스 버튼과 이벤트가 같다면
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("menu",3);
                    startActivity(intent);
                    break;

                case R.id.btn_Toilet :             //들어온게 박스 버튼과 이벤트가 같다면
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("menu",4);
                    startActivity(intent);
                    break;

                case R.id.btn_WIFI :             //들어온게 박스 버튼과 이벤트가 같다면
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("menu",5);
                    startActivity(intent);
                    break;
            }
    }


}
