package com.project.june.qrcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.june.qrcode.normal.NormalQrActivity;
import com.project.june.qrcode.special.activity.SpecialDiyQrActivity;
import com.project.june.qrcode.special.activity.SpecialQrActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.normal_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalQrActivity.startThis(MainActivity.this);
            }
        });

        findViewById(R.id.special_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialQrActivity.startThis(MainActivity.this);
            }
        });
        findViewById(R.id.special_diy_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialDiyQrActivity.startThis(MainActivity.this);
            }
        });
    }
}
