package com.aspectdev.mobilebasedqrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btn_login;
    EditText edit_username,edit_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login=findViewById(R.id.btn_login);
        edit_username=findViewById(R.id.edit_username_login);
        edit_password=findViewById(R.id.edit_password_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,dashboard.class));
            }
        });
    }
}