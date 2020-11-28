package com.example.myapplication;

public class Data<T> {
    //공식에 필요한 값들을 미리 정리
   public T name;                       //arrayList String형
   public T latitude;                   //arrayList double형
   public T longitude;
   public T address;                    //arrayList String형
   public int size;
    //생성자
    Data(T name, T latitude, T longitude , T address, int size){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.size = size;
    }

    public T getName()      {return name;}
    public T getLatitude()  {return latitude;}
    public T getLongitude() {return longitude;}
    public T getAddress()   {return address;}
    public int getSize()    {return size;}


}
