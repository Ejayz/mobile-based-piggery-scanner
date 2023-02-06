package com.aspectdev.mobilebasedqrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dashboard extends AppCompatActivity {
Button btn_scan_qrcode,btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btn_logout=findViewById(R.id.btn_logout);
        btn_scan_qrcode=findViewById(R.id.btn_scan_qrcode);
        btn_scan_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(dashboard.this,scan_qrcode.class));
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,MainActivity.class));
            }
        });
    }
}