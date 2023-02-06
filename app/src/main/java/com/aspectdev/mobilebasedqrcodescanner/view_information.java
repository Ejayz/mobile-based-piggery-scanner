package com.aspectdev.mobilebasedqrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class view_information extends AppCompatActivity {
    private Button btn_vaccination,btn_feeding,btn_medicine_administration,btn_back_scan,btn_deworming,btn_schedule;
    private String qrCode;
    private TextView data_pig_code,data_pig_birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information);
        btn_back_scan=findViewById(R.id.btn_back_scan);
        btn_feeding=findViewById(R.id.btn_feeding);
        btn_vaccination=findViewById(R.id.btn_vaccination);
        btn_medicine_administration=findViewById(R.id.btn_medicine_adminstration);
        btn_deworming=findViewById(R.id.btn_deworming);
        btn_schedule=findViewById(R.id.btn_schedule);
        data_pig_code=findViewById(R.id.data_qr_code);
        data_pig_birthday=findViewById(R.id.data_pig_birthdate);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            data_pig_code.setText(extras.getString("tag"));
        }
        btn_back_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),scan_qrcode.class));
            }
        });
    }
}