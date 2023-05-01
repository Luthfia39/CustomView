package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
// Ada 2 cara untuk membuat custom view :
// 1. Dengan extend component (extend suatu komponen kemudian di-modify/tidak buat dari awal)
//    Misalnya suatu edittex dimodifikasi dengan ditambah suatu clear button
// 2. Membuat suatu component button baru dengan view
//    Button lebih variatif dari button biasa

// method on touch memiliki kekurangan, yaitu perlu mendeklarasikan posisi komponen yang akan ditekan