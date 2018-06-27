package com.example.admin.motionsensor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenActivity2(View view){
        Intent intent = new Intent(this, AccelerometerActivity.class);
        startActivity(intent);
    }
    public void OpenActivity3(View view){
        Intent intent = new Intent(this, AccelerometerActivity2.class);
        startActivity(intent);
    }
    public void OpenActivity4(View view){
        Intent intent = new Intent(this, AccelerometerActivity3.class);
        startActivity(intent);
    }
}
